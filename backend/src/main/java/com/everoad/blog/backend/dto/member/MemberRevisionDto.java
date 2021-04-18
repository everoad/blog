package com.everoad.blog.backend.dto.member;

import com.everoad.blog.backend.core.revision.BaseRevisionDto;
import com.everoad.blog.backend.core.revision.MyRevisionEntity;
import com.everoad.blog.backend.domain.member.Member;
import lombok.Getter;
import org.hibernate.envers.RevisionType;

import java.util.Collection;

@Getter
public class MemberRevisionDto extends BaseRevisionDto {

  private final String memberId;
  private final String name;
  private final String password;

  public MemberRevisionDto(Member member, MyRevisionEntity entity, RevisionType revisionType, Collection<?> modified) {
    super(entity, revisionType, modified);
    this.memberId = member.getId();
    this.name = member.getName();
    this.password = isChanged("password");
  }

}
