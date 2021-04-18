package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.exception.ExceptionResponse;
import com.everoad.blog.backend.core.lib.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
    ExceptionResponse body = ExceptionResponse.create(exception.getMessage());
    MyUtils.writeResponseJSON(response, new ApiResponse<>(false, HttpStatus.UNAUTHORIZED, body));
  }

}
