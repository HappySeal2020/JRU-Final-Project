package com.circle_devs.zdanovskih.facade;

import com.circle_devs.zdanovskih.dto.PublisherCreateDto;
import com.circle_devs.zdanovskih.dto.PublisherResponseDto;
import com.circle_devs.zdanovskih.dto.PublisherUpdateDto;
import com.circle_devs.zdanovskih.entity.Publisher;
import com.circle_devs.zdanovskih.facade.impl.PublisherFacade;
import com.circle_devs.zdanovskih.service.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * facade for Publisher uses services and returns DTO
 */
@Component
@Slf4j
public class PublisherFacadeImpl implements PublisherFacade {
    private final PublisherServiceImpl publisherService;
    public PublisherFacadeImpl(PublisherServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * create new publisher
     * @param dto - PublisherCreateDto
     * @return PublisherResponseDto (with id)
     */
    @Override
    public PublisherResponseDto create (PublisherCreateDto dto){
        Publisher publisher = new Publisher();
        publisher.setName(dto.name());
        publisher.setSite(dto.site());
        Publisher saved = publisherService.savePublisher(publisher);
        return new PublisherResponseDto(saved.getId(), saved.getName(), saved.getSite());
    }

    /**
     * get publisher with filter and pagination
     * @param page - page number (default 0)
     * @param size - page size (default 5)
     * @param name - filter publisher by name
     * @param site - filter publisher by site
     * @return List of PublisherResponseDto
     */
    @Override
    public List<PublisherResponseDto> getAllPublishers(
            int page,
            int size,
            String name,
            String site){
        return publisherService.getAllPublishers(page, size, name, site)
                .stream()
                .map(publisher -> new PublisherResponseDto(publisher.getId(), publisher.getName(), publisher.getSite()))
                .toList();
    }

    /**
     * Update publisher
     * @param id - publisher's id
     * @param dto - dto for update
     * @return dto after update
     */
    @Override
    public PublisherUpdateDto update(Long id, PublisherUpdateDto dto){
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(dto.name());
        publisher.setSite(dto.site());
        Publisher saved = publisherService.savePublisher(publisher);
        return new PublisherUpdateDto(saved.getName(), saved.getSite());
    }

    /**
     * Delete publisher
     * @param id - publisher's id
     */
    @Override
    public void delete(Long id){
        publisherService.deleteById(id);
    }
}
