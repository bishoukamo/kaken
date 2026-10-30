package com.kitakokaken.CMS.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kitakokaken.CMS.entity.Enum.Yakuwari;
import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        select new com.kitakokaken.CMS.dto.UserDto(
            u.user_id,
            u.u_name,
            u.yakuwari,
            u.gakuban,
            u.setting
        )
        from User u
    """)
    List<UserDto> findData();

    //管理ユーザ申請一覧
    List<User> findByTeachRequestTrue();

    // メールアドレスからユーザーを検索
    Optional<User> findByMail(String mail);


    // ユーザーに関連するデータの件数を取得
    @Query(value = """
        SELECT
            (SELECT COUNT(*)
             FROM ptr_itiran
             WHERE user_id = :userId)
            +
            (SELECT COUNT(*)
             FROM siyoulog
             WHERE user_id = :userId)

        """, nativeQuery = true)
    long countRelatedData(@Param("userId") Long userId);
}