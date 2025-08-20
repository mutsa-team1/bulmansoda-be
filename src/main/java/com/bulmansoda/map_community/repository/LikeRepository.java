package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
