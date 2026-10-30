package com.kitakokaken.CMS.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.dto.UserDto;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public List<UserDto> findData(String sort, String order){

        String orderBy = switch(sort){

        case "user_id"   -> "u.user_id";
        case "u_name"    -> "u.u_name";
        case "yakuwari"  -> "u.yakuwari";
        case "gakuban"   -> "u.gakuban";
        case "setting"   -> "u.setting";

        default -> "u.user_id";
        };

        if(!order.equalsIgnoreCase("ASC")
                && !order.equalsIgnoreCase("DESC")){
            order = "ASC";
        }

        String jpql = """
            SELECT new com.kitakokaken.CMS.dto.UserDto(
                u.user_id,
                u.u_name,
                u.yakuwari,
                u.gakuban,
                u.setting
            )
            FROM User u
            ORDER BY
            """ + orderBy + " " + order;

        TypedQuery<UserDto> query =
                em.createQuery(jpql, UserDto.class);

        return query.getResultList();
    }
}
