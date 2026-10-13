package com.kitakokaken.CMS.dto;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class PTRitiranDto {

    // ==========================
    // フィールド
    // ==========================

    // 実習室ID
    private Long PTR_id;
    // 管理ユーザーID
    private Long user_id;
    // 実習室名
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
    public PTRitiranDto(Long PTR_id,
                   Long user_id,
                   String PTR_name) {
        
        this.PTR_id = PTR_id;
        this.user_id = user_id;
        this.PTR_name = PTR_name;
    }  
        

    // ==========================
    // Getter
    // ==========================

    /**
     * PTR_idを取得する
     */
    public Long getPTR_id() {
        return PTR_id;
    }

    /**
     * user_idを取得する
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * PTR_nameを取得する
     */
    public String getPTR_name() {
        return PTR_name;
    }
}