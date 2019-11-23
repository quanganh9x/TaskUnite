package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.MasterService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.MasterDTO;

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
 * REST controller for managing {@link com.vbtn.taskunite.domain.Master}.
 */
@RestController
@RequestMapping("/api")
public class MasterResource {

    private final Logger log = LoggerFactory.getLogger(MasterResource.class);

    private static final String ENTITY_NAME = "master";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MasterService masterService;

    public MasterResource(MasterService masterService) {
        this.masterService = masterService;
    }

    /**
     * {@code POST  /masters} : Create a new master.
     *
     * @param masterDTO the masterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterDTO, or with status {@code 400 (Bad Request)} if the master has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/masters")
    public ResponseEntity<MasterDTO> createMaster(@RequestBody MasterDTO masterDTO) throws URISyntaxException {
        log.debug("REST request to save Master : {}", masterDTO);
        if (masterDTO.getId() != null) {
            throw new BadRequestAlertException("A new master cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MasterDTO result = masterService.save(masterDTO);
        return ResponseEntity.created(new URI("/api/masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /masters} : Updates an existing master.
     *
     * @param masterDTO the masterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterDTO,
     * or with status {@code 400 (Bad Request)} if the masterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the masterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/masters")
    public ResponseEntity<MasterDTO> updateMaster(@RequestBody MasterDTO masterDTO) throws URISyntaxException {
        log.debug("REST request to update Master : {}", masterDTO);
        if (masterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MasterDTO result = masterService.save(masterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /masters} : get all the masters.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masters in body.
     */
    @GetMapping("/masters")
    public ResponseEntity<List<MasterDTO>> getAllMasters(Pageable pageable) {
        log.debug("REST request to get a page of Masters");
        Page<MasterDTO> page = masterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /masters/:id} : get the "id" master.
     *
     * @param id the id of the masterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/masters/{id}")
    public ResponseEntity<MasterDTO> getMaster(@PathVariable Long id) {
        log.debug("REST request to get Master : {}", id);
        Optional<MasterDTO> masterDTO = masterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(masterDTO);
    }

    /**
     * {@code DELETE  /masters/:id} : delete the "id" master.
     *
     * @param id the id of the masterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/masters/{id}")
    public ResponseEntity<Void> deleteMaster(@PathVariable Long id) {
        log.debug("REST request to delete Master : {}", id);
        masterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
