package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.exception.ExceptionResponse;
import com.everoad.blog.backend.core.lib.MyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    ExceptionResponse body = ExceptionResponse.create(authException.getMessage());
    MyUtils.writeResponseJSON(response, new ApiResponse<>(false, HttpStatus.UNAUTHORIZED, body));
  }

}
