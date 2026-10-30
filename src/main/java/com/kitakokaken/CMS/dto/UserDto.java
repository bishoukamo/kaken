package com.kitakokaken.CMS.dto;

import java.time.LocalDateTime;
import com.kitakokaken.CMS.entity.Enum.Yakuwari;

/**
 * データベースから必要な項目だけを受け取るためのクラス(DTO)
 * Entityの代わりに、このクラスへデータを格納する。
 */
public class UserDto {

    // ==========================
    // フィールド
    // ==========================

    // ユーザーID
    private Long user_id;

    // ユーザー名
    private String u_name;

    // 役割
    private Yakuwari yakuwari;

    // 学年組番号
    private Long gakuban;

    // 登録日時
    private LocalDateTime setting;

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
    public UserDto(Long user_id,
                       String u_name,
                       Yakuwari yakuwari,
                       Long gakuban,
                       LocalDateTime setting) {

        this.user_id = user_id;
        this.u_name = u_name;
        this.yakuwari = yakuwari;
        this.gakuban = gakuban;
        this.setting = setting;
    }

    // ==========================
    // Getter
    // ==========================

    /**
     * user_idを取得する
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * u_nameを取得する
     */
    public String getU_name() {
        return u_name;
    }

    /**
     * yakuwariを取得する
     */
    public Yakuwari getYakuwari() {
        return yakuwari;
    }

    /**
     * gakubanを取得する
     */
    public Long getGakuban() {
        return gakuban;
    }

    /**
     * settingを取得する
     */
    public LocalDateTime getSetting() {
        return setting;
    }


}