package org.likelion._thon.silver_navi.global.init;

import jakarta.validation.constraints.DecimalMin;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums.FacilityCategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class NursingFacilityInitializer implements CommandLineRunner {

    private final NursingFacilityRepository nursingFacilityRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 요양 시설 데이터가 이미 있는지 확인
        if (nursingFacilityRepository.count() == 0) {

            // 이미지 URL 목록 정의
            List<String> imageUrls = List.of(
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/facility_1.png",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/facility_2.png",
                    "https://silvernavi-s3-bucket.s3.ap-northeast-2.amazonaws.com/facility_3.jpg"
            );

            NursingFacility facility1 = NursingFacility.builder()
                    .name("성신노인요양원")
                    .address("동소문로13나길 110")
                    .facilityNumber("02-929-8538")
                    .latitude(37.5948)
                    .longitude(127.0185)
                    .category(FacilityCategory.NURSING_HOSPITAL)
                    .description("따뜻한 보살핌을 제공하는 성신노인요양원입니다.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("주간보호", "물리치료", "인지활동"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility1);

            NursingFacility facility2 = NursingFacility.builder()
                    .name("청화요양원")
                    .address("서경로 67")
                    .facilityNumber("02-942-2255")
                    .latitude(37.6126)
                    .longitude(127.0182)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("쾌적한 환경의 청화요양원입니다.")
                    .operatingHours("매일 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of("방문요양", "목욕 서비스"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility2);

            NursingFacility facility3 = NursingFacility.builder()
                    .name("보문요양원")
                    .address("고려대로7길 32")
                    .facilityNumber("02-960-1123")
                    .latitude(37.5854)
                    .longitude(127.0073)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("전문적인 케어를 제공합니다.")
                    .operatingHours("월-금 08:00 - 19:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of("전문 간호", "재활 프로그램", "식사 제공", "야간보호"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility3);

            NursingFacility facility4 = NursingFacility.builder()
                    .name("종암요양원")
                    .address("종암로9길 56")
                    .facilityNumber("02-921-1009")
                    .latitude(37.6031)
                    .longitude(127.0339)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("가족 같은 마음으로 모십니다.")
                    .operatingHours("24시간 운영")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("24시간 돌봄", "물리치료"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility4);

            NursingFacility facility5 = NursingFacility.builder()
                    .name("둥지요양원")
                    .address("오패산로 49 2층1호")
                    .facilityNumber("02-943-1235")
                    .latitude(37.6127)
                    .longitude(127.0292)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("안락한 둥지 요양원입니다.")
                    .operatingHours("월-토 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of("데이케어", "미술치료", "식사제공"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility5);

            NursingFacility facility6 = NursingFacility.builder()
                    .name("한솔요양원")
                    .address("오패산로10길 30 4층401호")
                    .facilityNumber("02-911-7879")
                    .latitude(37.6132)
                    .longitude(127.0271)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("세심한 돌봄 서비스 제공.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of("방문요양", "인지재활", "친목 활동"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility6);

            NursingFacility facility7 = NursingFacility.builder()
                    .name("다애요양원")
                    .address("성북로4길 52")
                    .facilityNumber("02-921-3131")
                    .latitude(37.6012)
                    .longitude(127.0207)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("사랑과 정성으로 모시는 다애요양원.")
                    .operatingHours("매일 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("주간보호", "야간보호", "식사제공", "상담"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility7);

            NursingFacility facility8 = NursingFacility.builder()
                    .name("정릉노인요양원")
                    .address("정릉로12길 69")
                    .facilityNumber("02-6243-3000")
                    .latitude(37.6081)
                    .longitude(127.0083)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("전문 간호 인력이 상주합니다.")
                    .operatingHours("24시간 운영")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of("전문 간호", "재활 프로그램"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility8);

            NursingFacility facility9 = NursingFacility.builder()
                    .name("백마요양원")
                    .address("종암로18길 14")
                    .facilityNumber("02-943-6090")
                    .latitude(37.5972)
                    .longitude(127.0332)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("어르신들의 편안한 쉼터.")
                    .operatingHours("월-금 09:00 - 17:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of("데이케어", "송영 서비스", "건강관리"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility9);

            NursingFacility facility10 = NursingFacility.builder()
                    .name("포근한요양원")
                    .address("솔샘로15다길 32")
                    .facilityNumber("02-6228-9987")
                    .latitude(37.6105)
                    .longitude(127.0069)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("포근하고 안락한 환경을 제공합니다.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("주간보호", "물리치료"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility10);

            NursingFacility facility11 = NursingFacility.builder()
                    .name("성북재가노인복지센터")
                    .address("화랑로 207 3층")
                    .facilityNumber("02-942-6466")
                    .latitude(37.6212)
                    .longitude(127.0490)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("방문 요양 및 복지 서비스.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of("방문요양", "방문목욕", "재가 서비스"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility11);

            NursingFacility facility12 = NursingFacility.builder()
                    .name("성북50플러스센터")
                    .address("지봉로24길 26")
                    .facilityNumber("02-6223-5060")
                    .latitude(37.5869)
                    .longitude(127.0123)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("50세 이상 주민을 위한 다양한 프로그램.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of("교육 프로그램", "일자리 상담", "커뮤니티 활동", "건강 증진"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility12);

            NursingFacility facility13 = NursingFacility.builder()
                    .name("길음안나데이케어센터")
                    .address("동소문로34길 5")
                    .facilityNumber("02-940-3510")
                    .latitude(37.6038)
                    .longitude(127.0235)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("데이케어 전문 센터입니다.")
                    .operatingHours("월-금 08:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("주간보호", "인지활동", "식사제공"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility13);

            NursingFacility facility14 = NursingFacility.builder()
                    .name("덕수노인복지센터")
                    .address("동소문로20길 36")
                    .facilityNumber("02-762-4262")
                    .latitude(37.5930)
                    .longitude(127.0180)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("방문 목욕 및 간호 서비스 제공.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of("방문목욕", "방문간호"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility14);

            NursingFacility facility15 = NursingFacility.builder()
                    .name("동소문데이케어센터")
                    .address("동소문로 41")
                    .facilityNumber("02-926-7273")
                    .latitude(37.5991)
                    .longitude(127.0170)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("다양한 재활 프로그램을 운영합니다.")
                    .operatingHours("월-토 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of("데이케어", "재활 프로그램", "미술치료", "음악치료"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility15);
        }
    }
}
