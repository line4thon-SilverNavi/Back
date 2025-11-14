package org.likelion._thon.silver_navi.global.init;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramStatus;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3)
public class ProgramInitializer implements CommandLineRunner {

    private final ProgramRepository programRepository;
    private final NursingFacilityRepository nursingFacilityRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 프로그램이 이미 있으면 실행하지 않음
        if (programRepository.count() == 0) {

            // 1. 대상 시설을 ID로 찾습니다. (2L, 8L, 10L)
            NursingFacility facility6 = nursingFacilityRepository.findById(6L).orElse(null);
            NursingFacility facility2 = nursingFacilityRepository.findById(2L).orElse(null);
            NursingFacility facility8 = nursingFacilityRepository.findById(8L).orElse(null);

            // S3에 업로드되었다고 가정한 프로그램 이미지 URL 5개
            List<String> programImageUrls = List.of(
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/program_1.png",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/program_2.png",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/program_3.png",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/program_4.jpg",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/program_5.png"
            );

            // 2. facility6 (ID: 6L) 프로그램 5개 생성 및 즉시 저장
            if (facility6 != null) {
                Program p1 = Program.builder()
                        .name("활기찬 아침 체조")
                        .category(ProgramCategory.HEALTH)
                        .instructorName("김건강")
                        .date(LocalDate.now().plusDays(10))
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(11, 0))
                        .location("강당")
                        .capacity(20)
                        .fee("무료")
                        .contactPhone("02-942-2255")
                        .description("아침을 깨우는 신나는 체조 시간!")
                        .supplies(List.of("편한 복장", "물통"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(0)))
                        .nursingFacility(facility6)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p1);

                Program p2 = Program.builder()
                        .name("수채화 그리기")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("이예술")
                        .date(LocalDate.now().plusDays(11))
                        .startTime(LocalTime.of(14, 0))
                        .endTime(LocalTime.of(15, 30))
                        .location("미술실")
                        .capacity(15)
                        .fee("재료비 5,000원")
                        .contactPhone("02-942-2255")
                        .description("그림을 그리며 마음의 안정을 찾습니다.")
                        .supplies(List.of("앞치마"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(1)))
                        .nursingFacility(facility6)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p2);

                Program p3 = Program.builder()
                        .name("음악 치료 세션")
                        .category(ProgramCategory.TREATMENT)
                        .instructorName("박치유")
                        .date(LocalDate.now().plusDays(12))
                        .startTime(LocalTime.of(16, 0))
                        .endTime(LocalTime.of(17, 0))
                        .location("음악실")
                        .capacity(10)
                        .fee("무료")
                        .contactPhone("02-942-2255")
                        .description("음악과 함께하는 힐링 타임.")
                        .supplies(List.of())
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(2)))
                        .nursingFacility(facility6)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p3);

                Program p4 = Program.builder()
                        .name("시니어 요가")
                        .category(ProgramCategory.HEALTH)
                        .instructorName("김건강")
                        .date(LocalDate.now().plusDays(13))
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(11, 0))
                        .location("강당")
                        .capacity(20)
                        .fee("무료")
                        .contactPhone("02-942-2255")
                        .description("관절에 무리가 가지 않는 요가 동작을 배웁니다.")
                        .supplies(List.of("요가매트"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(3)))
                        .nursingFacility(facility6)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p4);

                Program p5 = Program.builder()
                        .name("추억의 영화 상영")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("이예술")
                        .date(LocalDate.now().plusDays(14))
                        .startTime(LocalTime.of(14, 0))
                        .endTime(LocalTime.of(16, 0))
                        .location("시청각실")
                        .capacity(30)
                        .fee("무료")
                        .contactPhone("02-942-2255")
                        .description("다 함께 모여 추억의 영화를 봅니다.")
                        .supplies(List.of("간식"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(4)))
                        .nursingFacility(facility6)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p5);
            } else {
                System.out.println("ProgramInitializer: Facility ID 2L을(를) 찾을 수 없어 프로그램을 생성하지 못했습니다.");
            }

            // 3. facility2 (ID: 2L) 프로그램 5개 생성 및 즉시 저장
            if (facility2 != null) {
                Program p6 = Program.builder()
                        .name("웃음 치료 교실")
                        .category(ProgramCategory.TREATMENT)
                        .instructorName("최유쾌")
                        .date(LocalDate.now().plusDays(10))
                        .startTime(LocalTime.of(11, 0))
                        .endTime(LocalTime.of(12, 0))
                        .location("휴게실")
                        .capacity(25)
                        .fee("무료")
                        .contactPhone("02-6243-3000")
                        .description("웃음으로 면역력을 높여요!")
                        .supplies(List.of())
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(0)))
                        .nursingFacility(facility2)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p6);

                Program p7 = Program.builder()
                        .name("인지 강화 훈련")
                        .category(ProgramCategory.HEALTH)
                        .instructorName("김지혜")
                        .date(LocalDate.now().plusDays(11))
                        .startTime(LocalTime.of(14, 0))
                        .endTime(LocalTime.of(15, 0))
                        .location("프로그램실")
                        .capacity(15)
                        .fee("무료")
                        .contactPhone("02-6243-3000")
                        .description("퍼즐, 퀴즈 등으로 두뇌를 활성화합니다.")
                        .supplies(List.of("필기구"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(1)))
                        .nursingFacility(facility2)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p7);

                Program p8 = Program.builder()
                        .name("신나는 노래교실")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("이음악")
                        .date(LocalDate.now().plusDays(12))
                        .startTime(LocalTime.of(15, 0))
                        .endTime(LocalTime.of(16, 30))
                        .location("노래방")
                        .capacity(20)
                        .fee("월 10,000원")
                        .contactPhone("02-6243-3000")
                        .description("신나는 트로트와 가요를 배웁니다.")
                        .supplies(List.of("개인 물"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(2)))
                        .nursingFacility(facility2)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p8);

                Program p9 = Program.builder()
                        .name("원예 활동")
                        .category(ProgramCategory.TREATMENT)
                        .instructorName("박자연")
                        .date(LocalDate.now().plusDays(13))
                        .startTime(LocalTime.of(10, 30))
                        .endTime(LocalTime.of(11, 30))
                        .location("야외 정원")
                        .capacity(15)
                        .fee("재료비 10,000원")
                        .contactPhone("02-6243-3000")
                        .description("흙을 만지며 식물을 심고 가꿉니다.")
                        .supplies(List.of("장갑", "모자"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(3)))
                        .nursingFacility(facility2)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p9);

                Program p10 = Program.builder()
                        .name("건강 박수 체조")
                        .category(ProgramCategory.HEALTH)
                        .instructorName("최유쾌")
                        .date(LocalDate.now().plusDays(14))
                        .startTime(LocalTime.of(11, 0))
                        .endTime(LocalTime.of(11, 30))
                        .location("휴게실")
                        .capacity(30)
                        .fee("무료")
                        .contactPhone("02-6243-3000")
                        .description("박수 치며 스트레스를 해소합니다.")
                        .supplies(List.of())
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(4)))
                        .nursingFacility(facility2)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p10);
            } else {
                System.out.println("ProgramInitializer: Facility ID 8L을(를) 찾을 수 없어 프로그램을 생성하지 못했습니다.");
            }

            // 4. facility8 (ID: 8L) 프로그램 5개 생성 및 즉시 저장
            if (facility8 != null) {
                Program p11 = Program.builder()
                        .name("도자기 공예")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("김공예")
                        .date(LocalDate.now().plusDays(10))
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(11, 30))
                        .location("공예실")
                        .capacity(10)
                        .fee("재료비 20,000원")
                        .contactPhone("02-6228-9987")
                        .description("나만의 도자기를 만들어봅니다.")
                        .supplies(List.of("앞치마", "작업복"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(0)))
                        .nursingFacility(facility8)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p11);

                Program p12 = Program.builder()
                        .name("명상과 호흡")
                        .category(ProgramCategory.TREATMENT)
                        .instructorName("이평온")
                        .date(LocalDate.now().plusDays(11))
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(9, 45))
                        .location("명상실")
                        .capacity(15)
                        .fee("무료")
                        .contactPhone("02-6228-9987")
                        .description("편안한 마음을 위한 명상 시간.")
                        .supplies(List.of("편한 복장"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(1)))
                        .nursingFacility(facility8)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p12);

                Program p13 = Program.builder()
                        .name("튼튼 관절 운동")
                        .category(ProgramCategory.HEALTH)
                        .instructorName("박튼튼")
                        .date(LocalDate.now().plusDays(12))
                        .startTime(LocalTime.of(13, 30))
                        .endTime(LocalTime.of(14, 30))
                        .location("재활치료실")
                        .capacity(20)
                        .fee("무료")
                        .contactPhone("02-6228-9987")
                        .description("관절 건강을 위한 맞춤 운동.")
                        .supplies(List.of("운동화"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(2)))
                        .nursingFacility(facility8)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p13);

                Program p14 = Program.builder()
                        .name("서예 교실")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("최선비")
                        .date(LocalDate.now().plusDays(13))
                        .startTime(LocalTime.of(15, 0))
                        .endTime(LocalTime.of(16, 30))
                        .location("서예실")
                        .capacity(10)
                        .fee("재료비 15,000원")
                        .contactPhone("02-6228-9987")
                        .description("붓글씨로 마음을 다스립니다.")
                        .supplies(List.of("먹물", "붓"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(3)))
                        .nursingFacility(facility8)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p14);

                Program p15 = Program.builder()
                        .name("스마트폰 기초")
                        .category(ProgramCategory.CULTURE)
                        .instructorName("김스마트")
                        .date(LocalDate.now().plusDays(14))
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(11, 30))
                        .location("교육실")
                        .capacity(15)
                        .fee("무료")
                        .contactPhone("02-6228-9987")
                        .description("스마트폰으로 사진 찍고 카톡 보내기.")
                        .supplies(List.of("개인 스마트폰", "충전기"))
                        .proposalUrl(null)
                        .imageUrls(List.of(programImageUrls.get(4)))
                        .nursingFacility(facility8)
                        .currentParticipant(0)
                        .status(ProgramStatus.RECRUITING)
                        .build();
                programRepository.save(p15);
            }
        }
    }
}