package com.kitakokaken.CMS.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.entity.PTR_itiran;

@Service
public class PTRitiranService {

    @PersistenceContext
    private EntityManager em;

    public List<PTR_itiran> findData(String sort, String order){

        String orderBy = switch(sort){

        case "PTR_id"   -> "p.PTR_id";
        case "user_id"  -> "p.user_id";
        case "PTR_name" -> "p.PTR_name";

        default -> "p.PTR_id";
        };

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){
            order = "ASC";
        }

        String jpql = """
            SELECT p
            FROM PTR_itiran p
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<PTR_itiran> query =
                em.createQuery(jpql, PTR_itiran.class);

        return query.getResultList();
    }
}