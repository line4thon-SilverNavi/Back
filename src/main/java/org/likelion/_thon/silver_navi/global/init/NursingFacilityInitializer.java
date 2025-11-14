package org.likelion._thon.silver_navi.global.init;

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
                    .description("따뜻하고 전문적인 돌봄을 제공하는 A등급 노인요양시설입니다. 쾌적한 환경과 24시간 간호 및 보호 서비스를 운영합니다.")
                    .operatingHours("24시간 운영")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "주간보호",
                            "물리치료",
                            "인지활동",
                            "재활 프로그램",
                            "24시간 간호 및 보호 서비스"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility1);

            NursingFacility facility2 = NursingFacility.builder()
                    .name("정진요양원")
                    .address("서경로 110")
                    .facilityNumber("02-943-4272")
                    .latitude(37.6129)
                    .longitude(127.0102)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("중형 요양시설로 전문 인력과 다양한 재활 및 사회적응 프로그램을 운영하며, 쾌적한 환경 속에서 어르신들을 케어합니다.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "주간보호",
                            "야간보호",
                            "치매전담실 운영",
                            "물리치료 및 작업치료",
                            "인지기능 강화 프로그램",
                            "사회복지 상담",
                            "식사 및 영양 관리",
                            "개인 위생 관리"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility2);

            NursingFacility facility3 = NursingFacility.builder()
                    .name("e포근한요양원")
                    .address("솔샘로15다길 32")
                    .facilityNumber("02-6228-9987")
                    .latitude(37.6098)
                    .longitude(127.0114)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("쾌적하고 아늑한 환경에서 전문 의료진과 요양보호사가 어르신께 세심한 케어를 제공합니다.")
                    .operatingHours("평일 09:30 ~ 17:30 (공휴일 휴무)")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "치매전담실 운영",
                            "물리치료, 작업치료",
                            "여가활동 프로그램 (원예, 영화감상, 요리)",
                            "인지기능 향상 프로그램 (퍼즐, 조작활동)",
                            "개인 위생 및 건강 관리",
                            "영양 식단 제공",
                            "사회복지 지원"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility3);

            NursingFacility facility4 = NursingFacility.builder()
                    .name("청화요양원")
                    .address("서경로 67")
                    .facilityNumber("02-942-2255")
                    .latitude(37.6126)
                    .longitude(127.0182)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("중형 요양시설로, 쾌적한 환경에서 전문적인 돌봄과 재활 서비스를 제공합니다.")
                    .operatingHours("평일 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "주간보호",
                            "목욕 서비스",
                            "물리 및 작업치료",
                            "인지기능 향상 프로그램",
                            "사회복지 상담",
                            "식사 및 영양 관리"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility4);

            NursingFacility facility5 = NursingFacility.builder()
                    .name("길음안나데이케어센터")
                    .address("길음로9길 46")
                    .facilityNumber("02-940-3510")
                    .latitude(37.6087)
                    .longitude(127.0213)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양급여 수급자 및 노인성 질환 어르신을 위한 안전한 주야간 보호 시설로, 다양한 인지 기능 향상 및 재활 프로그램을 제공합니다.")
                    .operatingHours("월-금 08:30 ~ 17:30")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "인지기능 서비스(미술치료, 음악치료, 원예치료, 웃음치료)",
                            "기능회복 서비스(작업치료, 물리치료, 운동치료, 발마사지)",
                            "사회적응 서비스(나들이, 절기행사, 문화체험, 공연관람)",
                            "지역사회 연계 서비스(치매지원센터, 보건소 연계)",
                            "영양 서비스(중·석식, 간식 제공)",
                            "위생청결 서비스(목욕, 이·미용 서비스)",
                            "건강지원 서비스(혈압, 혈당 체크, 투약, 병원 진료 연계)",
                            "가족지원 서비스(가족 간담회 및 교육, 가족 참여 프로그램)",
                            "차량 운행"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility5);

            NursingFacility facility6 = NursingFacility.builder()
                    .name("성북미르사랑데이케어센터")
                    .address("보국문로 10")
                    .facilityNumber("02-919-9909")
                    .latitude(37.6160)
                    .longitude(127.0110)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양급여 수급자 및 노인성 질환 어르신에게 전문적인 주야간 보호와 다양한 인지 및 재활프로그램을 제공하는 공공 인가 시설입니다.")
                    .operatingHours("월-금 08:30 ~ 17:30")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "인지 기능 향상 프로그램",
                            "재활 치료 프로그램",
                            "사회적응 훈련",
                            "영양 관리 및 식사 제공",
                            "목욕 및 위생 서비스",
                            "건강관리 및 복약 지도",
                            "가족참여 프로그램",
                            "송영 서비스"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility6);

            NursingFacility facility7 = NursingFacility.builder()
                    .name("고구려복지센터")
                    .address("한천로 598")
                    .facilityNumber("02-6223-1008")
                    .latitude(37.6177)
                    .longitude(127.0377)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("재가노인복지 및 방문요양 전문 기관으로서, 어르신들의 일상생활 지원과 건강 관리를 돕습니다.")
                    .operatingHours("월-금 08:30 - 17:30")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "방문요양",
                            "방문목욕",
                            "일상생활 지원",
                            "건강관리 및 복약지도",
                            "사회복지 상담"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility7);

            NursingFacility facility8 = NursingFacility.builder()
                    .name("사랑주야간복지센터")
                    .address("보국문로17길 8")
                    .facilityNumber("02-913-8564")
                    .latitude(37.6137)  // 근사 좌표
                    .longitude(127.0077)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양급여 수급자 어르신들을 위한 전문 주야간 보호 및 다양한 인지·재활 프로그램을 운영합니다.")
                    .operatingHours("월-금 08:00 - 22:00, 주말 및 공휴일 별도 문의")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "인지기능 프로그램(미술, 음악, 웃음치료 등)",
                            "기능회복서비스(작업치료, 물리치료, 운동치료)",
                            "사회적응 프로그램(나들이, 문화체험, 행사 참여)",
                            "영양 및 식사 제공",
                            "목욕 및 위생 서비스",
                            "건강지원(혈압·혈당 체크, 복약지도)",
                            "가족참여 및 상담 프로그램",
                            "송영 서비스(차량 운행)"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility8);

            NursingFacility facility9 = NursingFacility.builder()
                    .name("다사랑노인재가센터")
                    .address("돌곶이로8가길 29")
                    .facilityNumber("02-962-5800")
                    .latitude(37.6184)
                    .longitude(127.0380)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("어르신들의 편안한 가정생활을 지원하는 방문요양 및 방문목욕 전문 기관입니다.")
                    .operatingHours("월-금 09:00 - 18:00, 주말 및 공휴일 휴무")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "방문요양 서비스",
                            "방문목욕 서비스",
                            "재가급여 지원",
                            "건강관리 및 복약지도",
                            "일상생활 지원",
                            "사회복지 상담"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility9);

            NursingFacility facility10 = NursingFacility.builder()
                    .name("부모섬김노인요양원")
                    .address("오패산로 98")
                    .facilityNumber("02-941-8890")
                    .latitude(37.6130)
                    .longitude(127.0292)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("소형 노인요양시설로 전문 요양보호사와 간호 인력을 통해 24시간 정성 어린 돌봄과 재활 치료를 제공합니다.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "주야간 보호",
                            "치매전담실 운영",
                            "물리치료 및 작업치료",
                            "방문 요양 서비스",
                            "영양 및 식사 제공",
                            "개인 위생 및 건강관리",
                            "사회복지 상담"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility10);

            NursingFacility facility11 = NursingFacility.builder()
                    .name("효성요양병원")
                    .address("삼양로 204")
                    .facilityNumber("02-988-9456")
                    .latitude(37.6341)
                    .longitude(127.0253)
                    .category(FacilityCategory.NURSING_HOSPITAL)
                    .description("내과, 신경과, 마취통증의학과 등 전문 진료 과목을 갖춘 1등급 요양병원입니다. 환자 맞춤 치료와 24시간 전문 간호 서비스를 제공합니다.")
                    .operatingHours("월-금 09:00 - 18:00 / 토요일 09:00 - 13:00 / 일 및 공휴일 휴무")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "24시간 입원 케어",
                            "재활치료",
                            "물리치료",
                            "한방 및 양방 협진",
                            "내과, 신경과 전문 진료",
                            "영양 관리 및 식사 제공",
                            "심리치료 및 미술 치료 프로그램"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility11);

            NursingFacility facility12 = NursingFacility.builder()
                    .name("의료법인참예원의료재단성북참요양병원")
                    .address("북악산로 1길 71")
                    .facilityNumber("02-912-2114")
                    .latitude(37.5944)
                    .longitude(127.0197)
                    .category(FacilityCategory.NURSING_HOSPITAL)
                    .description("보건복지부 인증의료기관으로, 항암치료, 재활치료, 만성병 관리 등을 포괄하는 통합요양병원입니다. 특히 암 치료 및 부작용 완화, 항암 면역 프로그램, 맞춤 식단 등 최고 수준의 서비스를 제공합니다.")
                    .operatingHours("월-금 09:00 - 17:30, 점심시간 12:00 - 13:00")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "암치료",
                            "재활치료",
                            "만성질환관리",
                            "고주파/면역치료",
                            "요양 및 방문진료",
                            "길상사 산책, 음악치료 등 힐링 프로그램"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility12);

            NursingFacility facility13 = NursingFacility.builder()
                    .name("성북희망데이케어센터")
                    .address("성북구 동소문로 190")
                    .facilityNumber("02-922-9955")
                    .latitude(37.5994)
                    .longitude(127.0183)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("노인장기요양급여 수급자를 위한 신뢰받는 주야간 보호시설로, 다양한 인지재활 및 복지 프로그램을 운영합니다.")
                    .operatingHours("월-금 08:30 - 17:30")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "인지재활 프로그램",
                            "운동요법 및 물리치료",
                            "사회적응 훈련과 문화활동",
                            "영양 관리와 식사 제공",
                            "건강 관리 및 복약 지도"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility13);

            NursingFacility facility14 = NursingFacility.builder()
                    .name("동서요양병원")
                    .address("동소문로 306")
                    .facilityNumber("02-942-3611")
                    .latitude(37.5999)
                    .longitude(127.0138)
                    .category(FacilityCategory.NURSING_HOSPITAL)
                    .description("내과, 신경과, 정형외과 등 전문 진료과목과 24시간 입원 케어를 제공하는 2등급 요양병원입니다.")
                    .operatingHours("월-금 09:00 - 18:00, 토요일 09:00 - 12:30, 일요일 및 공휴일 휴무")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "24시간 입원 케어",
                            "재활치료",
                            "물리치료",
                            "전문의 협진",
                            "정형외과 치료",
                            "내과 진료",
                            "영양 관리"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility14);

            NursingFacility facility15 = NursingFacility.builder()
                    .name("동소문데이케어센터")
                    .address("동소문로11길 18 101호")
                    .facilityNumber("02-926-7273")
                    .latitude(37.5991)
                    .longitude(127.0170)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양 급여수급자 및 노인성 질환 어르신을 위한 전문 주야간보호 서비스를 제공합니다.")
                    .operatingHours("월-금 08:00 - 22:00, 주말 및 공휴일 운영 문의 필요")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "인지기능 프로그램",
                            "미술·음악·원예치료",
                            "물리·작업치료 및 운동치료",
                            "영양식 및 간식 제공",
                            "목욕 및 위생 서비스",
                            "건강 관리 및 복약지도",
                            "송영서비스(차량 운행)",
                            "가족참여 프로그램 및 사회적응훈련"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility15);

            NursingFacility facility16 = NursingFacility.builder()
                    .name("일광노인요양센터")
                    .address("보문로31길 70")
                    .facilityNumber("02-742-0755")
                    .latitude(37.5865)
                    .longitude(127.0133)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("치매·중풍 등 노인성 질환자를 위한 맞춤형 요양서비스와 재활 프로그램을 운영합니다.")
                    .operatingHours("24시간 운영")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of("24시간 입소보호", "치매전담", "주야간보호", "재활프로그램", "식사 제공"))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility16);

            NursingFacility facility17 = NursingFacility.builder()
                    .name("삼선데이케어센터")
                    .address("동소문로 40")
                    .facilityNumber("02-6437-7272")
                    .latitude(37.5933)
                    .longitude(127.0188)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양 급여수급자, 인지지원 대상자를 위한 안전하고 전문적인 주야간 보호시설입니다.")
                    .operatingHours("월-금 08:00~22:00 / 주말 및 공휴일 운영(문의)")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "인지기능 프로그램",
                            "미술/음악/작업/원예치료",
                            "물리치료, 운동치료",
                            "영양식사 및 간식 제공",
                            "목욕 및 위생서비스",
                            "송영서비스(차량운행)",
                            "가족참여 프로그램",
                            "지역사회 연계 프로그램"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility17);

            NursingFacility facility18 = NursingFacility.builder()
                    .name("혜화동성당데이케어센터")
                    .address("혜화로12길 14-9")
                    .facilityNumber("02-742-0515")
                    .latitude(37.5795)
                    .longitude(127.0011)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양 급여수급자 및 치매노인을 위한 전문 주야간보호와 다양한 인지ㆍ재활 프로그램을 제공합니다.")
                    .operatingHours("월-금 08:00 - 22:00, 야간운영(18:00 - 22:00) 포함, 주말 및 공휴일 문의")
                    .imageUrls(List.of(imageUrls.get(2)))
                    .services(List.of(
                            "치매전담실 운영",
                            "미술·음악·원예치료",
                            "인지기능 향상 프로그램",
                            "물리·작업치료",
                            "식사·영양관리",
                            "건강관리와 복약지도",
                            "송영서비스(차량 제공)",
                            "사회적응훈련 및 가족참여 프로그램"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility18);

            NursingFacility facility19 = NursingFacility.builder()
                    .name("바른데이케어센터")
                    .address("보문로40길 11")
                    .facilityNumber("02-928-8890")
                    .latitude(37.6131)
                    .longitude(127.0190)
                    .category(FacilityCategory.DAYCARE_CENTER)
                    .description("장기요양급여 수급자 및 노인성 질환 어르신 대상으로 전문적인 주야간 보호와 인지기능강화 프로그램을 제공합니다.")
                    .operatingHours("월-금 08:00 - 22:00, 주말 및 공휴일 운영 문의")
                    .imageUrls(List.of(imageUrls.get(0)))
                    .services(List.of(
                            "인지기능 프로그램(미술, 음악, 원예 치료 등)",
                            "기능회복서비스(작업치료, 물리치료, 운동치료)",
                            "사회적응서비스(나들이, 문화체험, 공연관람 등)",
                            "영양서비스(중·석식 및 간식 제공)",
                            "위생청결서비스(목욕, 이·미용 서비스)",
                            "건강지원서비스(활력징후 체크, 병원진료 연결)",
                            "가족지원서비스(가족교육, 가족참여 프로그램)",
                            "송영서비스(차량 운행)"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility19);

            NursingFacility facility20 = NursingFacility.builder()
                    .name("다애요양원")
                    .address("성북로4길 52")
                    .facilityNumber("02-921-3131")
                    .latitude(37.6012)
                    .longitude(127.0207)
                    .category(FacilityCategory.NURSING_HOME)
                    .description("사랑과 정성으로 모시는 종합 요양시설로, 다양한 문화공연과 종교활동, 전문적인 신체기능 관리 프로그램을 운영합니다.")
                    .operatingHours("월-금 09:00 - 18:00")
                    .imageUrls(List.of(imageUrls.get(1)))
                    .services(List.of(
                            "주간 및 야간 보호",
                            "전문 간호 및 간병 서비스",
                            "물리치료 및 재활 프로그램",
                            "문화공연 및 사회 활동",
                            "종교활동 지원",
                            "식사 및 영양 관리",
                            "개인 위생 관리"
                    ))
                    .reviewCount(0L)
                    .averageRating(BigDecimal.ZERO)
                    .build();
            nursingFacilityRepository.save(facility20);
        }
    }
}
