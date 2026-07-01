package com.circle_devs.zdanovskih.mapper;

import com.circle_devs.zdanovskih.dto.PublisherResponseDto;
import com.circle_devs.zdanovskih.entity.Publisher;
import org.springframework.stereotype.Component;

/**
 * Mapper for Publisher
 */
@Component
public class PublisherMapper {

    //Publisher -> PublisherResponseDto
    public PublisherResponseDto publisherToPublisherResponseDto(Publisher publisher) {
        return new PublisherResponseDto(publisher.getId(),
                publisher.getName(),
                publisher.getSite());
    }



}
