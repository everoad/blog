package com.everoad.blog.backend.dto.member;

import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRole;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveDto {

  private String id;
  private String name;
  private String password;

  @Builder
  public MemberSaveDto(String id, String name, String password) {
    this.id = id;
    this.name = name;
    this.password = password;
  }

  public Member toEntity(Set<MemberRole> roles) {
    return Member.builder()
        .id(id)
        .name(name)
        .roles(roles)
        .password(password)
        .build();
  }

}
