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

    /**
     * M_idを指定してQRトークンを発行する
     *
     * すでに未使用のQRが存在する場合は
     * そのQRを再利用する。
     */
    @Transactional
    public QrLabel issue(Long materialId) {

        if (materialId == null) {
            throw new RuntimeException(
                    "容器IDを入力してください");
        }

        /*
         * すでにこのM_idに対して
         * 未使用のQRが存在するか確認
         */
        Optional<QrLabel> existing =
                qrLabelRepository
                        .findByMaterialIdAndAssignedAtIsNull(
                                materialId);

        if (existing.isPresent()) {
            return existing.get();
        }

        /*
         * すでに使用済みのQRが存在する場合
         */
        if (qrLabelRepository.existsByMaterialId(materialId)) {
            throw new RuntimeException(
                    "この容器IDにはすでに薬品登録されています");
        }

        /*
         * 新しいQRトークンを発行
         */
        for (int i = 0; i < 10; i++) {

            byte[] bytes = new byte[16];

            RANDOM.nextBytes(bytes);

            String token =
                    Base64.getUrlEncoder()
                            .withoutPadding()
                            .encodeToString(bytes);

            /*
             * トークンの重複確認
             */
            if (!qrLabelRepository.existsById(token)) {

                QrLabel qrLabel = new QrLabel();

                qrLabel.setQrToken(token);

                qrLabel.setMaterialId(materialId);

                qrLabel.setCreatedAt(
                        LocalDateTime.now());

                /*
                 * assignedAtはここでは設定しない
                 *
                 * 薬品登録が成功したときに
                 * assignToMaterial()で設定する
                 */
                qrLabel.setAssignedAt(null);

                return qrLabelRepository.save(qrLabel);
            }
        }

        throw new RuntimeException(
                "QRトークンの発行に失敗しました");
    }


    /**
     * QRトークンとM_idが一致しているか確認
     */
    @Transactional(readOnly = true)
    public void verify(
            String qrToken,
            Long materialId) {

        if (qrToken == null ||
                qrToken.isBlank()) {

            throw new RuntimeException(
                    "QRコードを生成してください");
        }

        if (materialId == null) {

            throw new RuntimeException(
                    "容器IDを入力してください");
        }

        /*
         * QRにCMS1:が付いていても対応
         */
        if (qrToken.startsWith("CMS1:")) {

            qrToken =
                    qrToken.substring(
                            "CMS1:".length());
        }

        /*
         * QRトークン検索
         */
        QrLabel qrLabel =
                qrLabelRepository
                        .findById(qrToken)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "QRトークンが見つかりません"));

        /*
         * M_id確認
         */
        if (!materialId.equals(
                qrLabel.getMaterialId())) {

            throw new RuntimeException(
                    "QRコードと容器IDが一致しません");
        }

        /*
         * すでに薬品登録済みか確認
         */
        if (qrLabel.getAssignedAt() != null) {

            throw new RuntimeException(
                    "このQRコードはすでに薬品登録に使用されています");
        }
    }


    /**
     * 薬品登録完了時にQRを使用済みにする
     */
    @Transactional
    public void assignToMaterial(
            String qrToken,
            Long materialId) {

        if (qrToken == null ||
                qrToken.isBlank()) {

            throw new RuntimeException(
                    "QRコードがありません");
        }

        if (materialId == null) {

            throw new RuntimeException(
                    "容器IDがありません");
        }

        /*
         * CMS1:を取り除く
         */
        if (qrToken.startsWith("CMS1:")) {

            qrToken =
                    qrToken.substring(
                            "CMS1:".length());
        }

        /*
         * QRトークン検索
         */
        QrLabel qrLabel =
                qrLabelRepository
                        .findById(qrToken)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "QRトークンが見つかりません"));

        /*
         * M_id確認
         */
        if (!materialId.equals(
                qrLabel.getMaterialId())) {

            throw new RuntimeException(
                    "QRコードと容器IDが一致しません");
        }

        /*
         * すでに使用済みか確認
         */
        if (qrLabel.getAssignedAt() != null) {

            throw new RuntimeException(
                    "このQRコードはすでに使用されています");
        }

        /*
         * 薬品登録完了
         */
        qrLabel.setAssignedAt(
                LocalDateTime.now());

        qrLabelRepository.save(qrLabel);
    }

    @Transactional
    public QrLabel reissue(Long materialId) {

    if (materialId == null) {
        throw new RuntimeException("容器IDを入力してください");
    }

    // ==========================================
    // M_idが実際に登録されているか確認
    // ==========================================
    if (!capaitiranRepository.existsById(materialId)) {
        throw new RuntimeException(
                "指定された容器番号のデータが見つかりません"
        );
    }

    // ==========================================
    // 現在使用中のQRを取得
    // ==========================================
    QrLabel oldQr =
            qrLabelRepository
                .findByMaterialIdAndAssignedAtIsNotNull(materialId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "この容器IDに登録済みのQRコードがありません"
                    )
                );

    // ==========================================
    // 古いQRを削除
    // ==========================================
    qrLabelRepository.delete(oldQr);

    // ==========================================
    // 新しいQRを発行
    // ==========================================
    for (int i = 0; i < 10; i++) {

        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);

        String token =
                Base64.getUrlEncoder()
                      .withoutPadding()
                      .encodeToString(bytes);

        if (!qrLabelRepository.existsById(token)) {

            QrLabel newQr = new QrLabel();

            newQr.setQrToken(token);
            newQr.setMaterialId(materialId);
            newQr.setCreatedAt(LocalDateTime.now());

            // すでに登録済みの薬品なのでassigned済みにする
            newQr.setAssignedAt(LocalDateTime.now());

            return qrLabelRepository.save(newQr);
        }
    }

    throw new RuntimeException(
            "QRトークンの再発行に失敗しました"
    );
    }
}