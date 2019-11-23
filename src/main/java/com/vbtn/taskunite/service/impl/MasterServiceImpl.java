package com.vbtn.taskunite.service.impl;

import com.vbtn.taskunite.service.MasterService;
import com.vbtn.taskunite.domain.Master;
import com.vbtn.taskunite.repository.MasterRepository;
import com.vbtn.taskunite.service.dto.MasterDTO;
import com.vbtn.taskunite.service.mapper.MasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Master}.
 */
@Service
@Transactional
public class MasterServiceImpl implements MasterService {

    private final Logger log = LoggerFactory.getLogger(MasterServiceImpl.class);

    private final MasterRepository masterRepository;

    private final MasterMapper masterMapper;

    public MasterServiceImpl(MasterRepository masterRepository, MasterMapper masterMapper) {
        this.masterRepository = masterRepository;
        this.masterMapper = masterMapper;
    }

    /**
     * Save a master.
     *
     * @param masterDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MasterDTO save(MasterDTO masterDTO) {
        log.debug("Request to save Master : {}", masterDTO);
        Master master = masterMapper.toEntity(masterDTO);
        master = masterRepository.save(master);
        return masterMapper.toDto(master);
    }

    /**
     * Get all the masters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Masters");
        return masterRepository.findAll(pageable)
            .map(masterMapper::toDto);
    }


    /**
     * Get one master by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MasterDTO> findOne(Long id) {
        log.debug("Request to get Master : {}", id);
        return masterRepository.findById(id)
            .map(masterMapper::toDto);
    }

    /**
     * Delete the master by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Master : {}", id);
        masterRepository.deleteById(id);
    }
}
