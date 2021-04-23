package com.everoad.blog.backend.domain.memberConnection;

import com.everoad.blog.backend.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberConnectionRepository extends JpaRepository<MemberConnection, Long> {

  Optional<MemberConnection> findByMemberAndAccessTokenExpiredAtIsAfter(Member member, LocalDateTime now);

  @EntityGraph(attributePaths = {"member", "member.roles"})
  Optional<MemberConnection> findByAccessToken(String accessToken);

  Optional<MemberConnection> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

}
