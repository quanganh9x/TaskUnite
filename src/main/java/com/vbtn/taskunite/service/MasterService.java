package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.MasterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.Master}.
 */
public interface MasterService {

    /**
     * Save a master.
     *
     * @param masterDTO the entity to save.
     * @return the persisted entity.
     */
    MasterDTO save(MasterDTO masterDTO);

    /**
     * Get all the masters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MasterDTO> findAll(Pageable pageable);


    /**
     * Get the "id" master.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MasterDTO> findOne(Long id);

    /**
     * Delete the "id" master.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
