package org.likelion._thon.silver_navi.domain.search.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.repository.FacilityBookmarkRepository;
import org.likelion._thon.silver_navi.domain.bookmark.repository.ProgramBookmarkRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;
import org.likelion._thon.silver_navi.global.web.dto.IntegratedSearchRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {
    private final ProgramRepository programRepository;
    private final NursingFacilityRepository facilityRepository;
    private final FacilityBookmarkRepository facilityBookmarkRepository;
    private final ProgramBookmarkRepository programBookmarkRepository;

    @Override
    public IntegratedSearchRes search(String keyword, User user) {
        // 프로그램 검색
        List<Program> programs = programRepository.findByNameContaining(keyword);
        List<UserByProgramListRes> programResList = programs.stream()
                .map(p -> UserByProgramListRes.from(
                        p,
                        p.getImageUrls().isEmpty()
                                ? null
                                : p.getImageUrls().getFirst(),
                        programBookmarkRepository.existsByUser_IdAndProgram_Id(user.getId(), p.getId())
                ))
                .toList();

        // 시설 검색
        List<NursingFacility> facilities = facilityRepository.findByNameContaining(keyword);
        List<NearbyFacilityRes> facilityResList = facilities.stream()
                .map(facility -> {
                    double distanceKm = GeoUtils.calculateDistance(
                            user.getLatitude(),
                            user.getLongitude(),
                            facility.getLatitude(),
                            facility.getLongitude()
                    );

                    boolean bookmarked = facilityBookmarkRepository.existsByUser_IdAndFacility_Id(user.getId(), facility.getId());

                    return NearbyFacilityRes.of(facility, distanceKm, bookmarked);
                })
                .toList();

        return IntegratedSearchRes.of(programResList, facilityResList);
    }
}
