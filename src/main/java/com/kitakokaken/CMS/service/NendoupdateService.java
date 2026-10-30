package com.kitakokaken.CMS.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.entity.Nendo_update;

@Service
public class NendoupdateService {

    @PersistenceContext
    private EntityManager em;

    public List<Nendo_update> findData(String sort, String order){

        String orderBy = switch(sort){

            case "U_id"        -> "n.U_id";
            case "M_id"        -> "n.M_id";
            case "U_date"      -> "n.U_date";
            case "U_weight"    -> "n.U_weight";
            case "kousinType"  -> "n.kousinType";

            default -> "n.U_id";
        };

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){
            order = "ASC";
        }

        String jpql = """
            SELECT n
            FROM Nendo_update n
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<Nendo_update> query =
                em.createQuery(jpql, Nendo_update.class);

        return query.getResultList();
    }
}