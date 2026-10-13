package com.kitakokaken.CMS.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import com.kitakokaken.CMS.dto.CapaitiranDto;

@Service
public class CapaitiranService {

    @PersistenceContext
    private EntityManager em;

    public List<CapaitiranDto> findData(String sort, String order){

        String orderBy = switch(sort){

            case "C_id"       -> "c.C_id";
            case "C_name"     -> "c.C_name";
            case "poisonType" -> "c.poisonType";
            case "M_id"       -> "cp.M_id";
            case "PRD"        -> "cp.PRD";
            case "capa"       -> "cp.capa";
            case "pur_weight" -> "cp.pur_weight";

            default -> "cp.M_id";
        };

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){
            order = "ASC";
        }

        String jpql = """
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
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<CapaitiranDto> query =
                em.createQuery(jpql, CapaitiranDto.class);

        return query.getResultList();
    }
}