package com.everoad.blog.backend.service;

import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRepository;
import com.everoad.blog.backend.domain.member.MemberRole;
import com.everoad.blog.backend.domain.memberConnection.MemberConnection;
import com.everoad.blog.backend.domain.memberConnection.MemberConnectionRepository;
import com.everoad.blog.backend.dto.member.MemberInfoDto;
import com.everoad.blog.backend.dto.member.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberConnectionRepository memberConnectionRepository;
  private final MemberRepository memberRepository;

  private final PasswordEncoder encoder;

  @Transactional
  public void register(MemberSaveDto saveDto) {
    if (memberRepository.existsById(saveDto.getId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    Member member = saveDto.toEntity(Set.of(MemberRole.USER));
    member.updatePassword(encoder, saveDto.getPassword());
    memberRepository.save(member);
  }

  public MemberInfoDto selectMemberByToken(String token) {
    MemberConnection memberConnection = memberConnectionRepository.findByAccessToken(token)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return MemberInfoDto.create(memberConnection.getMember());
  }

}