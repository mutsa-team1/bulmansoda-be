package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Like;
import com.bulmansoda.map_community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndCenterMarker(User user, CenterMarker centerMarker);
}
