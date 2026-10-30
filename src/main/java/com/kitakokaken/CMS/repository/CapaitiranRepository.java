package com.kitakokaken.CMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kitakokaken.CMS.dto.LowStockNoticeRow;
import com.kitakokaken.CMS.dto.ExpiryNoticeRow;
import com.kitakokaken.CMS.dto.CapaitiranDto;
import com.kitakokaken.CMS.entity.Capa_itiran;

public interface CapaitiranRepository
        extends JpaRepository<Capa_itiran, Long> {

    @Query("""
        SELECT new com.kitakokaken.CMS.dto.CapaitiranDto(
            c.C_id,
            c.C_name,
            c.remarks,
            c.poisonType,
            cp.capa,
            cp.pur_weight,
            cp.PRD,
            cp.M_id
        )
        FROM Capa_itiran cp, C_itiran c
        WHERE cp.C_id = c.C_id
        """)
    List<CapaitiranDto> findData();
    @Query(value = """
    SELECT
        c.C_name AS chemicalName,
        ca.M_id AS materialId,
        ca.PRD AS expiryDate
    FROM capa_itiran ca
    JOIN c_itiran c ON c.C_id = ca.C_id
    WHERE ca.PRD <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)
    ORDER BY ca.PRD ASC
    """, nativeQuery = true)
    List<ExpiryNoticeRow> findExpiryNotices();
    @Query(value = """
    WITH weight_events AS (
        SELECT
            M_id,
            af_weight AS remaining_weight,
            useday AS event_date,
            log_id AS event_id,
            1 AS event_priority
        FROM siyoulog

        UNION ALL

        SELECT
            M_id,
            U_weight AS remaining_weight,
            U_date AS event_date,
            U_id AS event_id,
            0 AS event_priority
        FROM nendo_update
        ),
        latest_weights AS (
        SELECT
            M_id,
            remaining_weight,
            ROW_NUMBER() OVER (
                PARTITION BY M_id
                ORDER BY event_date DESC,
                    event_priority DESC,
                    event_id DESC
            ) AS row_num
            FROM weight_events
        )
        SELECT
            c.C_name AS chemicalName,
            ca.M_id AS materialId,
            COALESCE(lw.remaining_weight, ca.pur_weight) AS remainingWeight,
            ca.pur_weight AS purchaseWeight
        FROM capa_itiran ca
        JOIN c_itiran c ON c.C_id = ca.C_id
        LEFT JOIN latest_weights lw
        ON lw.M_id = ca.M_id
        AND lw.row_num = 1
        WHERE ca.PRD >= CURDATE()
        AND COALESCE(lw.remaining_weight, ca.pur_weight)
            <= ca.pur_weight * 0.2
        ORDER BY remainingWeight ASC
        """, nativeQuery = true)
    List<LowStockNoticeRow> findLowStockNotices();
}