package com.vbtn.taskunite.service.impl;

import com.vbtn.taskunite.service.PaymentInformationService;
import com.vbtn.taskunite.domain.PaymentInformation;
import com.vbtn.taskunite.repository.PaymentInformationRepository;
import com.vbtn.taskunite.service.dto.PaymentInformationDTO;
import com.vbtn.taskunite.service.mapper.PaymentInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaymentInformation}.
 */
@Service
@Transactional
public class PaymentInformationServiceImpl implements PaymentInformationService {

    private final Logger log = LoggerFactory.getLogger(PaymentInformationServiceImpl.class);

    private final PaymentInformationRepository paymentInformationRepository;

    private final PaymentInformationMapper paymentInformationMapper;

    public PaymentInformationServiceImpl(PaymentInformationRepository paymentInformationRepository, PaymentInformationMapper paymentInformationMapper) {
        this.paymentInformationRepository = paymentInformationRepository;
        this.paymentInformationMapper = paymentInformationMapper;
    }

    /**
     * Save a paymentInformation.
     *
     * @param paymentInformationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentInformationDTO save(PaymentInformationDTO paymentInformationDTO) {
        log.debug("Request to save PaymentInformation : {}", paymentInformationDTO);
        PaymentInformation paymentInformation = paymentInformationMapper.toEntity(paymentInformationDTO);
        paymentInformation = paymentInformationRepository.save(paymentInformation);
        return paymentInformationMapper.toDto(paymentInformation);
    }

    /**
     * Get all the paymentInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentInformations");
        return paymentInformationRepository.findAll(pageable)
            .map(paymentInformationMapper::toDto);
    }


    /**
     * Get one paymentInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentInformationDTO> findOne(Long id) {
        log.debug("Request to get PaymentInformation : {}", id);
        return paymentInformationRepository.findById(id)
            .map(paymentInformationMapper::toDto);
    }

    /**
     * Delete the paymentInformation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentInformation : {}", id);
        paymentInformationRepository.deleteById(id);
    }
}
