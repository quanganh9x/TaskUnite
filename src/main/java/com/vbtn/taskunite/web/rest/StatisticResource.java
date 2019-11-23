package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.service.StatisticService;
import com.vbtn.taskunite.web.rest.errors.BadRequestAlertException;
import com.vbtn.taskunite.service.dto.StatisticDTO;

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
 * REST controller for managing {@link com.vbtn.taskunite.domain.Statistic}.
 */
@RestController
@RequestMapping("/api")
public class StatisticResource {

    private final Logger log = LoggerFactory.getLogger(StatisticResource.class);

    private static final String ENTITY_NAME = "statistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticService statisticService;

    public StatisticResource(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * {@code POST  /statistics} : Create a new statistic.
     *
     * @param statisticDTO the statisticDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticDTO, or with status {@code 400 (Bad Request)} if the statistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statistics")
    public ResponseEntity<StatisticDTO> createStatistic(@RequestBody StatisticDTO statisticDTO) throws URISyntaxException {
        log.debug("REST request to save Statistic : {}", statisticDTO);
        if (statisticDTO.getId() != null) {
            throw new BadRequestAlertException("A new statistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatisticDTO result = statisticService.save(statisticDTO);
        return ResponseEntity.created(new URI("/api/statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statistics} : Updates an existing statistic.
     *
     * @param statisticDTO the statisticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticDTO,
     * or with status {@code 400 (Bad Request)} if the statisticDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statistics")
    public ResponseEntity<StatisticDTO> updateStatistic(@RequestBody StatisticDTO statisticDTO) throws URISyntaxException {
        log.debug("REST request to update Statistic : {}", statisticDTO);
        if (statisticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatisticDTO result = statisticService.save(statisticDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statisticDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statistics} : get all the statistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statistics in body.
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticDTO>> getAllStatistics(Pageable pageable) {
        log.debug("REST request to get a page of Statistics");
        Page<StatisticDTO> page = statisticService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistics/:id} : get the "id" statistic.
     *
     * @param id the id of the statisticDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistics/{id}")
    public ResponseEntity<StatisticDTO> getStatistic(@PathVariable Long id) {
        log.debug("REST request to get Statistic : {}", id);
        Optional<StatisticDTO> statisticDTO = statisticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticDTO);
    }

    /**
     * {@code DELETE  /statistics/:id} : delete the "id" statistic.
     *
     * @param id the id of the statisticDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statistics/{id}")
    public ResponseEntity<Void> deleteStatistic(@PathVariable Long id) {
        log.debug("REST request to delete Statistic : {}", id);
        statisticService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
