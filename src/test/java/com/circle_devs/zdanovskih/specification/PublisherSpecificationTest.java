package com.circle_devs.zdanovskih.specification;
import com.circle_devs.zdanovskih.entity.Publisher;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PublisherSpecificationTest {
    @Test
    void filter_shouldAddLikePredicateWhenNameProvided(){
        String name = "My print";
        String site = "www.my-print.com";
        Specification<Publisher> spec = PublisherSpecification.filter(name, site );

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Publisher> root = mock(Root.class);
        Path<String> namePath = mock(Path.class);
        when(root.<String>get("name")).thenReturn(namePath);
        when(cb.lower(namePath)).thenReturn(mock(Expression.class));
        when(cb.like(any(), eq("%my print%"))).thenReturn(mock(Predicate.class));

        spec.toPredicate(root, query, cb);
        verify(cb).like(any(), eq("%my print%"));
    }

    @Test
    void filter_shouldReturnEmptyPredicateWhenNameIsNull(){
        Specification<Publisher> spec = PublisherSpecification.filter(null , null);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        spec.toPredicate(mock(Root.class), mock(CriteriaQuery.class), cb);
        verify(cb).and(new Predicate[0]);
    }

}
