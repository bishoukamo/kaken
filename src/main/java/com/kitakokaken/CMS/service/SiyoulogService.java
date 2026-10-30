package com.kitakokaken.CMS.service;

import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitakokaken.CMS.dto.SiyoulogDto;
import com.kitakokaken.CMS.entity.Capa_itiran;
import com.kitakokaken.CMS.entity.Siyoulog;
import com.kitakokaken.CMS.repository.CapaitiranRepository;
import com.kitakokaken.CMS.repository.PTRitiranRepository;
import com.kitakokaken.CMS.repository.SiyoulogRepository;

@Service
public class SiyoulogService {

    @Autowired
    private SiyoulogRepository repository;

    @Autowired
    private CapaitiranRepository capaitiranRepository;

    @Autowired
    private PTRitiranRepository ptritiranRepository;

    // 使用記録一覧取得
    public List<SiyoulogDto> findSiyoulogData(String order) {
        if ("desc".equalsIgnoreCase(order)) {
            return repository.findSiyoulogDataDesc();
        }
        return repository.findSiyoulogDataAsc();
    }

    // 自分の使用記録一覧取得
    public List<SiyoulogDto> findSiyoulogByUserId(Long userId) {
        return repository.findSiyoulogByUserId(userId);
    }

    // 使用記録をIDで取得
    public Siyoulog findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("使用記録が見つかりません"));
    }

    // 使用記録の共通チェック
    private String validateSiyoulog(
            Siyoulog siyoulog,
            boolean requireUseday) {

        if (siyoulog.getM_id() == null) {
            throw new IllegalArgumentException("容器IDがありません");
        }

        if (siyoulog.getC_id() == null) {
            throw new IllegalArgumentException("薬品IDがありません");
        }

        if (siyoulog.getPTR_id() == null) {
            throw new IllegalArgumentException("使用場所を選択してください");
        }

        if (requireUseday && siyoulog.getUseday() == null) {
            throw new IllegalArgumentException("使用日時を入力してください");
        }
        // 使用日時が未来になっていないか検証
        if (siyoulog.getUseday() != null
            && siyoulog.getUseday().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
            "使用日時に未来の日時は指定できません");
        }

        Capa_itiran capa = capaitiranRepository
                .findById(siyoulog.getM_id())
                .orElseThrow(() ->
                    new IllegalArgumentException("指定された容器IDは存在しません"));

        if (!Objects.equals(capa.getC_id(), siyoulog.getC_id())) {
            throw new IllegalArgumentException(
                "容器IDと薬品IDの組み合わせが正しくありません");
        }

        if (!ptritiranRepository.existsById(siyoulog.getPTR_id())) {
            throw new IllegalArgumentException("指定された実習室IDは存在しません");
        }

        double before = siyoulog.getBf_weight();
        double after = siyoulog.getAf_weight();

        if (!Double.isFinite(before) || !Double.isFinite(after)
                || before < 0 || after < 0) {
            throw new IllegalArgumentException(
                "使用前・使用後の重量は0以上で入力してください");
        }

        return null;
    }

    // 今回の使用前重量を、前回の最終重量と比較
    private String validateBeforeWeight(
            Siyoulog siyoulog,
            Capa_itiran capa,
            boolean isNew) {

        Siyoulog previous = null;

        if (isNew) {
            // 新規登録：同じ容器の最新記録を取得
            List<Siyoulog> records =
                    repository.findByMaterialIdOrderByLatest(
                            siyoulog.getM_id());

            if (!records.isEmpty()) {
                previous = records.get(0);
            }
        } else {
            // 編集：編集対象より前の記録を取得
            Siyoulog original = repository.findById(siyoulog.getLog_id())
                    .orElseThrow(() ->
                        new IllegalArgumentException("使用記録が見つかりません"));

            if (original.getUseday() != null) {
                List<Siyoulog> records =
                        repository.findPreviousRecords(
                                original.getM_id(),
                                original.getUseday(),
                                original.getLog_id());

                if (!records.isEmpty()) {
                    previous = records.get(0);
                }
            }
        }

        // 前回記録があればaf_weight、なければ初期重量を基準にする
        double expectedWeight = previous != null
                ? previous.getAf_weight()
                : capa.getPur_weight();

        double actualWeight = siyoulog.getBf_weight();
        double difference = actualWeight - expectedWeight;

        if (Math.abs(difference) > 3.0) {
            throw new IllegalArgumentException(
                    String.format(
                        "使用前重量が前回の最終重量と3gを超えて異なります。"
                        + "基準：%.2fg、今回：%.2fg、差：%+.2fg",
                        expectedWeight,
                        actualWeight,
                        difference));
        }

        if (difference != 0.0) {
            return String.format(
                    "警告：前回の最終重量との差は%+.2fgです。",
                    difference);
        }

        return null;
    }

    // 使用記録の新規登録
    @Transactional
    public String createSiyoulog(Siyoulog siyoulog) {

        validateSiyoulog(siyoulog, true);

        Capa_itiran capa = capaitiranRepository
                .findById(siyoulog.getM_id())
                .orElseThrow(() ->
                    new IllegalArgumentException("指定された容器IDは存在しません"));

        String warning =
                validateBeforeWeight(siyoulog, capa, true);

        repository.save(siyoulog);

        return warning;
    }

    // 使用記録の更新
    @Transactional
    public String updateSiyoulog(Siyoulog siyoulog) {

        Siyoulog existing = repository.findById(siyoulog.getLog_id())
                .orElseThrow(() ->
                    new IllegalArgumentException("使用記録が見つかりません"));

        validateSiyoulog(siyoulog, false);

        Capa_itiran capa = capaitiranRepository
                .findById(siyoulog.getM_id())
                .orElseThrow(() ->
                    new IllegalArgumentException("指定された容器IDは存在しません"));

        String warning =
                validateBeforeWeight(siyoulog, capa, false);

        existing.setC_id(siyoulog.getC_id());
        existing.setM_id(siyoulog.getM_id());
        existing.setPTR_id(siyoulog.getPTR_id());
        existing.setBf_weight(siyoulog.getBf_weight());
        existing.setAf_weight(siyoulog.getAf_weight());

        // useday と user_id は元の値を維持
        repository.save(existing);

        return warning;
    }

    // 既存処理との互換用
    public void saveSiyoulog(Siyoulog siyoulog) {
        createSiyoulog(siyoulog);
    }
}
