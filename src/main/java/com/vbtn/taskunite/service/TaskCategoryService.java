package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.TaskCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.TaskCategory}.
 */
public interface TaskCategoryService {

    /**
     * Save a taskCategory.
     *
     * @param taskCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    TaskCategoryDTO save(TaskCategoryDTO taskCategoryDTO);

    /**
     * Get all the taskCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" taskCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
