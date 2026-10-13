package com.kitakokaken.CMS.dto;

import java.time.LocalDate;
import com.kitakokaken.CMS.entity.Enum;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class CapaitiranDto {

    // ==========================
    // フィールド
    // ==========================

    //薬品ID
    private Long C_id;

    //薬品名
    private String C_name;

    //備考
    private String remarks;

    //劇毒
    private Enum.DS poisonType;

    //購入時内容量
    private double capa;
    
    // 購入時容器重量
    private double pur_weight;

    //購入登録日
    private LocalDate PRD;

    //管理ID
    private Long M_id;

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
    public CapaitiranDto(Long C_id,
                   String C_name,
                   String remarks,
                   Enum.DS poisonType,
                   double capa,
                   double pur_weight,
                   LocalDate PRD,
                   Long M_id) {

        this.C_id = C_id;
        this.C_name = C_name;
        this.remarks = remarks;
        this.poisonType = poisonType;
        this.capa = capa;
        this.pur_weight = pur_weight;
        this.PRD = PRD;
        this.M_id = M_id;
    }

    // ==========================
    // Getter
    // ==========================

    /**
     * C_idを取得する
     */
    public Long getC_id() {
        return C_id;
    }

    /**
     * C_nameを取得する
     */
    public String getC_name() {
        return C_name;
    }

    /**
     * remarksを取得する
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * poisonTypeを取得する
     */
    public Enum.DS getPoisonType() {
        return poisonType;
    }

    /**
     * capaを取得する
     */
    public double getCapa() {
        return capa;
    }

    /**
     * pur_weightを取得する
     */
    public double getPur_weight() {
        return pur_weight;
    }

    /**
     * PRDを取得する
     */
    public LocalDate getPRD() {
        return PRD;
    }

    /**
     * M_idを取得する
     */
    public Long getM_id() {
        return M_id;
    }

}