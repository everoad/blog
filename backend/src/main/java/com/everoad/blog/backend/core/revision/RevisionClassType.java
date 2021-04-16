package com.everoad.blog.backend.core.revision;


import com.everoad.blog.backend.domain.member.Member;
import com.everoad.blog.backend.domain.member.MemberRole;
import com.everoad.blog.backend.dto.member.MemberRevisionDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public enum RevisionClassType {

  MEMBER("member", Member.class, MemberRevisionDto.class);

  private final String name;
  private final Class<?> domainClass;
  private final Class<? extends BaseRevisionDto> dtoClass;

  RevisionClassType(String name, Class<?> domainClass, Class<? extends BaseRevisionDto> dtoClass) {
    this.name = name;
    this.domainClass = domainClass;
    this.dtoClass = dtoClass;
  }

  public static RevisionClassType findByName(String name) {
    return Arrays.stream(values()).filter(value -> value.getName().equalsIgnoreCase(name)).findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }

  public static List<String> getColumns(RevisionClassType type) {
    Field[] superFields = type.getDtoClass().getSuperclass().getDeclaredFields();
    Field[] fields = type.getDtoClass().getDeclaredFields();
    List<String> columns = new ArrayList<>(superFields.length + fields.length);
    for (Field field : superFields) {
      columns.add(field.getName());
    }
    for (Field field : fields) {
      columns.add(field.getName());
    }
    return columns;
  }

  public static String getIdClassName(RevisionClassType type) throws NoSuchFieldException {
    for (Field field : type.getDomainClass().getDeclaredFields()) {
      Id annotation = field.getAnnotation(Id.class);
      if (annotation != null) {
        return field.getType().getSimpleName();
      }
    }
    throw new RuntimeException("");
  }

}
