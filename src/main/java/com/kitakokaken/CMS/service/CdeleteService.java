package com.kitakokaken.CMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.kitakokaken.CMS.entity.C_delete;

@Service
public class CdeleteService {

    @PersistenceContext
    private EntityManager em;

    public List<C_delete> findData(String sort, String order){

        String orderBy = switch(sort){

            case "D_id"   -> "c.D_id";
            case "M_id"   -> "c.M_id";
            case "D_date" -> "c.D_date";

            default -> "c.D_id";
        };

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){
            order = "ASC";
        }

        String jpql = """
            SELECT c
            FROM C_delete c
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<C_delete> query =
                em.createQuery(jpql, C_delete.class);

        return query.getResultList();
    }
}