package com.kitakokaken.CMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kitakokaken.CMS.dto.UseCitiranDto;
import com.kitakokaken.CMS.entity.Siyoulog;

public interface UseCitiranRepository extends JpaRepository<Siyoulog, Long> {

    @Query("""
        SELECT new com.kitakokaken.CMS.dto.UseCitiranDto(
            s.M_id,
            c.poisonType,
            c.C_name,
            c.situation,
            s.af_weight,
            p.PTR_name
        )
        FROM Siyoulog s
        JOIN C_itiran c
            ON s.C_id = c.C_id
        JOIN PTR_itiran p
            ON s.PTR_id = p.PTR_id

        ORDER BY
            CASE WHEN :sort = 'm_id' AND :order = 'ASC'
                 THEN s.M_id END ASC,

            CASE WHEN :sort = 'm_id' AND :order = 'DESC'
                 THEN s.M_id END DESC,

            CASE WHEN :sort = 'C_name' AND :order = 'ASC'
                 THEN c.C_name END ASC,

            CASE WHEN :sort = 'C_name' AND :order = 'DESC'
                 THEN c.C_name END DESC,

            CASE WHEN :sort = 'poisonType' AND :order = 'ASC'
                 THEN c.poisonType END ASC,

            CASE WHEN :sort = 'poisonType' AND :order = 'DESC'
                 THEN c.poisonType END DESC,

            CASE WHEN :sort = 'situation' AND :order = 'ASC'
                 THEN c.situation END ASC,

            CASE WHEN :sort = 'situation' AND :order = 'DESC'
                 THEN c.situation END DESC,

            CASE WHEN :sort = 'af_weight' AND :order = 'ASC'
                 THEN s.af_weight END ASC,

            CASE WHEN :sort = 'af_weight' AND :order = 'DESC'
                 THEN s.af_weight END DESC,

            CASE WHEN :sort = 'PTR_name' AND :order = 'ASC'
                 THEN p.PTR_name END ASC,

            CASE WHEN :sort = 'PTR_name' AND :order = 'DESC'
                 THEN p.PTR_name END DESC
        """)
    List<UseCitiranDto> findCurrentChemical(
            @Param("sort") String sort,
            @Param("order") String order
    );
}