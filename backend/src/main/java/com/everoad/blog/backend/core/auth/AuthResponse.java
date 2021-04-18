package com.everoad.blog.backend.core.auth;

import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class AuthResponse {

  private String accessToken;
  private String refreshToken;
  private long accessTokenExpiredAt;
  private long refreshTokenExpiredAt;

  public AuthResponse(MemberConnection connection) {
    this.accessToken = connection.getAccessToken();
    this.refreshToken = connection.getRefreshToken();
    this.accessTokenExpiredAt = toMilliseconds(connection.getAccessTokenExpiredAt());
    this.refreshTokenExpiredAt = toMilliseconds(connection.getRefreshTokenExpiredAt());
  }

  private long toMilliseconds(LocalDateTime time) {
    return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

}
