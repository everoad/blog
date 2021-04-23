package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.auth.AuthTokenProvider;
import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.MyUtils;
import com.everoad.blog.backend.domain.member.MemberRepository;
import com.everoad.blog.backend.service.MemberConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  private final MemberConnectionService connectionService;
  private final AuthTokenProvider authTokenProvider;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException, ServletException {
    String accessToken = authTokenProvider.parseToken(request);
    connectionService.deleteConnectionByAccessToken(accessToken);
    MyUtils.writeResponseJSON(response, new ApiResponse<>());
  }

}
