package com.kitakokaken.CMS.dto;

import com.kitakokaken.CMS.entity.Enum;
/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class UseCitiranDto {

    // ==========================
    // フィールド
    // ==========================
    
    // 管理ID
    private Long M_id;

    // 毒物区分
    private Enum.DS poisonType;

    // 薬品名
    private String C_name;

    // 状態
    private Enum.Situation situation;

    // 使用後総重量
    private double af_weight;

    //実習室名
    private String PTR_name;



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
    public UseCitiranDto(Long M_id,
                       Enum.DS poisonType,
                       String C_name,
                       Enum.Situation situation,
                       double af_weight,
                       String PTR_name) {
        
        this.M_id = M_id;
        this.poisonType = poisonType;
        this.C_name = C_name;
        this.situation = situation;
        this.af_weight = af_weight;
        this.PTR_name = PTR_name;
    }

    // ==========================
    // Getter
    // ==========================


    /**
     * M_idを取得する
     */
    public Long getM_id() {
        return M_id;
    }

    /**
     * C_nameを取得する
     */
    public String getC_name() {
        return C_name;
    }

    /**
     * poisonTypeを取得する
     */
    public Enum.DS getPoisonType() {
        return poisonType;
    }

    /**
     * situationを取得する
     */
    public Enum.Situation getSituation() {
        return situation;
    }

    /**
     * af_weightを取得する
     */
    public double getAf_weight() {
        return af_weight;
    }

    /**
     * PTR_nameを取得する
     */
    public String getPTR_name() {
        return PTR_name;
    }
}
