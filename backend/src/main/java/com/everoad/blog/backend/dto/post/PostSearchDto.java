package com.everoad.blog.backend.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSearchDto {

  private String keyword;

  private Integer categoryId;

}