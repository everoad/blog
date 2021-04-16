package com.everoad.blog.backend.core.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExceptionResponse {

  private final List<String> messages = new ArrayList<>();

  /**
   * 별도로 message 를 만들 경우.
   */
  public static ExceptionResponse create(String message) {
    ExceptionResponse body = new ExceptionResponse();
    body.messages.add(message);
    return body;
  }

  /**
   * default validation
   */
  public static ExceptionResponse create(BindingResult bindingResult) {
    ExceptionResponse body = new ExceptionResponse();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      body.messages.add(fieldError.getDefaultMessage());
    }
    return body;
  }


  /**
   * custom validation
   */
  public static ExceptionResponse create(Errors errors) {
    ExceptionResponse body = new ExceptionResponse();
    for (FieldError fieldError : errors.getFieldErrors()) {
      body.messages.add(fieldError.getDefaultMessage());
    }
    for (ObjectError fieldError : errors.getGlobalErrors()) {
      body.messages.add(fieldError.getDefaultMessage());
    }
    return body;
  }


  public static ExceptionResponse create(ResponseStatusException ex) {
    ExceptionResponse body = new ExceptionResponse();
    body.messages.add(ex.getReason());
    return body;
  }


  public static ExceptionResponse create(HttpStatus status) {
    ExceptionResponse body = new ExceptionResponse();
    body.messages.add(getStatusMessage(status.value()));
    return body;
  }

  public static String getStatusMessage(int code) {
    switch (code) {
      case 404:
        return "페이지를 찾을 수 없습니다.";
      case 403:
        return "접근 권한이 없습니다.";
      default:
        return "서버 문제로 정상 처리되지 않았습니다.\n관리자에게 문의해 주시기 바랍니다.";
    }
  }

}
