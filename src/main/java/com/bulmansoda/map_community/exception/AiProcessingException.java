package com.bulmansoda.map_community.exception;

public class AiProcessingException extends RuntimeException {

  public AiProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

  public AiProcessingException(String message) {super(message);}
}

