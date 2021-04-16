package com.everoad.blog.backend.domain.member;

import com.everoad.blog.backend.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.data.domain.Persistable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Audited(withModifiedFlag = true)
@AuditOverride(forClass = BaseTimeEntity.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Persistable<String> {

  @Id @Column(name = "member_id", length = 20, nullable = false, updatable = false)
  private String id;

  @Column(name = "name", length = 20, nullable = false)
  private String name;

  @Column(name = "password", length = 128, nullable = false)
  private String password;

  @Column(name = "use_yn", nullable = false)
  private Boolean use;

  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  @JoinTable(name = "member_role",joinColumns = @JoinColumn(name = "member_id"))
  @Column(name = "role", nullable = false, length = 10)
  private Set<MemberRole> roles;

  @Builder
  public Member(String id, String name, String password, Set<MemberRole> roles) {
    Assert.hasText(id, "id is not empty");
    Assert.hasText(name, "name is not empty");
    Assert.hasText(password, "password is not empty");
    Assert.notNull(roles, "roles is not null");
    Assert.notEmpty(roles, "roles is not empty");
    this.id = id;
    this.name = name;
    this.password = password;
    this.roles = roles;
    this.use = true;
  }

  @Override
  public boolean isNew() {
    return getCreatedTime() == null;
  }

  //== 비즈니스 로직 ==//

  /**
   * 비밀번호 변경.
   *
   * @param encoder Password encoder.
   * @param password new password.
   */
  public void updatePassword(PasswordEncoder encoder, String password) {
    this.password = encoder.encode(password);
  }

}
