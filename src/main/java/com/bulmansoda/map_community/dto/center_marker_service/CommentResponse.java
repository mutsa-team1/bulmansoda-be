package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.Comment;
import com.bulmansoda.map_community.model.CommentLike;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class CommentResponse {

    private long commentId;

    private String name;

    private long userId;

    private String content;

    private int likeCount;

    private boolean isLiked;

    private Timestamp createdAt;

    public CommentResponse(long userId, Comment comment) {
        this.commentId = comment.getId();
        this.name = comment.getUser().getName();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.likeCount = comment.getCommentLikes().size();
        this.isLiked = false;
        for (CommentLike commentLike : comment.getCommentLikes()) {
         if(commentLike.getUser().getId() == userId) {
             this.isLiked = true;
             break;
         }
        }
        this.createdAt = comment.getCreatedAt();
    }

}
