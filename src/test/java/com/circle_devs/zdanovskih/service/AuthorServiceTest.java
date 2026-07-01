package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.repository.AuthorRepository;
import com.circle_devs.zdanovskih.specification.AuthorSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class AuthorServiceTest {
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    //Read authors
    @Test
    public void getAuthors_shouldCallRepositoryWithCorrectParameters() {
        int page=0, size=5;
        String name="Pushkin";
        Pageable expectedPageable= PageRequest.of(page, size, Sort.by("id"));
        AuthorSpecification.filter(name);
        Page<Author> mockPage = new PageImpl<>(List.of(new Author()));
        when(authorRepository.findAll(any(Specification.class), eq(expectedPageable)))
                .thenReturn(mockPage);

        List<Author> result = authorService.getAuthors(page, size, name);

        verify(authorRepository).findAll(any(Specification.class), eq(expectedPageable));
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnAuthorsByIds(){
        List<Long> ids = List.of(1L, 2L);
        List<Author> authors = List.of(new Author());
        when(authorRepository.findAllById(ids)).thenReturn(authors);
        List<Author> result = authorService.findAllByIds(ids);
        verify(authorRepository).findAllById(anyList());
        assertEquals(1, result.size());
        assertEquals(authors, result);
    }

    @Test
    void shouldReturnEmptyListWhenAuthorsNotFound() {
        List<Long> ids = List.of(100L);
        when(authorRepository.findAllById(ids)).thenReturn(Collections.emptyList());
        List<Author> result = authorService.findAllByIds(ids);
        verify(authorRepository).findAllById(ids);
        assertTrue(result.isEmpty());
    }


    @Test
    public void saveAuthor(){
        Author author = new Author();
        author.setId(1L);
        author.setName("Volkov");
        when(authorRepository.saveAndFlush(author)).thenReturn(author);
        Author result = authorService.saveAuthor(author);
        verify(authorRepository).saveAndFlush(author);
        assertEquals(author.getId(), result.getId());
        assertEquals(author.getName(), result.getName());
    }

    @Test
    public void deleteAuthor(){
        Long id = 1L;
        doNothing().when(authorRepository).deleteById(id);
        authorService.deleteById(id);
        verify(authorRepository).deleteById(id);
    }
}