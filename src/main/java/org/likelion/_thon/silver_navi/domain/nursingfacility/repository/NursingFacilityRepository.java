package org.likelion._thon.silver_navi.domain.nursingfacility.repository;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NursingFacilityRepository extends JpaRepository<NursingFacility, Long> {

    Optional<NursingFacility> findByName(String name);
    
    List<NursingFacility> findByNameContaining(String name);

    @Query(value = """
        SELECT DISTINCT nf.*
        FROM nursing_facility nf
        WHERE nf.latitude BETWEEN :minLat AND :maxLat
          AND nf.longitude BETWEEN :minLng AND :maxLng
          AND ST_Distance_Sphere(POINT(nf.longitude, nf.latitude), POINT(:lng, :lat)) <= :radius * 1000
        ORDER BY ST_Distance_Sphere(POINT(nf.longitude, nf.latitude), POINT(:lng, :lat))
        """, nativeQuery = true)
    List<NursingFacility> findNearbyFacilities(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radius, // km 단위
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng
    );
}
