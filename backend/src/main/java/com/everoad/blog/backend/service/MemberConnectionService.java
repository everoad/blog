package com.everoad.blog.backend.service;

import com.everoad.blog.backend.core.auth.AuthToken;
import com.everoad.blog.backend.core.auth.AuthTokenProvider;
import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import com.everoad.blog.backend.domain.memberConnection.MemberConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberConnectionService {

  private final MemberConnectionRepository connectionRepository;
  private final AuthTokenProvider authTokenProvider;

  @Transactional
  public MemberConnection insertConnection(Member member) {
    AuthToken authToken = authTokenProvider.createAuthToken(member.getId());
    MemberConnection connection = new MemberConnection(member, authToken);
    connectionRepository.save(connection);
    return connection;
  }

  public Optional<MemberConnection> selectConnectionByAccessToken(String accessToken) {
    return connectionRepository.findByAccessToken(accessToken);
  }

  public Optional<MemberConnection> selectConnectionByAccessTokenAndRefreshToken(String accessToken, String refreshToken) {
    return connectionRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken);
  }

  @Transactional
  public void deleteConnection(MemberConnection connection) {
    connectionRepository.deleteById(connection.getId());
  }
}
