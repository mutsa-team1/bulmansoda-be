package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    @Query("SELECT m FROM Marker m WHERE " +
            "m.latitude BETWEEN :minLat AND :maxLat AND " +
            "m.longitude BETWEEN :minLng AND :maxLng")
    List<Marker> findMarkersInArea(
        @Param("minLat") double minLat,
        @Param("maxLat") double maxLat,
        @Param("minLng") double minLng,
        @Param("maxLng") double maxLng
    );

    @Modifying
    @Query("DELETE FROM Marker m WHERE m.centerMarker = :centerMarker")
    void deleteAllByCenterMarker(@Param("centerMarker")CenterMarker centerMarker);
}
