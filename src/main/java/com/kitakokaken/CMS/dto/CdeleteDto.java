package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class CdeleteDto {

    // ==========================
    // フィールド
    // ==========================

    // 抹消済み薬品ID
    private Long D_id;

    // 管理ID
    private Long M_id;

    // 抹消日
    private LocalDate D_date;

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
    public CdeleteDto(Long D_id,
                   Long M_id,
                   LocalDate d_date) {
        
        this.D_id = D_id;
        this.M_id = M_id;
        this.D_date = d_date;
    }

    // ==========================
    // Getter
    // ==========================

    /**
     * D_idを取得する
     */
    public Long getD_id() {
        return D_id;
    }

    

    /**
     * M_idを取得する
     */
    public Long getM_id() {
        return M_id;
    }

    /**
     * D_dateを取得する
     */
    public LocalDate getD_date() {
        return D_date;
    }
}