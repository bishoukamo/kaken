package com.kitakokaken.CMS.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitakokaken.CMS.entity.QrLabel;
import com.kitakokaken.CMS.repository.CapaitiranRepository;
import com.kitakokaken.CMS.repository.QrLabelRepository;

@Service
public class QrLabelService {

    private static final SecureRandom RANDOM =
            new SecureRandom();

    private final QrLabelRepository qrLabelRepository;

    private final CapaitiranRepository capaitiranRepository;

    public QrLabelService(
            QrLabelRepository qrLabelRepository,
            CapaitiranRepository capaitiranRepository) {

        this.qrLabelRepository = qrLabelRepository;
        this.capaitiranRepository = capaitiranRepository;
    }

    //M_idを指定してQRトークンを発行する。すでに未使用のQRが存在する場合は、そのQRを再利用する。
    @Transactional
    public QrLabel issue(Long materialId) {

    if (materialId == null) {
        throw new RuntimeException("容器IDを入力してください");
    }

    if (materialId < 1) {
        throw new RuntimeException("容器IDは1以上の数値を入力してください");
    }

    // 同じ容器IDに未使用のQRがあれば再利用する
    Optional<QrLabel> existing =
            qrLabelRepository.findByMaterialIdAndAssignedAtIsNull(materialId);

    if (existing.isPresent()) {
        return existing.get();
    }

    // 使用済みのQRがある容器IDには新規発行しない
    if (qrLabelRepository.existsByMaterialId(materialId)) {
        throw new RuntimeException(
                "この容器IDにはすでに薬品登録されています");
    }

    // QRトークンを生成する
    for (int i = 0; i < 10; i++) {

        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        if (!qrLabelRepository.existsById(token)) {

            QrLabel qrLabel = new QrLabel();

            qrLabel.setQrToken(token);
            qrLabel.setMaterialId(materialId);
            qrLabel.setCreatedAt(LocalDateTime.now());
            qrLabel.setAssignedAt(null);

            return qrLabelRepository.save(qrLabel);
        }
    }

    throw new RuntimeException("QRトークンの発行に失敗しました");
}

@Transactional
public void verify(String qrToken, Long materialId) {

    if (qrToken == null || qrToken.isBlank()) {
        throw new RuntimeException("QRコードを生成してください");
    }

    if (materialId == null) {
        throw new RuntimeException("容器IDを入力してください");
    }

    if (materialId < 1) {
        throw new RuntimeException("容器IDは1以上の数値を入力してください");
    }

    // QRコードの接頭辞を除去
    if (qrToken.startsWith("CMS1:")) {
        qrToken = qrToken.substring("CMS1:".length());
    }

    // QRコードの行をロックして取得
    QrLabel qrLabel = qrLabelRepository
            .findByQrTokenForUpdate(qrToken)
            .orElseThrow(() ->
                    new RuntimeException("QRトークンが見つかりません"));

    // 容器IDの一致を確認
    if (!materialId.equals(qrLabel.getMaterialId())) {
        throw new RuntimeException("QRコードと容器IDが一致しません");
    }

    // 使用済みか確認
    if (qrLabel.getAssignedAt() != null) {
        throw new RuntimeException(
                "このQRコードはすでに薬品登録に使用されています");
    }
}


    @Transactional
    public void assignToMaterial(String qrToken, Long materialId) {
    if (qrToken == null || qrToken.isBlank()) {
        throw new RuntimeException("QRコードがありません");
    }

    if (materialId == null) {
        throw new RuntimeException("容器IDがありません");
    }

    // QRコードの接頭辞を除去
    if (qrToken.startsWith("CMS1:")) {
        qrToken = qrToken.substring("CMS1:".length());
    }

    // QRコードの行をロックして取得
    QrLabel qrLabel = qrLabelRepository.findByQrTokenForUpdate(qrToken)
            .orElseThrow(() -> new RuntimeException("QRトークンが見つかりません"));

    // 容器IDの一致を確認
    if (!materialId.equals(qrLabel.getMaterialId())) {
        throw new RuntimeException("QRコードと容器IDが一致しません");
    }

    // 使用済みか確認
    if (qrLabel.getAssignedAt() != null) {
        throw new RuntimeException("このQRコードはすでに使用されています");
    }

    // QRコードを使用済みにする
    qrLabel.setAssignedAt(LocalDateTime.now());
    qrLabelRepository.save(qrLabel);
    }

    @Transactional
    public QrLabel reissue(Long materialId) {

    if (materialId == null) {
        throw new RuntimeException("容器IDを入力してください");
    }

    if (materialId < 1) {
        throw new RuntimeException("容器IDは1以上の数値を入力してください");
    }

    // 容器IDが実際に登録されているか確認
    if (!capaitiranRepository.existsById(materialId)) {
        throw new RuntimeException(
                "指定された容器番号のデータが見つかりません");
    }

    // 現在使用中のQRコードを取得
    QrLabel oldQr = qrLabelRepository
            .findByMaterialIdAndAssignedAtIsNotNull(materialId)
            .orElseThrow(() -> new RuntimeException(
                    "この容器IDに登録済みのQRコードがありません"));

    // 新しいQRトークンを生成して登録
    for (int i = 0; i < 10; i++) {

        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        if (qrLabelRepository.existsById(token)) {
            continue;
        }

        QrLabel newQr = new QrLabel();
        newQr.setQrToken(token);
        newQr.setMaterialId(materialId);
        newQr.setCreatedAt(LocalDateTime.now());

        // 登録済みの薬品なので、新しいQRも使用済み状態にする
        newQr.setAssignedAt(LocalDateTime.now());

        // 新しいQRを先に保存
        QrLabel savedQr = qrLabelRepository.saveAndFlush(newQr);

        // 新しいQRの保存に成功したら、古いQRを削除
        qrLabelRepository.delete(oldQr);
        qrLabelRepository.flush();

        // 新しいQRを返す
        return savedQr;
    }

    throw new RuntimeException(
            "QRトークンの再発行に失敗しました");
}
}