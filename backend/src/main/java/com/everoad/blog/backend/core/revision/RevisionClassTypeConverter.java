package com.everoad.blog.backend.core.revision;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class RevisionClassTypeConverter implements Converter<String, RevisionClassType> {

  @Override
  public RevisionClassType convert(String source) {
    return RevisionClassType.findByName(source);
  }

}
