package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
