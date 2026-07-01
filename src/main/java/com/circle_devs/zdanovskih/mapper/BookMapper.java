package com.circle_devs.zdanovskih.mapper;

import com.circle_devs.zdanovskih.dto.BookResponseDto;
import com.circle_devs.zdanovskih.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper for Book
 */
@Component
@RequiredArgsConstructor
public class BookMapper {
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;

    //Book -> BookResponseDto
    public BookResponseDto bookToBookResponseDto(Book book) {
        return new BookResponseDto(book.getId(),
                book.getName(),
                book.getAuthors()
                                .stream()
                                 .map(authorMapper::authorToAuthorResponseDto)
                                 .toList(),
                book.getPrintYear(),
                publisherMapper.publisherToPublisherResponseDto(book.getPublisher()),
                book.getBbk(),
                book.getIsbn(),
                book.getPages());
    }




}
