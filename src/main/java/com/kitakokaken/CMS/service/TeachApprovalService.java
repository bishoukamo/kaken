package com.kitakokaken.CMS.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitakokaken.CMS.entity.Enum.Yakuwari;
import com.kitakokaken.CMS.entity.TeachApproval;
import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.TeachApprovalRepository;
import com.kitakokaken.CMS.repository.UserRepository;

@Service
public class TeachApprovalService {

    private final UserRepository userRepository;
    private final TeachApprovalRepository teachApprovalRepository;

    public TeachApprovalService(
            UserRepository userRepository,
            TeachApprovalRepository teachApprovalRepository) {

        this.userRepository = userRepository;
        this.teachApprovalRepository = teachApprovalRepository;
    }

    @Transactional
    public void approve(Long applicantId, String approverMail) {

        // 現在ログインして承認ボタンを押した管理ユーザ
        User approver = userRepository.findByMail(approverMail)
                .orElseThrow(() ->
                    new RuntimeException("承認者が見つかりません"));

        // URLを直接実行されても、一般ユーザは承認不可
        if (approver.getYakuwari() != Yakuwari.teach) {
            throw new RuntimeException("管理ユーザのみ承認できます");
        }

        // 管理ユーザになりたい申請者
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() ->
                    new RuntimeException("申請ユーザが見つかりません"));

        if (!applicant.isTeachRequest()) {
            throw new RuntimeException("この申請はすでに処理済みです");
        }

        // 自分自身への承認は禁止
        if (approver.getUser_id().equals(applicant.getUser_id())) {
            throw new RuntimeException("自分自身の申請は承認できません");
        }

        // 同じ承認者による二重承認は禁止
        if (teachApprovalRepository.alreadyApproved(
                applicantId, approver.getUser_id())) {
            throw new RuntimeException("あなたはすでにこの申請を承認済みです");
        }

        TeachApproval approval = new TeachApproval();
        approval.setApplicant(applicant);
        approval.setApprover(approver);
        approval.setApprovedAt(LocalDateTime.now());

        teachApprovalRepository.save(approval);

        long approvalCount =
                teachApprovalRepository.countByApplicantId(applicantId);

        // 異なる管理ユーザ2人の承認後にだけ昇格
        if (approvalCount >= 2) {
            applicant.setYakuwari(Yakuwari.teach);
            applicant.setTeachRequest(false);
            userRepository.save(applicant);
        }
    }
}