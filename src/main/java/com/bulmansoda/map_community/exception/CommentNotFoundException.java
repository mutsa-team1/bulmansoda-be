package com.bulmansoda.map_community.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Comment Not Found with id: " + id);
    }
}
