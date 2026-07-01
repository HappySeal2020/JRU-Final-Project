package com.circle_devs.zdanovskih.specification;
import com.circle_devs.zdanovskih.entity.Publisher;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Class provides filtering by publisher
 */
@Slf4j
public class PublisherSpecification {
    public static Specification<Publisher> filter(String name, String site) {
        log.info("Filtering Publisher by name={}, site={}", name, site);
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }

            if (site != null) {
                predicates.add(cb.like(cb.lower(root.get("site")),
                        "%" + site.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
