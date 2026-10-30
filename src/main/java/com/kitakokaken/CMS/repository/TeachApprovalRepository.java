package com.kitakokaken.CMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kitakokaken.CMS.entity.TeachApproval;

public interface TeachApprovalRepository
        extends JpaRepository<TeachApproval, Long> {

    @Query("""
        SELECT COUNT(a)
        FROM TeachApproval a
        WHERE a.applicant.user_id = :applicantId
    """)
    long countByApplicantId(Long applicantId);

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM TeachApproval a
        WHERE a.applicant.user_id = :applicantId
          AND a.approver.user_id = :approverId
    """)
    boolean alreadyApproved(Long applicantId, Long approverId);
}