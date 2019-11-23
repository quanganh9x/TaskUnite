package com.vbtn.taskunite.service.impl;

import com.vbtn.taskunite.service.TaskerService;
import com.vbtn.taskunite.domain.Tasker;
import com.vbtn.taskunite.repository.TaskerRepository;
import com.vbtn.taskunite.service.dto.TaskerDTO;
import com.vbtn.taskunite.service.mapper.TaskerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tasker}.
 */
@Service
@Transactional
public class TaskerServiceImpl implements TaskerService {

    private final Logger log = LoggerFactory.getLogger(TaskerServiceImpl.class);

    private final TaskerRepository taskerRepository;

    private final TaskerMapper taskerMapper;

    public TaskerServiceImpl(TaskerRepository taskerRepository, TaskerMapper taskerMapper) {
        this.taskerRepository = taskerRepository;
        this.taskerMapper = taskerMapper;
    }

    /**
     * Save a tasker.
     *
     * @param taskerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskerDTO save(TaskerDTO taskerDTO) {
        log.debug("Request to save Tasker : {}", taskerDTO);
        Tasker tasker = taskerMapper.toEntity(taskerDTO);
        tasker = taskerRepository.save(tasker);
        return taskerMapper.toDto(tasker);
    }

    /**
     * Get all the taskers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Taskers");
        return taskerRepository.findAll(pageable)
            .map(taskerMapper::toDto);
    }

    /**
     * Get all the taskers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TaskerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return taskerRepository.findAllWithEagerRelationships(pageable).map(taskerMapper::toDto);
    }
    

    /**
     * Get one tasker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskerDTO> findOne(Long id) {
        log.debug("Request to get Tasker : {}", id);
        return taskerRepository.findOneWithEagerRelationships(id)
            .map(taskerMapper::toDto);
    }

    /**
     * Delete the tasker by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tasker : {}", id);
        taskerRepository.deleteById(id);
    }
}
