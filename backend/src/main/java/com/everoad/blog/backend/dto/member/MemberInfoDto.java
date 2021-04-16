package com.everoad.blog.backend.dto.member;

import com.everoad.blog.backend.domain.member.Member;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {

  private String id;
  private String name;

  @Builder
  public MemberInfoDto(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public static MemberInfoDto create(Member member) {
    return MemberInfoDto.builder()
        .id(member.getId())
        .name(member.getName())
        .build();
  }

}

