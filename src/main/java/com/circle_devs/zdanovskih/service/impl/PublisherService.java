package com.circle_devs.zdanovskih.service.impl;

import com.circle_devs.zdanovskih.entity.Publisher;

import java.util.List;

/**
 * Interface for publishers service
 */
public interface PublisherService {
    /**
     * Returns all publishers with filter and pagination
     * @param page page number (default 0)
     * @param size page size (default 5)
     * @param name filter publisher by name
     * @param site filter publisher by site
     * @return List of Publisher entity
     */
    List<Publisher> getAllPublishers(int page, int size, String name, String site);

    /**
     * save the publisher
     * @param publisher - Publisher
     * @return Publisher
     */
    Publisher savePublisher (Publisher publisher);

    /**
     * delete the publisher
     * @param id - Publisher's id
     */
    void deleteById(Long id);

    /**
     * get single publisher by id
     * @param id - Publisher's id
     * @return Publisher
     */
    Publisher findById(Long id);
}
