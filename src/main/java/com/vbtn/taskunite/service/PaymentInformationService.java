package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.PaymentInformationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.PaymentInformation}.
 */
public interface PaymentInformationService {

    /**
     * Save a paymentInformation.
     *
     * @param paymentInformationDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentInformationDTO save(PaymentInformationDTO paymentInformationDTO);

    /**
     * Get all the paymentInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentInformationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentInformationDTO> findOne(Long id);

    /**
     * Delete the "id" paymentInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
