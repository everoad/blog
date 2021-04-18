package com.everoad.blog.backend.core.exception;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.Const;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestControllerAdvice(basePackages = {"com.everoad.blog.backend.controller"})
@RequiredArgsConstructor
public class ExceptionRestControllerAdvice {


  private final ObjectMapper objectMapper;


  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<ExceptionResponse>> businessException(HttpServletRequest request, BusinessException exception) {
    printLog(request, exception);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionResponse body = ExceptionResponse.create(status);
    return createResponseEntity(status, body);
  }


  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<ApiResponse<ExceptionResponse>> myValidationException(HttpServletRequest request, CustomValidationException exception) {
    if (log.isDebugEnabled()) {
      printLog(request, exception);
    }
    ExceptionResponse body = ExceptionResponse.create(exception.getErrors());
    return createResponseEntity(HttpStatus.BAD_REQUEST, body);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<ExceptionResponse>> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
    if (log.isDebugEnabled()) {
      printLog(request, exception);
    }
    ExceptionResponse body = ExceptionResponse.create(exception.getBindingResult());
    return createResponseEntity(HttpStatus.BAD_REQUEST, body);
  }


  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<ExceptionResponse>> responseStatusException(HttpServletRequest request, ResponseStatusException exception) {
    if (log.isDebugEnabled()) {
      printLog(request, exception);
    }
    ExceptionResponse body = ExceptionResponse.create(exception);
    return createResponseEntity(exception.getStatus(), body);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<ExceptionResponse>> exception(HttpServletRequest request, Exception exception) {
    printLog(request, exception);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionResponse body = ExceptionResponse.create(status);
    return createResponseEntity(status, body);
  }


  private ResponseEntity<ApiResponse<ExceptionResponse>> createResponseEntity(HttpStatus status, ExceptionResponse body) {
    ApiResponse<ExceptionResponse> apiResponse = new ApiResponse<>(false, status, body);
    return ResponseEntity.status(status)
        .header(HttpHeaders.CONTENT_TYPE, Const.APPLICATION_JSON_VALUE)
        .body(apiResponse);
  }


  private void printLog(HttpServletRequest request, String exceptionMessage) {
    String body = String.valueOf(request.getAttribute(Const.REQUEST_BODY));
    String query = convertJsonToString(getQuery(request));
    log.error("[ ERROR ] {} {} {} {} {}", request.getMethod(), request.getRequestURI(), query, body, exceptionMessage);
  }


  private void printLog(HttpServletRequest request, Exception exception) {
    String body = String.valueOf(request.getAttribute(Const.REQUEST_BODY));
    String query = convertJsonToString(getQuery(request));
    log.error("[ ERROR ] {} {} {} {} {}", request.getMethod(), request.getRequestURI(), query, body, exception);
  }


  private Map<String, Object> getQuery(HttpServletRequest request) {
    Map<String, Object> parameters = new HashMap<>();
    Enumeration<String> requestParameterNames = request.getParameterNames();
    String name;
    String[] parameterValues;
    while (requestParameterNames.hasMoreElements()) {
      name = requestParameterNames.nextElement();
      parameterValues = request.getParameterValues(name);
      if (parameterValues.length == 1) {
        parameters.put(name, convertStringToJson(parameterValues[0]));
      } else {
        List<Object> values = new ArrayList<>();
        for (String parameterValue : parameterValues) {
          values.add(convertStringToJson(parameterValue));
        }
        parameters.put(name, values);
      }
    }
    return parameters;
  }


  private String convertJsonToString(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException ignored) {
      return obj.toString();
    }
  }


  private Object convertStringToJson(String str) {
    str = str.trim();
    try {
      return objectMapper.readValue(str, new TypeReference<Map<String, Object>>() {
      });
    } catch (JsonProcessingException ignored) {
      return str;
    }
  }


}