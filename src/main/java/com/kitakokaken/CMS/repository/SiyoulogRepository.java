package com.kitakokaken.CMS.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kitakokaken.CMS.dto.SiyoulogDto;
import com.kitakokaken.CMS.entity.Siyoulog;

public interface SiyoulogRepository extends JpaRepository<Siyoulog, Long> {

    @Query("""
        SELECT new com.kitakokaken.CMS.dto.SiyoulogDto(
            s.log_id,
            s.useday,
            c.C_name,
            s.M_id,
            s.bf_weight,
            s.af_weight,
            s.PTR_id,
            p.PTR_name,
            u.u_name
        )
        FROM Siyoulog s
        JOIN C_itiran c
            ON s.C_id = c.C_id
        JOIN PTR_itiran p
            ON s.PTR_id = p.PTR_id
        JOIN User u
            ON s.user_id = u.user_id
        """)
    List<SiyoulogDto> findSiyoulogDataAsc();
    
    @Query("""
    SELECT new com.kitakokaken.CMS.dto.SiyoulogDto(
        s.log_id,
        s.useday,
        c.C_name,
        s.M_id,
        s.bf_weight,
        s.af_weight,
        s.PTR_id,
        p.PTR_name,
        u.u_name
    )
    FROM Siyoulog s
    JOIN C_itiran c
        ON s.C_id = c.C_id
    JOIN PTR_itiran p
        ON s.PTR_id = p.PTR_id
    JOIN User u
        ON s.user_id = u.user_id
    ORDER BY s.useday DESC
    """)
    List<SiyoulogDto> findSiyoulogDataDesc();

    @Query("""
    SELECT new com.kitakokaken.CMS.dto.SiyoulogDto(
            s.log_id,
            s.useday,
            c.C_name,
            s.M_id,
            s.bf_weight,
            s.af_weight,
            s.PTR_id,
            p.PTR_name,
            u.u_name
        )
        FROM Siyoulog s
        JOIN C_itiran c
            ON s.C_id = c.C_id
        JOIN PTR_itiran p
            ON s.PTR_id = p.PTR_id
        JOIN User u
            ON s.user_id = u.user_id
        WHERE s.user_id = :userId
        ORDER BY s.useday DESC
        """)
    List<SiyoulogDto> findSiyoulogByUserId(Long userId);

    // 指定した容器の使用記録を新しい順に取得
    @Query("""
        SELECT s
        FROM Siyoulog s
        WHERE s.M_id = :mId
        ORDER BY s.useday DESC, s.log_id DESC
        """)
    List<Siyoulog> findByMaterialIdOrderByLatest(
        @Param("mId") Long mId);


    // 編集対象の記録より前にある、同じ容器の使用記録を取得
    @Query("""
        SELECT s
        FROM Siyoulog s
        WHERE s.M_id = :mId
        AND (
          s.useday < :useday
          OR (s.useday = :useday AND s.log_id < :logId)
        )
        ORDER BY s.useday DESC, s.log_id DESC
    """)
    List<Siyoulog> findPreviousRecords(
        @Param("mId") Long mId,
        @Param("useday") LocalDateTime useday,
        @Param("logId") Long logId);
}