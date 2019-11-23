package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.TaskCategoryService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.TaskCategoryDTO;

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
 * REST controller for managing {@link com.vbtn.taskunite.domain.TaskCategory}.
 */
@RestController
@RequestMapping("/api")
public class TaskCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TaskCategoryResource.class);

    private static final String ENTITY_NAME = "taskCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCategoryService taskCategoryService;

    public TaskCategoryResource(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }

    /**
     * {@code POST  /task-categories} : Create a new taskCategory.
     *
     * @param taskCategoryDTO the taskCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCategoryDTO, or with status {@code 400 (Bad Request)} if the taskCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-categories")
    public ResponseEntity<TaskCategoryDTO> createTaskCategory(@RequestBody TaskCategoryDTO taskCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save TaskCategory : {}", taskCategoryDTO);
        if (taskCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCategoryDTO result = taskCategoryService.save(taskCategoryDTO);
        return ResponseEntity.created(new URI("/api/task-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-categories} : Updates an existing taskCategory.
     *
     * @param taskCategoryDTO the taskCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the taskCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-categories")
    public ResponseEntity<TaskCategoryDTO> updateTaskCategory(@RequestBody TaskCategoryDTO taskCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update TaskCategory : {}", taskCategoryDTO);
        if (taskCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCategoryDTO result = taskCategoryService.save(taskCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-categories} : get all the taskCategories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskCategories in body.
     */
    @GetMapping("/task-categories")
    public ResponseEntity<List<TaskCategoryDTO>> getAllTaskCategories(Pageable pageable) {
        log.debug("REST request to get a page of TaskCategories");
        Page<TaskCategoryDTO> page = taskCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-categories/:id} : get the "id" taskCategory.
     *
     * @param id the id of the taskCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-categories/{id}")
    public ResponseEntity<TaskCategoryDTO> getTaskCategory(@PathVariable Long id) {
        log.debug("REST request to get TaskCategory : {}", id);
        Optional<TaskCategoryDTO> taskCategoryDTO = taskCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCategoryDTO);
    }

    /**
     * {@code DELETE  /task-categories/:id} : delete the "id" taskCategory.
     *
     * @param id the id of the taskCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-categories/{id}")
    public ResponseEntity<Void> deleteTaskCategory(@PathVariable Long id) {
        log.debug("REST request to delete TaskCategory : {}", id);
        taskCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
