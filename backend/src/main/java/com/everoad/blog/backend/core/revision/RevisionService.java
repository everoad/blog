package com.everoad.blog.backend.core.revision;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RevisionService {

  private final CustomRevisionRepository revisionRepository;

  public Page<? extends BaseRevisionDto> selectRevisionList(RevisionClassType type, RevisionSearchDto searchDto, Pageable pageable) {
    return revisionRepository.findAll(type, searchDto, pageable);
  }

}
