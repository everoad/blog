package com.everoad.blog.backend.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

  Optional<Member> findByIdAndUseIsTrue(String id);

}