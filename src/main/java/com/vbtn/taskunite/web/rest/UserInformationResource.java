package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.UserInformationService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.UserInformationDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.vbtn.taskunite.domain.UserInformation}.
 */
@RestController
@RequestMapping("/api")
public class UserInformationResource {

    private final Logger log = LoggerFactory.getLogger(UserInformationResource.class);

    private static final String ENTITY_NAME = "userInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInformationService userInformationService;

    public UserInformationResource(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    /**
     * {@code POST  /user-informations} : Create a new userInformation.
     *
     * @param userInformationDTO the userInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInformationDTO, or with status {@code 400 (Bad Request)} if the userInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-informations")
    public ResponseEntity<UserInformationDTO> createUserInformation(@RequestBody UserInformationDTO userInformationDTO) throws URISyntaxException {
        log.debug("REST request to save UserInformation : {}", userInformationDTO);
        if (userInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInformationDTO result = userInformationService.save(userInformationDTO);
        return ResponseEntity.created(new URI("/api/user-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-informations} : Updates an existing userInformation.
     *
     * @param userInformationDTO the userInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInformationDTO,
     * or with status {@code 400 (Bad Request)} if the userInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-informations")
    public ResponseEntity<UserInformationDTO> updateUserInformation(@RequestBody UserInformationDTO userInformationDTO) throws URISyntaxException {
        log.debug("REST request to update UserInformation : {}", userInformationDTO);
        if (userInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserInformationDTO result = userInformationService.save(userInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-informations} : get all the userInformations.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInformations in body.
     */
    @GetMapping("/user-informations")
    public ResponseEntity<List<UserInformationDTO>> getAllUserInformations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("tasker-is-null".equals(filter)) {
            log.debug("REST request to get all UserInformations where tasker is null");
            return new ResponseEntity<>(userInformationService.findAllWhereTaskerIsNull(),
                    HttpStatus.OK);
        }
        if ("master-is-null".equals(filter)) {
            log.debug("REST request to get all UserInformations where master is null");
            return new ResponseEntity<>(userInformationService.findAllWhereMasterIsNull(),
                    HttpStatus.OK);
        }
        if ("address-is-null".equals(filter)) {
            log.debug("REST request to get all UserInformations where address is null");
            return new ResponseEntity<>(userInformationService.findAllWhereAddressIsNull(),
                    HttpStatus.OK);
        }
        if ("statistic-is-null".equals(filter)) {
            log.debug("REST request to get all UserInformations where statistic is null");
            return new ResponseEntity<>(userInformationService.findAllWhereStatisticIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserInformations");
        Page<UserInformationDTO> page = userInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-informations/:id} : get the "id" userInformation.
     *
     * @param id the id of the userInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-informations/{id}")
    public ResponseEntity<UserInformationDTO> getUserInformation(@PathVariable Long id) {
        log.debug("REST request to get UserInformation : {}", id);
        Optional<UserInformationDTO> userInformationDTO = userInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInformationDTO);
    }

    /**
     * {@code DELETE  /user-informations/:id} : delete the "id" userInformation.
     *
     * @param id the id of the userInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-informations/{id}")
    public ResponseEntity<Void> deleteUserInformation(@PathVariable Long id) {
        log.debug("REST request to delete UserInformation : {}", id);
        userInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
