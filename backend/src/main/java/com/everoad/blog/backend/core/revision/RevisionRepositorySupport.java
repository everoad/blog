package com.everoad.blog.backend.core.revision;


import com.everoad.blog.backend.core.exception.BusinessException;
import lombok.Getter;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.MatchMode;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Revision 리스트 조회 기능.
 *
 * @author bjkim
 */
public abstract class RevisionRepositorySupport {

  private final EntityManagerFactory entityManagerFactory;

  public RevisionRepositorySupport(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  protected AuditReader getAuditReader() {
    return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
  }

  protected AuditQuery getAuditQuery(Class<?> domainClass) {
    return getAuditReader().createQuery().forRevisionsOfEntityWithChanges(domainClass, true);
  }

  protected Page<? extends BaseRevisionDto> applyPagination(RevisionClassType clazzType, Pageable pageable, RevisionSearchDto searchDto) {
    List<? extends BaseRevisionDto> content = applyPaginationForList(clazzType, pageable, searchDto);
    long total = content.size() > 0 ? selectTotalCount(clazzType.getDomainClass(), searchDto) : 0L;
    return new PageImpl<>(content, pageable, total);
  }

  protected List<? extends BaseRevisionDto> applyPaginationForList(RevisionClassType clazzType, Pageable pageable, RevisionSearchDto searchDto) {
    AuditQuery auditQuery = getAuditQuery(clazzType.getDomainClass())                   // Domain table 지정, from 문
        .addOrder(AuditEntity.revisionNumber().desc())                                  // order by 문
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults((int) (pageable.getPageSize() + pageable.getOffset()));          // limit 문
    addConditions(clazzType.getDomainClass(), auditQuery, searchDto);                   // where 문
    List<Object[]> resultList = auditQuery.getResultList();
    return convert(resultList, clazzType);
  }

  protected Slice<? extends BaseRevisionDto> applyPaginationForSlice(RevisionClassType clazzType, Pageable pageable, RevisionSearchDto searchDto) {
    AuditQuery auditQuery = getAuditQuery(clazzType.getDomainClass())                   // Domain table 지정, from 문
        .addOrder(AuditEntity.revisionNumber().desc())                                  // order by 문
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults((int) (pageable.getPageSize() + pageable.getOffset()) + 1);          // limit 문
    addConditions(clazzType.getDomainClass(), auditQuery, searchDto);                   // where 문
    List<Object[]> resultList = auditQuery.getResultList();
    List<? extends BaseRevisionDto> content = convert(resultList, clazzType);
    return toSlice(content, pageable);
  }

  /**
   * 페이징을 위한 content 총 개수.
   */
  protected Long selectTotalCount(Class<?> domainClass, RevisionSearchDto searchDto) {
    AuditQuery auditQuery = getAuditQuery(domainClass);
    auditQuery.addProjection(AuditEntity.revisionNumber().count());
    addConditions(domainClass, auditQuery, searchDto);
    return (Long) auditQuery.getSingleResult();
  }

  /**
   * 조건문 추가 함수. ( Where 문 )
   */
  protected void addConditions(Class<?> domainClass, AuditQuery auditQuery, RevisionSearchDto searchDto) {
    // rev = transaction group.
    if (searchDto.getRev() != null) {
      auditQuery.add(AuditEntity.revisionNumber().eq(searchDto.getRev()));
    }
    // Domain 키
    if (searchDto.getId() != null) {
      auditQuery.add(AuditEntity.id().eq(convertValueType(searchDto.getId(), domainClass)));
    }
    // ADD(추가), MOD(수정), DEL(삭제)
    if (StringUtils.hasText(searchDto.getType())) {
      auditQuery.add(AuditEntity.revisionType().eq(RevisionType.valueOf(searchDto.getType())));
    }
    if (StringUtils.hasText(searchDto.getRevCreatedBy())) {
      auditQuery.add(AuditEntity.revisionProperty("revCreatedBy.id").like(searchDto.getRevCreatedBy(), MatchMode.ANYWHERE));
    }
    // 날짜
    if (searchDto.getStartDate() != null && searchDto.getEndDate() != null) {
      long startTime = Timestamp.valueOf(searchDto.getStartTime()).getTime();
      long endTime = Timestamp.valueOf(searchDto.getEndTime()).getTime();
      auditQuery.add(AuditEntity.revisionProperty("timestamp").between(startTime, endTime));
    }
  }


  protected List<? extends BaseRevisionDto> convert(List<Object[]> resultList, RevisionClassType clazzType) {
    return resultList.stream().map(result -> {
      try {
        MyRevisionEntity entity = (MyRevisionEntity) result[1];
        RevisionType revisionType = (RevisionType) result[2];
        Collection<?> modified = (Collection<?>) result[3];
        return clazzType.getDtoClass()
            .getDeclaredConstructor(clazzType.getDomainClass(), MyRevisionEntity.class, RevisionType.class, Collection.class)
            .newInstance(result[0], entity, revisionType, modified);
      } catch (Exception e) {
        throw new BusinessException("Fail to create baseRevisionDto.", e);
      }
    }).collect(Collectors.toList());
  }

  private Object convertValueType(Object value, Class<?> domainClass) {
    Class<?> idFieldClass = getIdFieldClass(domainClass);
    try {
      if (idFieldClass == Integer.class) {
        return Integer.parseInt(String.valueOf(value));
      }
      if (idFieldClass == Long.class) {
        return Long.parseLong(String.valueOf(value));
      }
      return idFieldClass.cast(value);
    } catch (Exception e) {
      throw new BusinessException("Fail to cast revision ID class.", e);
    }
  }

  protected Class<?> getIdFieldClass(Class<?> domainClass) {
    for (Field field : domainClass.getDeclaredFields()) {
      Id annotation = field.getAnnotation(Id.class);
      if (annotation != null) {
        return field.getType();
      }
    }
    throw new RuntimeException("");
  }

  protected <T> Slice<T> toSlice(List<T> content, Pageable pageable) {
    boolean hasNext = false;

    if (content.size() > pageable.getPageSize()) {
      content.remove(pageable.getPageSize());
      hasNext = true;
    }
    return new SliceImpl<>(content, pageable, hasNext);
  }

  @Getter
  static class RevisionParamDto {

    private final Object entity;
    private final MyRevisionEntity revisionEntity;
    private final RevisionType revisionType;
    private final Collection<?> modified;

    public RevisionParamDto(Object entity, MyRevisionEntity revisionEntity, RevisionType revisionType, Collection<?> modified) {
      this.entity = entity;
      this.revisionEntity = revisionEntity;
      this.revisionType = revisionType;
      this.modified = modified;
    }
  }

}
