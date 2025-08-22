package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.center_marker_service.*;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.exception.DuplicateLikeException;
import com.bulmansoda.map_community.exception.UserNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Comment;
import com.bulmansoda.map_community.model.Like;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CenterMarkerService {

    private final UserRepository userRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CenterMarkerService(UserRepository userRepository, CenterMarkerRepository centerMarkerRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    public InsideCenterMarkerResponse openCenterMarker(OpenCenterMarkerRequest request) {
        long userId = request.getUserId();
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));
        return new InsideCenterMarkerResponse(userId, center);
    }

    public long likeCenterMarker(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        CenterMarker center = centerMarkerRepository.findById(request.getCenterMarkerId())
                .orElseThrow(() -> new CenterMarkerNotFoundException(request.getCenterMarkerId()));

        if (likeRepository.findByUserAndCenterMarker(user,center).isPresent()) {
            throw new DuplicateLikeException("User has already liked it");
        }

        Like like = new Like();
        like.setUser(user);
        like.setCenterMarker(center);
        likeRepository.save(like);

        return like.getId();
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

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
