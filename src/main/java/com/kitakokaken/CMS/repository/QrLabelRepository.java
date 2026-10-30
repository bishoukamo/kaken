package com.kitakokaken.CMS.repository;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kitakokaken.CMS.entity.QrLabel;
import java.util.Optional;


public interface QrLabelRepository
        extends JpaRepository<QrLabel, String> {

    boolean existsByMaterialId(Long materialId);

    Optional<QrLabel> findByMaterialIdAndAssignedAtIsNull(
        Long materialId);

    Optional<QrLabel> findByMaterialIdAndAssignedAtIsNotNull(
        Long materialId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QrLabel q WHERE q.qrToken = :qrToken")
    Optional<QrLabel> findByQrTokenForUpdate(
        @Param("qrToken") String qrToken);
}