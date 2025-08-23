package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.CenterMarkerLike;
import com.bulmansoda.map_community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterMarkerLikeRepository extends JpaRepository<CenterMarkerLike, Long> {

    Optional<CenterMarkerLike> findByUserAndCenterMarker(User user, CenterMarker centerMarker);
}
