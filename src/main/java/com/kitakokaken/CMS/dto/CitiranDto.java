package com.kitakokaken.CMS.dto;

import java.time.LocalDate;
import com.kitakokaken.CMS.entity.Enum;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class CitiranDto {

    // ==========================
    // フィールド
    // ==========================

    // 薬品ID
    private Long C_id;

    // 薬品名
    private String C_name;

    // 毒劇物区分
    private Enum.DS poisonType;

    // 状態
    private Enum.Situation situation;

    // 薬品登録日時
    private LocalDate C_set;
    
    // 備考
    private String remark;

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
    public CitiranDto(Long C_id,
                   String C_name,
                   Enum.DS poisonType,
                   Enum.Situation situation,
                   LocalDate C_set,
                   String remark) {
        
        this.C_id = C_id;
        this.C_name = C_name;
        this.poisonType = poisonType;
        this.situation = situation;
        this.C_set = C_set;
        this.remark = remark;
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
     * poisonType (DS)を取得する
     */
    public Enum.DS getPoisonType() {
        return poisonType;
    }

    /**
     * situation (situation)を取得する
     */
    public Enum.Situation getSituation() {
        return situation;
    }

    /**
     * C_setを取得する
     */
    public LocalDate getC_set() {
        return C_set;
    }

    /**
     * remarkを取得する
     */
    public String getRemark() {
        return remark;
    }
}