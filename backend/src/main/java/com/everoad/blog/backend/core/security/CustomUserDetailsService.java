package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByIdAndUseIsTrue(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
    return new MemberAdapter(member);
  }

}
