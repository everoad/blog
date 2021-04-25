package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.Const;
import com.everoad.blog.backend.core.revision.BaseRevisionDto;
import com.everoad.blog.backend.core.revision.RevisionClassType;
import com.everoad.blog.backend.core.revision.RevisionSearchDto;
import com.everoad.blog.backend.core.revision.RevisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/revisions", produces = Const.APPLICATION_JSON_VALUE)
public class RevisionController {

  private final RevisionService revisionService;

  @GetMapping("/{clazzType}/columns")
  public ApiResponse<?> getColumnList(@PathVariable RevisionClassType clazzType) throws Exception {
    List<String> columns = RevisionClassType.getColumns(clazzType);
    String idClassName = RevisionClassType.getIdClassName(clazzType);
    return new ApiResponse<>(Map.of("columns", columns, "idClassName", idClassName));
  }

  @GetMapping("/{clazzType}")
  public ApiResponse<?> getRevisionList(@PathVariable RevisionClassType clazzType, RevisionSearchDto searchDto, Pageable pageable) {
    Slice<? extends BaseRevisionDto> slice = revisionService.selectRevisionList(clazzType, searchDto, pageable);
    return new ApiResponse<>(slice);
  }

}

