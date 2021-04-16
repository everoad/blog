package com.everoad.blog.backend.core.security;

import com.everoad.blog.backend.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = String.valueOf(authentication.getPrincipal());
    String password = String.valueOf(authentication.getCredentials());
    MemberAdapter memberAdapter = (MemberAdapter) userDetailsService.loadUserByUsername(username);
    Member member = memberAdapter.getMember();
    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new BadCredentialsException(username);
    }
    return new UsernamePasswordAuthenticationToken(memberAdapter, password, memberAdapter.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}