package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.auth.AuthToken;
import com.everoad.blog.backend.core.auth.AuthTokenProvider;
import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import com.everoad.blog.backend.service.MemberConnectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@Setter
public class RefreshTokenFilter extends AbstractCustomAuthenticationFilter {

  private MemberConnectionService connectionService;
  private AuthTokenProvider authTokenProvider;
  private ObjectMapper objectMapper;

  @Override
  protected Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    AuthToken authToken = getAuthToken(request);
    MemberConnection connection = connectionService.selectConnectionByAccessTokenAndRefreshToken(authToken.getAccessToken(), authToken.getRefreshToken())
        .orElseThrow(() -> new CustomAuthenticationException("Invalid refresh token."));
    if (authTokenProvider.isExpired(connection.getRefreshTokenExpiredAt())) {
      throw new CustomAuthenticationException("Refresh token is expired.");
    }
    connectionService.deleteConnection(connection);
    MemberAdapter memberAdapter = new MemberAdapter(connection.getMember());
    return new UsernamePasswordAuthenticationToken(memberAdapter, memberAdapter.getPassword(), memberAdapter.getAuthorities());
  }


  private AuthToken getAuthToken(HttpServletRequest request) {
    try {
      return objectMapper.readValue(request.getReader(), AuthToken.class);
    } catch (Exception ignored) {
      return new AuthToken();
    }
  }

}
