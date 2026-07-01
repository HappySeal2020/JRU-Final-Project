package com.circle_devs.zdanovskih.specification;

import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.entity.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Class provides Book pagination & filtering
 */
@Slf4j
public class BookSpecification {

    /**
     * Filter by
     *       when given one print year - by this print year
     *       when given two print year - between them
     *       when given one number of pages - by this number
     *       when given two number of pages - between them
     * @param name book's name
     * @param author book's author
     * @param printYearFrom book's print year
     * @param printYearTo book's print year
     * @param publisher book's publisher
     * @param bbk book's bbk
     * @param isbn book's isbn
     * @param pagesFrom number of pages in book
     * @param pagesTo number of pages in book
     * @return filtered Books
     */
    public static Specification<Book> filter(String name,
                                             String author,
                                             Integer printYearFrom,
                                             Integer printYearTo,
                                             String publisher,
                                             String bbk,
                                             String isbn,
                                             Integer pagesFrom,
                                             Integer pagesTo
                                             ) {
        log.info("Filtering Book by name={}, author={}, print year1={}, print year2={}, publisher={}, bbk={}, isbn={}, pages1={} pages2={}",
                name, author, printYearFrom, printYearTo,publisher, bbk, isbn, pagesFrom, pagesTo);
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            assert query != null;
            query.distinct(true);
            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }

            if (author != null) {
                Join<Book, Author> authorJoin = root.join("authors", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(authorJoin.get("name")),
                        "%" + author.toLowerCase() + "%"));
            }

            if (publisher != null) {
                predicates.add(cb.like(cb.lower(root.get("publisher").get("name")),
                        "%" + publisher.toLowerCase() + "%"));
            }

            if (bbk != null) {
                predicates.add(cb.like(cb.lower(root.get("bbk")),
                        "%" + bbk.toLowerCase() + "%"));
            }
            if (isbn != null) {
                predicates.add(cb.like(cb.lower(root.get("isbn")),
                        "%" + isbn.toLowerCase() + "%"));
            }

            if (printYearFrom != null && printYearTo != null) {
                predicates.add(cb.between(root.get("printYear"), printYearFrom, printYearTo));
            } else if (printYearFrom != null) {
                predicates.add(cb.equal(root.get("printYear"), printYearFrom));
            }

            if (pagesFrom != null && pagesTo != null) {
                predicates.add(cb.between(root.get("pages"), pagesFrom, pagesTo));
            } else if (pagesFrom != null) {
                predicates.add(cb.equal(root.get("pages"), pagesFrom));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
