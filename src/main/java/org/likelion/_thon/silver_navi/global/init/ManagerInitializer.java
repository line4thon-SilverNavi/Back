package org.likelion._thon.silver_navi.global.init;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.likelion._thon.silver_navi.domain.manager.repository.ManagerRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.global.auth.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(2)
public class ManagerInitializer implements CommandLineRunner {

    private final ManagerRepository managerRepository;
    private final NursingFacilityRepository nursingFacilityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (managerRepository.count() == 0) {
            NursingFacility facility2 = nursingFacilityRepository.findById(2L)
                    .orElse(null);

            if (facility2 != null) {
                String loginId = "demo2";
                String password = "demo2!";
                String encodedPassword = passwordEncoder.encode(password);

                Manager adminManager = Manager.builder()
                        .loginId(loginId)
                        .password(encodedPassword)
                        .nursingFacility(facility2)
                        .role(UserRole.ADMIN)
                        .build();

                managerRepository.save(adminManager);
            }

            // ----------

            NursingFacility facility8 = nursingFacilityRepository.findById(8L)
                    .orElse(null);

            if (facility8 != null) {
                String loginId = "demo8";
                String password = "demo8!";
                String encodedPassword = passwordEncoder.encode(password);

                Manager adminManager = Manager.builder()
                        .loginId(loginId)
                        .password(encodedPassword)
                        .nursingFacility(facility8)
                        .role(UserRole.ADMIN)
                        .build();

                managerRepository.save(adminManager);
            }

            // ----------

            NursingFacility facility10 = nursingFacilityRepository.findById(10L)
                    .orElse(null);

            if (facility10 != null) {
                String loginId = "demo10";
                String password = "demo10!";
                String encodedPassword = passwordEncoder.encode(password);

                Manager adminManager = Manager.builder()
                        .loginId(loginId)
                        .password(encodedPassword)
                        .nursingFacility(facility10)
                        .role(UserRole.ADMIN)
                        .build();

                managerRepository.save(adminManager);
            }
        }
    }
}
