package org.likelion._thon.silver_navi.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.entity.ConsultReply;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultNotFoundException;
import org.likelion._thon.silver_navi.domain.consult.repository.ConsultReplyRepository;
import org.likelion._thon.silver_navi.domain.consult.repository.ConsultRepository;
import org.likelion._thon.silver_navi.domain.consult.repository.GeneralConsultRepository;
import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationStatus;
import org.likelion._thon.silver_navi.domain.notification.repository.NotificationRepository;
import org.likelion._thon.silver_navi.domain.notification.web.dto.CountRes;
import org.likelion._thon.silver_navi.domain.notification.web.dto.NotificationRes;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityAccessDeniedException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramApplicantNotFoundException;
import org.likelion._thon.silver_navi.domain.program.exception.ProgramNotFoundException;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewNotFoundException;
import org.likelion._thon.silver_navi.domain.review.repository.ReviewRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.likelion._thon.silver_navi.domain.notification.exception.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProgramRepository programRepository;
    private final ProgramApplyRepository programApplyRepository;
    private final ConsultRepository consultRepository;
    private final GeneralConsultRepository generalConsultRepository;
    private final ReviewRepository reviewRepository;
    private final ConsultReplyRepository  consultReplyRepository;

    @Override
    public CountRes getCount(User user) {
        int unreadCount = notificationRepository.countByUserAndIsReadFalse(user);
        return CountRes.from(unreadCount);
    }

    @Override
    @Transactional
    public void read(User user, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new NotificationAccessDeniedException();
        }
        notification.markAsRead();
    }

    @Override
    public List<NotificationRes> getList(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserOrderByCreatedAtDesc(user);

        return notifications.stream()
                .map(n -> {
                    String targetName = null;
                    String rejectReason = null;
                    LocalDate programDate = null;
                    LocalTime programStartTime = null;

                    switch (n.getType()) {
                        case PROGRAM -> {
                            ProgramApply apply = programApplyRepository.findById(n.getReferenceId())
                                    .orElseThrow(ProgramApplicantNotFoundException::new);

                            targetName = apply.getProgram().getName();
                            programDate = apply.getProgram().getDate();
                            programStartTime = apply.getProgram().getStartTime();

                            // 거부된 경우 거부 사유
                            if (n.getStatus() == NotificationStatus.REJECTED) {
                                rejectReason = programApplyRepository.findByUserAndProgram(user,
                                                programRepository.findById(n.getReferenceId()).orElse(null))
                                        .map(ProgramApply::getRejectReason)
                                        .orElse(null);
                            }
                        }
                        case CONSULT -> {
                            if (n.getConsultCategory() == ConsultCategory.GRADE) {
                                targetName = consultRepository.findById(n.getReferenceId())
                                        .map(c -> c.getFacility().getName())
                                        .orElseThrow(ConsultNotFoundException::new);
                                rejectReason = consultReplyRepository.findByConsult_Id(n.getReferenceId())
                                        .map(ConsultReply::getContent)
                                        .orElse(null);
                            } else {
                                targetName = generalConsultRepository.findById(n.getReferenceId())
                                        .map(c -> c.getFacility().getName())
                                        .orElseThrow(ConsultNotFoundException::new);
                                rejectReason = consultReplyRepository.findByGeneralConsult_Id(n.getReferenceId())
                                        .map(ConsultReply::getContent)
                                        .orElse(null);
                            }
                        }
                        case PROGRAM_REMINDER -> {
                            Program program = programRepository.findById(n.getReferenceId())
                                    .orElseThrow(ProgramNotFoundException::new);

                            targetName = program.getName();
                            programDate = program.getDate();
                            programStartTime = program.getStartTime();
                        }
                        case REVIEW_REPLY -> {
                            targetName = reviewRepository.findById(n.getReferenceId())
                                    .map(r -> r.getNursingFacility().getName())
                                    .orElseThrow(ReviewNotFoundException::new);
                        }
                    }

                    return NotificationRes.from(n, targetName, rejectReason, programDate, programStartTime);
                })
                .toList();
    }
}
