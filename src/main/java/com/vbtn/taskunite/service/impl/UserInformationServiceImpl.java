package com.vbtn.taskunite.service.impl;

import com.vbtn.taskunite.service.UserInformationService;
import com.vbtn.taskunite.domain.UserInformation;
import com.vbtn.taskunite.repository.UserInformationRepository;
import com.vbtn.taskunite.service.dto.UserInformationDTO;
import com.vbtn.taskunite.service.mapper.UserInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link UserInformation}.
 */
@Service
@Transactional
public class UserInformationServiceImpl implements UserInformationService {

    private final Logger log = LoggerFactory.getLogger(UserInformationServiceImpl.class);

    private final UserInformationRepository userInformationRepository;

    private final UserInformationMapper userInformationMapper;

    public UserInformationServiceImpl(UserInformationRepository userInformationRepository, UserInformationMapper userInformationMapper) {
        this.userInformationRepository = userInformationRepository;
        this.userInformationMapper = userInformationMapper;
    }

    /**
     * Save a userInformation.
     *
     * @param userInformationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserInformationDTO save(UserInformationDTO userInformationDTO) {
        log.debug("Request to save UserInformation : {}", userInformationDTO);
        UserInformation userInformation = userInformationMapper.toEntity(userInformationDTO);
        userInformation = userInformationRepository.save(userInformation);
        return userInformationMapper.toDto(userInformation);
    }

    /**
     * Get all the userInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInformations");
        return userInformationRepository.findAll(pageable)
            .map(userInformationMapper::toDto);
    }



    /**
    *  Get all the userInformations where Tasker is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<UserInformationDTO> findAllWhereTaskerIsNull() {
        log.debug("Request to get all userInformations where Tasker is null");
        return StreamSupport
            .stream(userInformationRepository.findAll().spliterator(), false)
            .filter(userInformation -> userInformation.getTasker() == null)
            .map(userInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the userInformations where Master is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<UserInformationDTO> findAllWhereMasterIsNull() {
        log.debug("Request to get all userInformations where Master is null");
        return StreamSupport
            .stream(userInformationRepository.findAll().spliterator(), false)
            .filter(userInformation -> userInformation.getMaster() == null)
            .map(userInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the userInformations where Address is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<UserInformationDTO> findAllWhereAddressIsNull() {
        log.debug("Request to get all userInformations where Address is null");
        return StreamSupport
            .stream(userInformationRepository.findAll().spliterator(), false)
            .filter(userInformation -> userInformation.getAddress() == null)
            .map(userInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the userInformations where Statistic is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<UserInformationDTO> findAllWhereStatisticIsNull() {
        log.debug("Request to get all userInformations where Statistic is null");
        return StreamSupport
            .stream(userInformationRepository.findAll().spliterator(), false)
            .filter(userInformation -> userInformation.getStatistic() == null)
            .map(userInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserInformationDTO> findOne(Long id) {
        log.debug("Request to get UserInformation : {}", id);
        return userInformationRepository.findById(id)
            .map(userInformationMapper::toDto);
    }

    /**
     * Delete the userInformation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInformation : {}", id);
        userInformationRepository.deleteById(id);
    }
}
