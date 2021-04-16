package com.everoad.blog.backend.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

}
