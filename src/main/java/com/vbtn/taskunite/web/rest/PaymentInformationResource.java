package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.PaymentInformationService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.PaymentInformationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vbtn.taskunite.domain.PaymentInformation}.
 */
@RestController
@RequestMapping("/api")
public class PaymentInformationResource {

    private final Logger log = LoggerFactory.getLogger(PaymentInformationResource.class);

    private static final String ENTITY_NAME = "paymentInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentInformationService paymentInformationService;

    public PaymentInformationResource(PaymentInformationService paymentInformationService) {
        this.paymentInformationService = paymentInformationService;
    }

    /**
     * {@code POST  /payment-informations} : Create a new paymentInformation.
     *
     * @param paymentInformationDTO the paymentInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentInformationDTO, or with status {@code 400 (Bad Request)} if the paymentInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-informations")
    public ResponseEntity<PaymentInformationDTO> createPaymentInformation(@RequestBody PaymentInformationDTO paymentInformationDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentInformation : {}", paymentInformationDTO);
        if (paymentInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentInformationDTO result = paymentInformationService.save(paymentInformationDTO);
        return ResponseEntity.created(new URI("/api/payment-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-informations} : Updates an existing paymentInformation.
     *
     * @param paymentInformationDTO the paymentInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentInformationDTO,
     * or with status {@code 400 (Bad Request)} if the paymentInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-informations")
    public ResponseEntity<PaymentInformationDTO> updatePaymentInformation(@RequestBody PaymentInformationDTO paymentInformationDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentInformation : {}", paymentInformationDTO);
        if (paymentInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentInformationDTO result = paymentInformationService.save(paymentInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-informations} : get all the paymentInformations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentInformations in body.
     */
    @GetMapping("/payment-informations")
    public ResponseEntity<List<PaymentInformationDTO>> getAllPaymentInformations(Pageable pageable) {
        log.debug("REST request to get a page of PaymentInformations");
        Page<PaymentInformationDTO> page = paymentInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-informations/:id} : get the "id" paymentInformation.
     *
     * @param id the id of the paymentInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-informations/{id}")
    public ResponseEntity<PaymentInformationDTO> getPaymentInformation(@PathVariable Long id) {
        log.debug("REST request to get PaymentInformation : {}", id);
        Optional<PaymentInformationDTO> paymentInformationDTO = paymentInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentInformationDTO);
    }

    /**
     * {@code DELETE  /payment-informations/:id} : delete the "id" paymentInformation.
     *
     * @param id the id of the paymentInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-informations/{id}")
    public ResponseEntity<Void> deletePaymentInformation(@PathVariable Long id) {
        log.debug("REST request to delete PaymentInformation : {}", id);
        paymentInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
