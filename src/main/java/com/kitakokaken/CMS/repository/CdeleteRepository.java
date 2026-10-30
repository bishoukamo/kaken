package com.kitakokaken.CMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kitakokaken.CMS.entity.C_delete;

/*全体を出力 */
public interface CdeleteRepository
    extends JpaRepository<C_delete, Long> {
}