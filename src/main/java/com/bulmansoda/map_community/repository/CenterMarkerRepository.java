package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.CenterMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterMarkerRepository extends JpaRepository<CenterMarker, Long> {
    @Query("SELECT cm FROM CenterMarker cm WHERE " +
            "cm.latitude BETWEEN :minLat AND :maxLat AND " +
            "cm.longitude BETWEEN :minLng AND :maxLng")
    List<CenterMarker> findCenterMarkersInArea(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng
    );
}
