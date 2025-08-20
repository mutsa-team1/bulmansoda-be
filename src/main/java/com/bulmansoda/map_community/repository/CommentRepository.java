package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
