package org.likelion._thon.silver_navi.domain.program.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.bookmark.entity.ProgramBookmark;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramStatus;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramModifyReq;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "program")
public class Program extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;                                // 프로그램 이름

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ProgramCategory category;                   // 카테고리 - 건강, 문화, 치료

    @Column(name = "instructor_name")
    private String instructorName;                      // 강사명

    @Column(name = "date", nullable = false)
    private LocalDate date;                             // 일정

    @Column(name = "start_time", nullable = false, columnDefinition = "TIME(0)")
    private LocalTime startTime;                        // 시작 시간

    @Column(name = "end_time", nullable = false, columnDefinition = "TIME(0)")
    private LocalTime endTime;                          // 종료 시간

    @Column(name = "location")
    private String location;                            // 장소

    @Column(name = "capacity")
    private Integer capacity;                           // 정원

    @Column(name = "fee")
    private String fee;                                // 참가비

    @Column(name = "contact_phone")
    private String contactPhone;                        // 문의 전화

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;                         // 프로그램 설명

    @Column(name = "current_participant", nullable = false)
    private Integer currentParticipant;                 // 신청 인원

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProgramStatus status;                       // 상태

    @ElementCollection
    @CollectionTable(name = "program_supplies", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "supply")
    @Builder.Default
    private List<String> supplies = new ArrayList<>();  // 준비물 목록들

    // ------------------------------ 첨부 서류 ------------------------------

    @Column(name = "proposal_url")
    private String proposalUrl;                         // 프로그램 기획서

    @ElementCollection
    @CollectionTable(name = "program_images", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>(); // 프로그램 사진들


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private NursingFacility nursingFacility;            // FK

    @OneToMany(mappedBy = "program",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramApply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "program",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramBookmark> bookmarks = new ArrayList<>();

    // ------------------------------ 메서드 ------------------------------
    public static Program toEntity(
            ProgramCreateReq req,
            NursingFacility nursingFacility,
            String proposalUrl,
            List<String> imageUrls
    ) {
        String instructorName = (req.getInstructorName() == null || req.getInstructorName().isBlank())
                ? null : req.getInstructorName();
        String location = (req.getLocation() == null || req.getLocation().isBlank())
                ? null : req.getLocation();
        String fee = (req.getFee() == null || req.getFee().isBlank())
                ? null : req.getFee();
        String contactPhone = (req.getNumber() == null || req.getNumber().isBlank())
                ? null : req.getNumber();
        String description = (req.getDescription() == null || req.getDescription().isBlank())
                ? null : req.getDescription();

        List<String> supplies = (req.getSupplies() == null)
                ? new ArrayList<>() : req.getSupplies().stream()
                .filter(s -> s != null && !s.isBlank())
                .toList();

        return Program.builder()
                .name(req.getName())
                .category(ProgramCategory.fromValue(req.getCategory()))
                .instructorName(instructorName)
                .date(req.getDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .location(location)
                .capacity(req.getCapacity())
                .fee(fee)
                .contactPhone(contactPhone)
                .description(description)
                .supplies(supplies)
                // URL 및 연관관계
                .proposalUrl(proposalUrl)
                .imageUrls(imageUrls)
                .nursingFacility(nursingFacility)
                // 기본값
                .currentParticipant(0)
                .status(ProgramStatus.RECRUITING)
                .build();
    }

    public Program updateEntity(
            ProgramModifyReq req, String finalProposalUrl, List<String> finalImageUrls
    ) {
        if (StringUtils.hasText(req.getName())) {
            this.name = req.getName();
        }
        if (StringUtils.hasText(req.getCategory())) {
            this.category = ProgramCategory.fromValue(req.getCategory());
        }
        if (req.getInstructorName() != null) {
            this.instructorName = req.getInstructorName().isBlank() ? null : req.getInstructorName();
        }
        if (req.getDate() != null) {
            this.date = req.getDate();
        }
        if (req.getStartTime() != null) {
            this.startTime = req.getStartTime();
        }
        if (req.getEndTime() != null) {
            this.endTime = req.getEndTime();
        }
        if (req.getLocation() != null) {
            this.location = req.getLocation().isBlank() ? null : req.getLocation();
        }
        if (req.getCapacity() != null) {
            if (req.getCapacity() == -1) {
                this.capacity = null; // -1이면 제거
            } else if (req.getCapacity() >= 1) { // 1 이상이면 수정
                this.capacity = req.getCapacity();
            }
        }
        if (req.getFee() != null) {
            this.fee = req.getFee().isBlank() ? null : req.getFee();
        }
        if (req.getNumber() != null) {
            this.contactPhone = req.getNumber().isBlank() ? null : req.getNumber();
        }
        if (req.getDescription() != null) {
            this.description = req.getDescription().isBlank() ? null : req.getDescription();
        }
        if (req.getSupplies() != null) {
            this.supplies.clear();
            List<String> validSupplies = req.getSupplies().stream()
                    .filter(StringUtils::hasText) // null, "", " " 필터링
                    .toList();
            this.supplies.addAll(validSupplies);
        }

        // 기획서
        if (req.getIsDeleteProposal()) {
            this.proposalUrl = finalProposalUrl;
        }
        // 사진
        this.imageUrls.clear();
        if (finalImageUrls != null) {
            this.imageUrls.addAll(finalImageUrls);
        }

        return this;
    }
}
