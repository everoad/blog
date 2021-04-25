package com.everoad.blog.backend.core.revision;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class CustomRevisionRepository extends RevisionRepositorySupport {

  public CustomRevisionRepository(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory);
  }

  public Slice<? extends BaseRevisionDto> findAll(RevisionClassType type, RevisionSearchDto searchDto, Pageable pageable) {
    return applyPaginationForSlice(type, pageable, searchDto);
  }

}
