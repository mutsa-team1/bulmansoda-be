package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.center_marker_service.*;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.exception.CommentNotFoundException;
import com.bulmansoda.map_community.exception.DuplicateLikeException;
import com.bulmansoda.map_community.exception.UserNotFoundException;
import com.bulmansoda.map_community.model.*;
import com.bulmansoda.map_community.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public InsideCenterMarkerResponse openCenterMarker(OpenCenterMarkerRequest request) {
        long userId = request.getUserId();
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));
        return new InsideCenterMarkerResponse(userId, center);
    }

    public long likeCenterMarker(LikeCenterMarkerRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));

        if (centerMarkerLikeRepository.findByUserAndCenterMarker(user,center).isPresent()) {
            throw new DuplicateLikeException("User has already liked this center marker");
        }

        CenterMarkerLike centerMarkerLike = new CenterMarkerLike();
        centerMarkerLike.setUser(user);
        centerMarkerLike.setCenterMarker(center);
        centerMarkerLikeRepository.save(centerMarkerLike);

        return centerMarkerLike.getId();
    }

    public long commentCenterMarker(CommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCenterMarker(center);
        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return comment.getId();
    }

    public long likeComment(LikeCommentRequest likeCommentRequest) {
        User user = userRepository.findById(likeCommentRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(likeCommentRequest.getUserId()));
        Comment comment = commentRepository.findById(likeCommentRequest.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(likeCommentRequest.getCommentId()));

        if(commentLikeRepository.findByUserAndComment(user, comment).isPresent()) {
         throw new DuplicateLikeException("User has already liked this comment");
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLikeRepository.save(commentLike);

        return commentLike.getId();
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
