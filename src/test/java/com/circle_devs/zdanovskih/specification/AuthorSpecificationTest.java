package com.circle_devs.zdanovskih.specification;

import com.circle_devs.zdanovskih.entity.Author;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AuthorSpecificationTest {
    @Test
    void filter_shouldAddLikePredicateWhenNameProvided(){
        String name = "John Doe";
        Specification<Author> spec = AuthorSpecification.filter(name );

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Author> root = mock(Root.class);
        Path<String> namePath = mock(Path.class);
        when(root.<String>get("name")).thenReturn(namePath);
        when(cb.lower(namePath)).thenReturn(mock(Expression.class));
        when(cb.like(any(), eq("%john doe%"))).thenReturn(mock(Predicate.class));

        Predicate predicate = spec.toPredicate(root,query,cb);
        verify(cb).like(any(), eq("%john doe%"));
    }

    @Test
    void filter_shouldReturnEmptyPredicateWhenNameIsNull(){
        Specification<Author> spec = AuthorSpecification.filter(null );
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Predicate result = spec.toPredicate(mock(Root.class), mock(CriteriaQuery.class), cb);
        verify(cb).and(new Predicate[0]);
    }
}
