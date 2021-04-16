package com.everoad.blog.backend.core.security;


import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Security User 를 상속 받음.
 * CurrentUser Annotation 으로 member field 값을 가져옴.
 *
 * @author bjkim
 */
public class MemberAdapter extends User implements Serializable {

  private static final long serialVersionUID = 1L;
  private final static String ROLE_PREFIX = "ROLE_";
  private final Member member;

  public MemberAdapter(Member member) {
    super(member.getId(), member.getPassword(), authorities(member.getRoles()));
    this.member = member;
  }

  public Member getMember() {
    return member;
  }

  private static Collection<? extends GrantedAuthority> authorities(Set<MemberRole> roles) {
    return roles.stream().map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r.name())).collect(Collectors.toSet());
  }

}
