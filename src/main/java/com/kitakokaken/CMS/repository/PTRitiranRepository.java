package com.kitakokaken.CMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kitakokaken.CMS.entity.PTR_itiran;
/*全体を出力 */
public interface PTRitiranRepository
    extends JpaRepository<PTR_itiran, Long> {
}