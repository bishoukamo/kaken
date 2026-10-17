package com.kitakokaken.CMS.repository;

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
}