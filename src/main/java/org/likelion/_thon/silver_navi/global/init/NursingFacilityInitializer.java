package org.likelion._thon.silver_navi.global.init;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NursingFacilityInitializer implements ApplicationRunner {

    private final NursingFacilityRepository nursingFacilityRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // 요양 시설 데이터가 이미 있는지 확인
        if (nursingFacilityRepository.count() == 0) {

            // --- 1. 성신노인요양원 ---
            NursingFacility facility1 = NursingFacility.builder()
                    // 기본 정보
                    .name("성신노인요양원")
                    .address("서울시 성북구 정릉동 123-45")
                    .facilityNumber("0507-1335-8538")
                    .latitude(37.6021)
                    .longitude(127.0045)
                    .build();
            nursingFacilityRepository.save(facility1);

            // --- 2. 행복주간보호센터 ---
            NursingFacility facility2 = NursingFacility.builder()
                    // 기본 정보
                    .name("행복주간보호센터")
                    .address("서울시 은평구 불광동 50-1")
                    .facilityNumber("02-1234-5678")
                    .latitude(37.6100)
                    .longitude(126.9300)
                    .build();
            nursingFacilityRepository.save(facility2);

            // --- 3. 서울요양병원 ---
            NursingFacility facility3 = NursingFacility.builder()
                    // 기본 정보
                    .name("서울요양병원")
                    .address("서울시 강남구 역삼동 789-10")
                    .facilityNumber("02-9876-5432")
                    .latitude(37.5000)
                    .longitude(127.0300)
                    .build();
            nursingFacilityRepository.save(facility3);

            // --- 4. 마포실버케어센터 ---
            NursingFacility facility4 = NursingFacility.builder()
                    .name("마포실버케어센터")
                    .address("서울시 마포구 상수동 33-3")
                    .facilityNumber("02-3333-1111")
                    .latitude(37.5480)
                    .longitude(126.9200)
                    .build();
            nursingFacilityRepository.save(facility4);

            // --- 5. 서초사랑요양병원 ---
            NursingFacility facility5 = NursingFacility.builder()
                    .name("서초사랑요양병원")
                    .address("서울시 서초구 서초동 500-1")
                    .facilityNumber("02-5555-8888")
                    .latitude(37.4900)
                    .longitude(127.0100)
                    .build();
            nursingFacilityRepository.save(facility5);

            // --- 6. 송파한마음요양원 ---
            NursingFacility facility6 = NursingFacility.builder()
                    .name("송파한마음요양원")
                    .address("서울시 송파구 잠실동 77-7")
                    .facilityNumber("0507-1300-2000")
                    .latitude(37.5100)
                    .longitude(127.0800)
                    .build();
            nursingFacilityRepository.save(facility6);

            // --- 7. 영등포늘봄주간보호센터 ---
            NursingFacility facility7 = NursingFacility.builder()
                    .name("영등포늘봄주간보호센터")
                    .address("서울시 영등포구 여의도동 10-1")
                    .facilityNumber("02-7777-9999")
                    .latitude(37.5250)
                    .longitude(126.9250)
                    .build();
            nursingFacilityRepository.save(facility7);

            // --- 8. 종로효요양원 ---
            NursingFacility facility8 = NursingFacility.builder()
                    .name("종로효요양원")
                    .address("서울시 종로구 평창동 12-3")
                    .facilityNumber("02-6666-3456")
                    .latitude(37.6100)
                    .longitude(126.9700)
                    .build();
            nursingFacilityRepository.save(facility8);

            // --- 9. 노원힐링요양병원 ---
            NursingFacility facility9 = NursingFacility.builder()
                    .name("노원힐링요양병원")
                    .address("서울시 노원구 상계동 200-5")
                    .facilityNumber("0507-1400-5000")
                    .latitude(37.6600)
                    .longitude(127.0700)
                    .build();
            nursingFacilityRepository.save(facility9);

            // --- 10. 강동자애요양원 ---
            NursingFacility facility10 = NursingFacility.builder()
                    .name("강동자애요양원")
                    .address("서울시 강동구 성내동 30-1")
                    .facilityNumber("02-4000-8282")
                    .latitude(37.5300)
                    .longitude(127.1300)
                    .build();
            nursingFacilityRepository.save(facility10);
        }
    }
}
