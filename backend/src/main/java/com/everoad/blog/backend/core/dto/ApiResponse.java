package com.everoad.blog.backend.core.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
public class ApiResponse<T> {

  private final boolean success;
  private final int code;
  private int draw;
  private T body;

  public ApiResponse() {
    this.success = true;
    this.code = HttpStatus.OK.value();
  }

  public ApiResponse(T body) {
    this.success = true;
    this.code = HttpStatus.OK.value();
    this.body = body;
  }

  public ApiResponse(T body, int draw) {
    this.success = true;
    this.code = HttpStatus.OK.value();
    this.body = body;
    this.draw = draw;
  }

  public ApiResponse(boolean success, int code, T body) {
    this.success = success;
    this.code = code;
    this.body = body;
  }

  public ApiResponse(boolean success, HttpStatus status, T body) {
    this.success = success;
    this.code = status.value();
    this.body = body;
  }

}
