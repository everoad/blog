package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.core.auth.AuthResponse;
import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.MyUtils;
import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import com.everoad.blog.backend.service.MemberConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final MemberConnectionService connectionService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    // 사용자.
    MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();
    Member member = memberAdapter.getMember();

    MemberConnection connection;
    Optional<MemberConnection> optionalConnection = connectionService.selectConnectionByMember(member);
    if (optionalConnection.isEmpty()) {
      connection = connectionService.insertConnection(member);
    } else {
      connection = optionalConnection.get();
    }
    // 응답.
    MyUtils.writeResponseJSON(response, new ApiResponse<>(new AuthResponse(connection)));
  }

}
