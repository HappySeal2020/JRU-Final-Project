package com.circle_devs.zdanovskih.controller;

import com.circle_devs.zdanovskih.dto.PublisherCreateDto;
import com.circle_devs.zdanovskih.dto.PublisherResponseDto;
import com.circle_devs.zdanovskih.dto.PublisherUpdateDto;
import com.circle_devs.zdanovskih.facade.PublisherFacadeImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;
import static com.circle_devs.zdanovskih.constant.Const.REST_PUBLISHER_PATH;

/**
 * Controller for Publishers
 * Provides CRUD operations with book's publishers.
 */
@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class PublisherController {
    private final PublisherFacadeImpl publisherFacadeImpl;

    public PublisherController(PublisherFacadeImpl publisherFacadeImpl) {
        this.publisherFacadeImpl = publisherFacadeImpl;
    }

    @Operation(summary = "Чтение всех издателей. Фильтр по издателю, его сайту; пагинация.")
    @GetMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<PublisherResponseDto> getAllPublishers(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String site,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        return publisherFacadeImpl.getAllPublishers(page, size, name, site);
    }

    //Create
    @Operation(summary = "Создание нового издателя.")
    @PostMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherResponseDto createPublisher(@Valid @RequestBody PublisherCreateDto publisherCreateDto) {
        log.info("REST API - Creating new publisher: {}", publisherCreateDto);
        return publisherFacadeImpl.create(publisherCreateDto);
    }

    //Update
    @Operation(summary = "Изменение издателя с номером {id}")
    @PutMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PublisherUpdateDto updatePublisher(@PathVariable Long  id, @Valid @RequestBody PublisherUpdateDto publisherUpdateDto) {
        log.info("REST API - Updating publisher={}, {}", id, publisherUpdateDto);
        return publisherFacadeImpl.update(id, publisherUpdateDto);
    }

    //Delete
    @Operation(summary = "Удаление издателя с номером {id}")
    @DeleteMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        log.info("REST API - Deleting publisher={}", id);
        publisherFacadeImpl.delete(id);
    }

}
