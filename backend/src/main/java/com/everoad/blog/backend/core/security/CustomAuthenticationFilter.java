package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.auth.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Getter
@Setter
public class CustomAuthenticationFilter extends AbstractCustomAuthenticationFilter {

  private ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    // 로그인 정보.
    AuthRequest auth = getAuthRequest(request);
    if (log.isDebugEnabled()) {
      log.debug("[ AuthRequest ] username : {}, password: {}", auth.getUsername(), StringUtils.hasText(auth.getPassword()));
    }
    // 유저 정보 체크.
    if (!StringUtils.hasText(auth.getUsername()) || !StringUtils.hasText(auth.getPassword())) {
      throw new CustomAuthenticationException("Full authentication is required to access this resource.");
    }
    // 로그인 처리.
    Authentication authentication = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword(), null);
    return getAuthenticationManager().authenticate(authentication);
  }


  private AuthRequest getAuthRequest(HttpServletRequest request) {
    try {
      return objectMapper.readValue(request.getReader(), AuthRequest.class);
    } catch (IOException ignored) {
      return new AuthRequest();
    }
  }

}
