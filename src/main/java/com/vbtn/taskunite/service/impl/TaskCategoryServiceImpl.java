package com.vbtn.taskunite.service.impl;

import com.vbtn.taskunite.service.TaskCategoryService;
import com.vbtn.taskunite.domain.TaskCategory;
import com.vbtn.taskunite.repository.TaskCategoryRepository;
import com.vbtn.taskunite.service.dto.TaskCategoryDTO;
import com.vbtn.taskunite.service.mapper.TaskCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskCategory}.
 */
@Service
@Transactional
public class TaskCategoryServiceImpl implements TaskCategoryService {

    private final Logger log = LoggerFactory.getLogger(TaskCategoryServiceImpl.class);

    private final TaskCategoryRepository taskCategoryRepository;

    private final TaskCategoryMapper taskCategoryMapper;

    public TaskCategoryServiceImpl(TaskCategoryRepository taskCategoryRepository, TaskCategoryMapper taskCategoryMapper) {
        this.taskCategoryRepository = taskCategoryRepository;
        this.taskCategoryMapper = taskCategoryMapper;
    }

    /**
     * Save a taskCategory.
     *
     * @param taskCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskCategoryDTO save(TaskCategoryDTO taskCategoryDTO) {
        log.debug("Request to save TaskCategory : {}", taskCategoryDTO);
        TaskCategory taskCategory = taskCategoryMapper.toEntity(taskCategoryDTO);
        taskCategory = taskCategoryRepository.save(taskCategory);
        return taskCategoryMapper.toDto(taskCategory);
    }

    /**
     * Get all the taskCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskCategories");
        return taskCategoryRepository.findAll(pageable)
            .map(taskCategoryMapper::toDto);
    }


    /**
     * Get one taskCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCategoryDTO> findOne(Long id) {
        log.debug("Request to get TaskCategory : {}", id);
        return taskCategoryRepository.findById(id)
            .map(taskCategoryMapper::toDto);
    }

    /**
     * Delete the taskCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskCategory : {}", id);
        taskCategoryRepository.deleteById(id);
    }
}
