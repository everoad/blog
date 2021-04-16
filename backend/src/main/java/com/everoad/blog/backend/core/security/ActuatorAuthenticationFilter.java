package com.everoad.blog.backend.core.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class ActuatorAuthenticationFilter extends GenericFilterBean {

  private final String username;
  private final String password;
  private final String[] roles;

  public ActuatorAuthenticationFilter(String username, String password, String[] roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String[] headers = parseHeader(request);
    if (headers.length == 2) {
      String username = headers[0];
      String password = headers[1];
      if (isActuatorUser(username, password)) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities(Arrays.asList(roles)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }

  /**
   * Basic Authentication 처리.
   */
  private String[] parseHeader(ServletRequest request) {
    String basic = "Basic ";
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(authorization) && authorization.contains(basic)) {
      String encodeString = authorization.replace(basic, "");
      String decodeString = new String(Base64.getDecoder().decode(encodeString), StandardCharsets.UTF_8);
      return decodeString.split(":");
    }
    return new String[]{};
  }

  /**
   * username, password 체크.
   */
  private boolean isActuatorUser(String username, String password) {
    if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
      return false;
    }
    return username.equals(this.username) && password.equals(this.password);
  }

  /**
   * GrantedAuthority 생성.
   */
  private Collection<? extends GrantedAuthority> authorities(Collection<String> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
  }

}
