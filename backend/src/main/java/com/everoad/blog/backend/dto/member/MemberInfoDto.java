package com.everoad.blog.backend.dto.member;

import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRole;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {

  private String id;
  private String name;
  private Set<MemberRole> roles;

  @Builder
  public MemberInfoDto(String id, String name, Set<MemberRole> roles) {
    this.id = id;
    this.name = name;
    this.roles = roles;
  }

  public static MemberInfoDto create(Member member) {
    return MemberInfoDto.builder()
        .id(member.getId())
        .name(member.getName())
        .roles(member.getRoles())
        .build();
  }

}

