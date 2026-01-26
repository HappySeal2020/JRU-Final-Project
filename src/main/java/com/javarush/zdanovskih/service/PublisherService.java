package com.javarush.zdanovskih.service;

import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        Sort sort = Sort.sort(Publisher.class).by(Publisher::getId);
        return publisherRepository.findAll(sort);
    }

    @Transactional
    public Publisher save (Publisher publisher) {
        return publisherRepository.saveAndFlush(publisher);
    }

    @Transactional
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }

    public List<Publisher> findAll(){
        return publisherRepository.findAll();
    }
}
