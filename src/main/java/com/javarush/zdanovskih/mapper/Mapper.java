package com.javarush.zdanovskih.mapper;

import com.javarush.zdanovskih.dto.AuthorDto;
import com.javarush.zdanovskih.dto.BookCreationDto;
import com.javarush.zdanovskih.dto.BookResponseDto;
import com.javarush.zdanovskih.dto.PublisherDto;
import com.javarush.zdanovskih.entity.Book;
import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.entity.Author;

public class Mapper {
    public static AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
    public static Author toAuthor(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName());
    }

    public static PublisherDto toPublisherDto(Publisher publisher) {
        return new PublisherDto(publisher.getId(), publisher.getName(), publisher.getSite());
    }
    public static Publisher toPublisher(PublisherDto publisherDto) {
        return new Publisher(publisherDto.getId(), publisherDto.getName(), publisherDto.getSite());
    }

    public static BookResponseDto bookToBookDto(Book book) {
        return new BookResponseDto(book.getId(),
                book.getName(),
                book.getAuthors(),
                book.getPrintYear(),
                book.getPublisher(),
                book.getBbk(),
                book.getIsbn(),
                book.getPages());
    }

    public static BookCreationDto toBookCreationDto(BookCreationDto bookCreationDto) {
        return new BookCreationDto(bookCreationDto.getName(),
                bookCreationDto.getAuthor(),
                bookCreationDto.getPrintYear(),
                bookCreationDto.getPublisher(),
                bookCreationDto.getBbk(),
                bookCreationDto.getIsbn(),
                bookCreationDto.getPages());
    }
}
