package com.everoad.blog.backend.core.revision;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RevisionSearchDto {

  private Long rev;

  private Object id;

  private String type;

  private String revCreatedBy;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private String item;

  private String keyword;

  public LocalDateTime getStartTime() {
    return startDate.atStartOfDay();
  }

  public LocalDateTime getEndTime() {
    return endDate.plusDays(1).atStartOfDay().minusNanos(1);
  }

}
