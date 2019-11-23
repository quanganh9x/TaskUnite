package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.TaskerService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.TaskerDTO;

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
 * REST controller for managing {@link com.vbtn.taskunite.domain.Tasker}.
 */
@RestController
@RequestMapping("/api")
public class TaskerResource {

    private final Logger log = LoggerFactory.getLogger(TaskerResource.class);

    private static final String ENTITY_NAME = "tasker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskerService taskerService;

    public TaskerResource(TaskerService taskerService) {
        this.taskerService = taskerService;
    }

    /**
     * {@code POST  /taskers} : Create a new tasker.
     *
     * @param taskerDTO the taskerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskerDTO, or with status {@code 400 (Bad Request)} if the tasker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taskers")
    public ResponseEntity<TaskerDTO> createTasker(@RequestBody TaskerDTO taskerDTO) throws URISyntaxException {
        log.debug("REST request to save Tasker : {}", taskerDTO);
        if (taskerDTO.getId() != null) {
            throw new BadRequestAlertException("A new tasker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskerDTO result = taskerService.save(taskerDTO);
        return ResponseEntity.created(new URI("/api/taskers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taskers} : Updates an existing tasker.
     *
     * @param taskerDTO the taskerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskerDTO,
     * or with status {@code 400 (Bad Request)} if the taskerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taskers")
    public ResponseEntity<TaskerDTO> updateTasker(@RequestBody TaskerDTO taskerDTO) throws URISyntaxException {
        log.debug("REST request to update Tasker : {}", taskerDTO);
        if (taskerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskerDTO result = taskerService.save(taskerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /taskers} : get all the taskers.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskers in body.
     */
    @GetMapping("/taskers")
    public ResponseEntity<List<TaskerDTO>> getAllTaskers(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Taskers");
        Page<TaskerDTO> page;
        if (eagerload) {
            page = taskerService.findAllWithEagerRelationships(pageable);
        } else {
            page = taskerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taskers/:id} : get the "id" tasker.
     *
     * @param id the id of the taskerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taskers/{id}")
    public ResponseEntity<TaskerDTO> getTasker(@PathVariable Long id) {
        log.debug("REST request to get Tasker : {}", id);
        Optional<TaskerDTO> taskerDTO = taskerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskerDTO);
    }

    /**
     * {@code DELETE  /taskers/:id} : delete the "id" tasker.
     *
     * @param id the id of the taskerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taskers/{id}")
    public ResponseEntity<Void> deleteTasker(@PathVariable Long id) {
        log.debug("REST request to delete Tasker : {}", id);
        taskerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
