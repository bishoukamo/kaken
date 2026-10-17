package com.kitakokaken.CMS.entity;

import java.time.LocalDateTime;
import com.kitakokaken.CMS.repository.TeachApprovalRepository;
import jakarta.persistence.*;

@Entity
@Table(
    name = "teach_approval",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"applicant_user_id", "approver_user_id"}
    )
)
public class TeachApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long approvalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_user_id", nullable = false)
    private User applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_user_id", nullable = false)
    private User approver;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;

    public Long getApprovalId() {
        return approvalId;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
}
