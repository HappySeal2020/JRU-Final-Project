package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.handler.NotFoundException;
import com.circle_devs.zdanovskih.repository.PublisherRepository;
import com.circle_devs.zdanovskih.service.impl.PublisherService;
import com.circle_devs.zdanovskih.specification.PublisherSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing Publishers
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers(int page, int size, String name, String site) {
        Sort.sort(Publisher.class).by(Publisher::getId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Publisher> publisherPage;
        publisherPage = publisherRepository.findAll(
                PublisherSpecification.filter(
                        name,
                        site),
                        pageable);
        return publisherPage.getContent();
    }

    @Override
    @Transactional
    public Publisher savePublisher (Publisher publisher) {
        return publisherRepository.saveAndFlush(publisher);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public Publisher findById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Publisher " + id + " not found"));
    }


}
