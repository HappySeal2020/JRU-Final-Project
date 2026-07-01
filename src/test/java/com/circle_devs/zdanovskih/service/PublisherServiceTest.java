package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.repository.PublisherRepository;
import com.circle_devs.zdanovskih.specification.PublisherSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class PublisherServiceTest {
    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    //Read publishers
    @Test
    public void getPublishers_shouldCallRepositoryWithCorrectParameters() {
        int page=0, size=5;
        String name="FastPrint";
        String site="www.fastprint.com";
        Pageable expectedPageable= PageRequest.of(page, size, Sort.by("id"));
        PublisherSpecification.filter(name,site);
        Page<Publisher> mockPage = new PageImpl<>(List.of(new Publisher()));
        when(publisherRepository.findAll(any(Specification.class), eq(expectedPageable)))
                .thenReturn(mockPage);

        List<Publisher> result = publisherService.getAllPublishers(page, size, name, site);

        verify(publisherRepository).findAll(any(Specification.class), eq(expectedPageable));
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnPublishersById(){
        Long id = 1L;
        Publisher publisher = new Publisher();
        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));
        Publisher result = publisherService.findById(id);
        verify(publisherRepository).findById(id);
        assertEquals(publisher, result);
    }

    @Test
    void shouldReturnErrorWhenPublisherNotFound() {
        Long id = 100L;
        when(publisherRepository.findById(id)).thenReturn(Optional.of(new Publisher()));
        publisherService.findById(id);
        Exception exception = assertThrows(RuntimeException.class,
                () -> publisherService.findById(1L));
        assertThat(exception.getMessage(),containsString("not found"));
    }

    @Test
    public void savePublisher(){
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("PrintCompany");
        publisher.setSite("www.printcompany.com");
        when(publisherRepository.saveAndFlush(publisher)).thenReturn(publisher);
        Publisher result = publisherService.savePublisher(publisher);
        verify(publisherRepository).saveAndFlush(publisher);
        assertEquals(publisher.getId(), result.getId());
        assertEquals(publisher.getName(), result.getName());
        assertEquals(publisher.getSite(), result.getSite());
    }

    @Test
    public void deletePublisher(){
        Long id = 1L;
        doNothing().when(publisherRepository).deleteById(id);
        publisherService.deleteById(id);
        verify(publisherRepository).deleteById(id);
    }




}
