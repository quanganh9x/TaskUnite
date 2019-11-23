package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.Statistic;
import com.vbtn.taskunite.repository.StatisticRepository;
import com.vbtn.taskunite.service.StatisticService;
import com.vbtn.taskunite.service.dto.StatisticDTO;
import com.vbtn.taskunite.service.mapper.StatisticMapper;
import com.vbtn.taskunite.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.vbtn.taskunite.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StatisticResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class StatisticResourceIT {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_EXPERIENCE = 1;
    private static final Integer UPDATED_EXPERIENCE = 2;

    private static final Integer DEFAULT_COMPLETED_TASK = 1;
    private static final Integer UPDATED_COMPLETED_TASK = 2;

    private static final Integer DEFAULT_INCOMPLETED_TASK = 1;
    private static final Integer UPDATED_INCOMPLETED_TASK = 2;

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final Integer DEFAULT_RANKING = 1;
    private static final Integer UPDATED_RANKING = 2;

    private static final Double DEFAULT_BONUS = 1D;
    private static final Double UPDATED_BONUS = 2D;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStatisticMockMvc;

    private Statistic statistic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatisticResource statisticResource = new StatisticResource(statisticService);
        this.restStatisticMockMvc = MockMvcBuilders.standaloneSetup(statisticResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistic createEntity(EntityManager em) {
        Statistic statistic = new Statistic()
            .level(DEFAULT_LEVEL)
            .experience(DEFAULT_EXPERIENCE)
            .completedTask(DEFAULT_COMPLETED_TASK)
            .incompletedTask(DEFAULT_INCOMPLETED_TASK)
            .rating(DEFAULT_RATING)
            .ranking(DEFAULT_RANKING)
            .bonus(DEFAULT_BONUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return statistic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistic createUpdatedEntity(EntityManager em) {
        Statistic statistic = new Statistic()
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .completedTask(UPDATED_COMPLETED_TASK)
            .incompletedTask(UPDATED_INCOMPLETED_TASK)
            .rating(UPDATED_RATING)
            .ranking(UPDATED_RANKING)
            .bonus(UPDATED_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return statistic;
    }

    @BeforeEach
    public void initTest() {
        statistic = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatistic() throws Exception {
        int databaseSizeBeforeCreate = statisticRepository.findAll().size();

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);
        restStatisticMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statisticDTO)))
            .andExpect(status().isCreated());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeCreate + 1);
        Statistic testStatistic = statisticList.get(statisticList.size() - 1);
        assertThat(testStatistic.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testStatistic.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testStatistic.getCompletedTask()).isEqualTo(DEFAULT_COMPLETED_TASK);
        assertThat(testStatistic.getIncompletedTask()).isEqualTo(DEFAULT_INCOMPLETED_TASK);
        assertThat(testStatistic.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testStatistic.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testStatistic.getBonus()).isEqualTo(DEFAULT_BONUS);
        assertThat(testStatistic.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStatistic.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testStatistic.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createStatisticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statisticRepository.findAll().size();

        // Create the Statistic with an existing ID
        statistic.setId(1L);
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statisticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatistics() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList
        restStatisticMockMvc.perform(get("/api/statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].completedTask").value(hasItem(DEFAULT_COMPLETED_TASK)))
            .andExpect(jsonPath("$.[*].incompletedTask").value(hasItem(DEFAULT_INCOMPLETED_TASK)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING)))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getStatistic() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        // Get the statistic
        restStatisticMockMvc.perform(get("/api/statistics/{id}", statistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statistic.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.completedTask").value(DEFAULT_COMPLETED_TASK))
            .andExpect(jsonPath("$.incompletedTask").value(DEFAULT_INCOMPLETED_TASK))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatistic() throws Exception {
        // Get the statistic
        restStatisticMockMvc.perform(get("/api/statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatistic() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        int databaseSizeBeforeUpdate = statisticRepository.findAll().size();

        // Update the statistic
        Statistic updatedStatistic = statisticRepository.findById(statistic.getId()).get();
        // Disconnect from session so that the updates on updatedStatistic are not directly saved in db
        em.detach(updatedStatistic);
        updatedStatistic
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .completedTask(UPDATED_COMPLETED_TASK)
            .incompletedTask(UPDATED_INCOMPLETED_TASK)
            .rating(UPDATED_RATING)
            .ranking(UPDATED_RANKING)
            .bonus(UPDATED_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        StatisticDTO statisticDTO = statisticMapper.toDto(updatedStatistic);

        restStatisticMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statisticDTO)))
            .andExpect(status().isOk());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeUpdate);
        Statistic testStatistic = statisticList.get(statisticList.size() - 1);
        assertThat(testStatistic.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testStatistic.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testStatistic.getCompletedTask()).isEqualTo(UPDATED_COMPLETED_TASK);
        assertThat(testStatistic.getIncompletedTask()).isEqualTo(UPDATED_INCOMPLETED_TASK);
        assertThat(testStatistic.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testStatistic.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testStatistic.getBonus()).isEqualTo(UPDATED_BONUS);
        assertThat(testStatistic.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStatistic.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testStatistic.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = statisticRepository.findAll().size();

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statisticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatistic() throws Exception {
        // Initialize the database
        statisticRepository.saveAndFlush(statistic);

        int databaseSizeBeforeDelete = statisticRepository.findAll().size();

        // Delete the statistic
        restStatisticMockMvc.perform(delete("/api/statistics/{id}", statistic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statistic> statisticList = statisticRepository.findAll();
        assertThat(statisticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
