package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.UserInformationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.UserInformation}.
 */
public interface UserInformationService {

    /**
     * Save a userInformation.
     *
     * @param userInformationDTO the entity to save.
     * @return the persisted entity.
     */
    UserInformationDTO save(UserInformationDTO userInformationDTO);

    /**
     * Get all the userInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserInformationDTO> findAll(Pageable pageable);
    /**
     * Get all the UserInformationDTO where Tasker is {@code null}.
     *
     * @return the list of entities.
     */
    List<UserInformationDTO> findAllWhereTaskerIsNull();
    /**
     * Get all the UserInformationDTO where Master is {@code null}.
     *
     * @return the list of entities.
     */
    List<UserInformationDTO> findAllWhereMasterIsNull();
    /**
     * Get all the UserInformationDTO where Address is {@code null}.
     *
     * @return the list of entities.
     */
    List<UserInformationDTO> findAllWhereAddressIsNull();
    /**
     * Get all the UserInformationDTO where Statistic is {@code null}.
     *
     * @return the list of entities.
     */
    List<UserInformationDTO> findAllWhereStatisticIsNull();


    /**
     * Get the "id" userInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserInformationDTO> findOne(Long id);

    /**
     * Delete the "id" userInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
