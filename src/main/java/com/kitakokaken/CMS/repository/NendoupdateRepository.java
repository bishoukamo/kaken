package com.kitakokaken.CMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kitakokaken.CMS.entity.Nendo_update;

/*全体を出力 */
public interface NendoupdateRepository
    extends JpaRepository<Nendo_update, Long> {
}