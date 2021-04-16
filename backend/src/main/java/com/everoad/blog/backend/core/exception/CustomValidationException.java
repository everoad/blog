package com.everoad.blog.backend.core.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class CustomValidationException extends RuntimeException {

  private final Errors errors;

  public CustomValidationException(Errors errors) {
    this.errors = errors;
  }

}
