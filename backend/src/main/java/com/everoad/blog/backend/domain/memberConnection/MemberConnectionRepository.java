package com.everoad.blog.backend.domain.memberConnection;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberConnectionRepository extends JpaRepository<MemberConnection, Long> {

  Optional<MemberConnection> findByMemberId(String memberId);

  @EntityGraph(attributePaths = {"member", "member.roles"})
  Optional<MemberConnection> findByAccessToken(String accessToken);

  Optional<MemberConnection> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

}
