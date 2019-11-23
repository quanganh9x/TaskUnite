package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.TaskerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.Tasker}.
 */
public interface TaskerService {

    /**
     * Save a tasker.
     *
     * @param taskerDTO the entity to save.
     * @return the persisted entity.
     */
    TaskerDTO save(TaskerDTO taskerDTO);

    /**
     * Get all the taskers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskerDTO> findAll(Pageable pageable);

    /**
     * Get all the taskers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TaskerDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" tasker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskerDTO> findOne(Long id);

    /**
     * Delete the "id" tasker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
