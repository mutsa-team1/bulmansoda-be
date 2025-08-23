package com.bulmansoda.map_community.repository;

import com.bulmansoda.map_community.model.Comment;
import com.bulmansoda.map_community.model.CommentLike;
import com.bulmansoda.map_community.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
