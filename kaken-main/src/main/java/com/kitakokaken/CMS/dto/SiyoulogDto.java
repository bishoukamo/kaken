package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class SiyoulogDto {

    // ==========================
    // フィールド
    // ==========================

    // 使用記録ID
    private Long log_id;

    // 登録日時
    private LocalDate useday;
    
    // 薬品名
    private String C_name;

    // 管理ID
    private Long M_id;

    //使用前総重量
    private double bf_weight;

    // 使用後総重量
    private double af_weight;

    // 実習室ID
    private Long PTR_id;

    //　実習室名
    private String PTR_name;

    //ユーザー名
    private String u_name;



    // ==========================
    // コンストラクタ
    // ==========================

    /**
     * Repositoryの
     * SELECT new com.kitakokaken.CMS.dto.SiyoulogDto(...)
     * で呼ばれるコンストラクタ
     *
     * 引数の順番はRepositoryの@Queryと完全に一致させること！
     */
    public SiyoulogDto(Long log_id,
                       LocalDate useday,
                       String C_name,
                       Long M_id,
                       double bf_weight,
                       double af_weight,
                       Long PTR_id,
                       String PTR_name,
                       String u_name) {

        this.log_id = log_id;
        this.useday = useday;
        this.C_name = C_name;
        this.M_id = M_id;
        this.bf_weight = bf_weight;
        this.af_weight = af_weight;
        this.PTR_id = PTR_id;
        this.PTR_name = PTR_name;
        this.u_name = u_name;
    }

    // ==========================
    // Getter
    // ==========================

    /**
     * log_idを取得する
     */
    public Long getLog_id() {
        return log_id;
    }

    /**
     * usedayを取得する
     */
    public LocalDate getUseday() {
        return useday;
    }

    /**
     * C_nameを取得する
     */
    public String getC_name() {
        return C_name;
    }

    /**
     * M_idを取得する
     */
    public Long getM_id() {
        return M_id;
    }

    /**
     * bf_weightを取得する
     */ 
    public double getBf_weight() {
        return bf_weight;
    }

    /**
     * af_weightを取得する
     */
    public double getAf_weight() {
        return af_weight;
    }

    /**
     * PTR_idを取得する
     */
    public Long getPTR_id() {
        return PTR_id;
    }

    /**
     * PTR_nameを取得する
     */
    public String getPTR_name() {
        return PTR_name;
    }

    /**
     * U_nameを取得する
     */ 
    public String getU_name() {
        return u_name;
    }


}