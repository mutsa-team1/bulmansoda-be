package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.center_marker_service.*;
import com.bulmansoda.map_community.exception.*;
import com.bulmansoda.map_community.model.*;
import com.bulmansoda.map_community.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;


@Service
@Transactional
public class CenterMarkerService {

    private final UserRepository userRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    private final CenterMarkerLikeRepository centerMarkerLikeRepository;

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public CenterMarkerService(UserRepository userRepository, CenterMarkerRepository centerMarkerRepository, CenterMarkerLikeRepository centerMarkerLikeRepository, CommentRepository commentRepository, CommentLikeRepository commentLikeRepository) {
        this.userRepository = userRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.centerMarkerLikeRepository = centerMarkerLikeRepository;
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    public InsideCenterMarkerResponse openCenterMarker(long userId, long centerMarkerId) {
        CenterMarker center = centerMarkerRepository.findById(centerMarkerId)
                .orElseThrow(() -> new CenterMarkerNotFoundException(centerMarkerId));
        return new InsideCenterMarkerResponse(userId, center);
    }

    public long likeCenterMarker(Long userId, Long centerMarkerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        CenterMarker center = centerMarkerRepository.findById(centerMarkerId)
                .orElseThrow(() -> new CenterMarkerNotFoundException(centerMarkerId));

        if (centerMarkerLikeRepository.findByUserAndCenterMarker(user,center).isPresent()) {
            throw new DuplicateLikeException("User has already liked this center marker");
        }

        CenterMarkerLike centerMarkerLike = new CenterMarkerLike();
        centerMarkerLike.setUser(user);
        centerMarkerLike.setCenterMarker(center);
        centerMarkerLikeRepository.save(centerMarkerLike);

        center.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        centerMarkerRepository.save(center);

        return centerMarkerLike.getId();
    }

    public long commentCenterMarker(Long userId, CommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCenterMarker(center);
        comment.setContent(request.getContent());
        commentRepository.save(comment);

        center.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        centerMarkerRepository.save(center);

        return comment.getId();
    }

    public long likeComment(long userId, long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if(commentLikeRepository.findByUserAndComment(user, comment).isPresent()) {
         throw new DuplicateLikeException("User has already liked this comment");
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLikeRepository.save(commentLike);

        CenterMarker center = comment.getCenterMarker();
        if (center != null) {
            center.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            centerMarkerRepository.save(center);
        }

        return commentLike.getId();
    }

    public void deleteComment(long userId, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (comment.getUser().getId() != userId) {
            throw new AuthorizationException("User is not authorized to delete this comment.");
        }

        commentRepository.deleteById(commentId);
    }
}
