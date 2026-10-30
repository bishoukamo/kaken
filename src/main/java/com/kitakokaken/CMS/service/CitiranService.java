package com.kitakokaken.CMS.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import com.kitakokaken.CMS.entity.C_itiran;

@Service
public class CitiranService {

    @PersistenceContext
    private EntityManager em;

    public List<C_itiran> findData(String sort, String order){

        String orderBy = switch(sort){
            
            case "C_id"        -> "c.C_id";
            case "C_name"      -> "c.C_name";
            case "poisonType"  -> "c.poisonType";
            case "situation"   -> "c.situation";
            case "C_set"       -> "c.C_set";
            case "remarks"     -> "c.remarks";

            default -> "c.C_id";
        };
        

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){

            order = "ASC";
        }

        String jpql = """
            SELECT c
            FROM C_itiran c
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<C_itiran> query =
                em.createQuery(jpql, C_itiran.class);

        return query.getResultList();
    }
}