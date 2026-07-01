package com.circle_devs.zdanovskih.facade.impl;

import com.circle_devs.zdanovskih.dto.PublisherCreateDto;
import com.circle_devs.zdanovskih.dto.PublisherResponseDto;
import com.circle_devs.zdanovskih.dto.PublisherUpdateDto;

import java.util.List;

/**
 * Interface for PublisherFacade
 */
public interface PublisherFacade {
    PublisherResponseDto create (PublisherCreateDto dto);

    List<PublisherResponseDto> getAllPublishers(
            int page,
            int size,
            String name,
            String site);

    PublisherUpdateDto update(Long id, PublisherUpdateDto dto);

    void delete(Long id);
}
