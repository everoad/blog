package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.exception.ExceptionResponse;
import com.everoad.blog.backend.core.lib.MyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    HttpStatus status;
    if (authentication != null) {
      status = HttpStatus.FORBIDDEN;
    } else {
      status = HttpStatus.UNAUTHORIZED;
    }
    ExceptionResponse exceptionResponse = ExceptionResponse.create(accessDeniedException.getMessage());
    MyUtils.writeResponseJSON(response, new ApiResponse<>(false, status, exceptionResponse));
  }

}
