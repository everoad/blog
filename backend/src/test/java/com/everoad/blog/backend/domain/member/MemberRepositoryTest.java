package com.everoad.blog.backend.domain.member;

import com.everoad.blog.backend.dto.member.MemberSaveDto;
import com.everoad.blog.backend.util.GenerateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @WithUserDetails("tester")
  @Test
  public void 사용자_등록() throws Exception {
    //given
    int key = 0;
    MemberSaveDto memberSaveDto = GenerateUtils.generateMemberDto(key);

    //when
    Member member = memberSaveDto.toEntity(Set.of(MemberRole.ADMIN));
    memberRepository.save(member);

    //then
    assert member.getId() != null;
    Optional<Member> optionalMember = memberRepository.findById(member.getId());

    assertTrue(optionalMember.isPresent());
    assertEquals(optionalMember.get().getId(), memberSaveDto.getId());
    assertEquals(optionalMember.get().getName(), memberSaveDto.getName());
  }

}