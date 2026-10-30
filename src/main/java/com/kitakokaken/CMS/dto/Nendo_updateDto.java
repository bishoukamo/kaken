package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class Nendo_updateDto {

    // ==========================
    // フィールド
    // ==========================

    // 年度更新ID
    private Long U_id;

    // 管理ID
    private Long M_id;

    // 年度更新登録日
    private LocalDate U_date;

    // 年度更新重量
    private double U_weight;

    // 年度更新有無
    private double kousin_umu;

    // ==========================
    // コンストラクタ
    // ==========================

    /**
     * Repositoryの
     * SELECT new com.kitakokaken.CMS.dto.CapaDto(...)
     * で呼ばれるコンストラクタ
     *
     * 引数の順番はRepositoryの@Queryと完全に一致させること！
     */
    public Nendo_updateDto(Long U_id,
                   Long M_id,
                   LocalDate U_date,
                   double U_weight,
                   double kousin_umu) {

        this.U_id = U_id;
        this.M_id = M_id;
        this.U_date = U_date;
        this.U_weight = U_weight;
        this.kousin_umu = kousin_umu;
    }

    // ==========================
    // Getter
    // ==========================

    /**
     * U_idを取得する
     */
    public Long getU_id() {
        return U_id;
    }

    /**
     * M_idを取得する
     */
    public Long getM_id() {
        return M_id;
    }

    

    /**
     * U_dateを取得する
     */
    public LocalDate getU_date() {
        return U_date;
    }

    /**
     * U_weightを取得する
     */
    public double getU_weight() {
        return U_weight;
    }

    /**
     * kousin_umuを取得する
     */
    public double getKousin_umu() {
        return kousin_umu;
    }

}
