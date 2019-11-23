package com.vbtn.taskunite.service;

import com.vbtn.taskunite.service.dto.StatisticDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vbtn.taskunite.domain.Statistic}.
 */
public interface StatisticService {

    /**
     * Save a statistic.
     *
     * @param statisticDTO the entity to save.
     * @return the persisted entity.
     */
    StatisticDTO save(StatisticDTO statisticDTO);

    /**
     * Get all the statistics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatisticDTO> findAll(Pageable pageable);


    /**
     * Get the "id" statistic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticDTO> findOne(Long id);

    /**
     * Delete the "id" statistic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
