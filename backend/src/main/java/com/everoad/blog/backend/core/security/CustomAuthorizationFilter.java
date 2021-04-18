package com.everoad.blog.backend.core.security;


import com.everoad.blog.backend.core.auth.AuthTokenProvider;
import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import com.everoad.blog.backend.service.MemberConnectionService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Getter
@Setter
public class CustomAuthorizationFilter extends GenericFilterBean {

  private AuthenticationEntryPoint authenticationEntryPoint;
  private MemberConnectionService memberConnectionService;
  private AuthTokenProvider authTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, AuthenticationException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    try {
      String token = authTokenProvider.parseToken(httpServletRequest);
      if (StringUtils.hasText(token)) {
        MemberConnection connection = memberConnectionService.selectConnectionByAccessToken(token)
            .orElseThrow(() -> new CustomAuthenticationException("Access token is invalid"));
        if (authTokenProvider.isExpired(connection.getAccessTokenExpiredAt())) {
          throw new CustomAuthenticationException("Access token is expired");
        }
        MemberAdapter memberAdapter = new MemberAdapter(connection.getMember());
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberAdapter, memberAdapter.getPassword(), memberAdapter.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      chain.doFilter(request, response);
    } catch (AuthenticationException exception) {
      this.authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, exception);
    }
  }
}

