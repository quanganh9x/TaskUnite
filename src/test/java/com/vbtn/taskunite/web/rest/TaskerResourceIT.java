package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.Tasker;
import com.vbtn.taskunite.repository.TaskerRepository;
import com.vbtn.taskunite.service.TaskerService;
import com.vbtn.taskunite.service.dto.TaskerDTO;
import com.vbtn.taskunite.service.mapper.TaskerMapper;
import com.vbtn.taskunite.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static com.vbtn.taskunite.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskerResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class TaskerResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskerRepository taskerRepository;

    @Mock
    private TaskerRepository taskerRepositoryMock;

    @Autowired
    private TaskerMapper taskerMapper;

    @Mock
    private TaskerService taskerServiceMock;

    @Autowired
    private TaskerService taskerService;

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

    private MockMvc restTaskerMockMvc;

    private Tasker tasker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskerResource taskerResource = new TaskerResource(taskerService);
        this.restTaskerMockMvc = MockMvcBuilders.standaloneSetup(taskerResource)
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
    public static Tasker createEntity(EntityManager em) {
        Tasker tasker = new Tasker()
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return tasker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasker createUpdatedEntity(EntityManager em) {
        Tasker tasker = new Tasker()
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return tasker;
    }

    @BeforeEach
    public void initTest() {
        tasker = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasker() throws Exception {
        int databaseSizeBeforeCreate = taskerRepository.findAll().size();

        // Create the Tasker
        TaskerDTO taskerDTO = taskerMapper.toDto(tasker);
        restTaskerMockMvc.perform(post("/api/taskers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskerDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasker in the database
        List<Tasker> taskerList = taskerRepository.findAll();
        assertThat(taskerList).hasSize(databaseSizeBeforeCreate + 1);
        Tasker testTasker = taskerList.get(taskerList.size() - 1);
        assertThat(testTasker.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTasker.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTasker.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTasker.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testTasker.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createTaskerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskerRepository.findAll().size();

        // Create the Tasker with an existing ID
        tasker.setId(1L);
        TaskerDTO taskerDTO = taskerMapper.toDto(tasker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskerMockMvc.perform(post("/api/taskers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasker in the database
        List<Tasker> taskerList = taskerRepository.findAll();
        assertThat(taskerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaskers() throws Exception {
        // Initialize the database
        taskerRepository.saveAndFlush(tasker);

        // Get all the taskerList
        restTaskerMockMvc.perform(get("/api/taskers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasker.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTaskersWithEagerRelationshipsIsEnabled() throws Exception {
        TaskerResource taskerResource = new TaskerResource(taskerServiceMock);
        when(taskerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTaskerMockMvc = MockMvcBuilders.standaloneSetup(taskerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTaskerMockMvc.perform(get("/api/taskers?eagerload=true"))
        .andExpect(status().isOk());

        verify(taskerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTaskersWithEagerRelationshipsIsNotEnabled() throws Exception {
        TaskerResource taskerResource = new TaskerResource(taskerServiceMock);
            when(taskerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTaskerMockMvc = MockMvcBuilders.standaloneSetup(taskerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTaskerMockMvc.perform(get("/api/taskers?eagerload=true"))
        .andExpect(status().isOk());

            verify(taskerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTasker() throws Exception {
        // Initialize the database
        taskerRepository.saveAndFlush(tasker);

        // Get the tasker
        restTaskerMockMvc.perform(get("/api/taskers/{id}", tasker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tasker.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTasker() throws Exception {
        // Get the tasker
        restTaskerMockMvc.perform(get("/api/taskers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasker() throws Exception {
        // Initialize the database
        taskerRepository.saveAndFlush(tasker);

        int databaseSizeBeforeUpdate = taskerRepository.findAll().size();

        // Update the tasker
        Tasker updatedTasker = taskerRepository.findById(tasker.getId()).get();
        // Disconnect from session so that the updates on updatedTasker are not directly saved in db
        em.detach(updatedTasker);
        updatedTasker
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        TaskerDTO taskerDTO = taskerMapper.toDto(updatedTasker);

        restTaskerMockMvc.perform(put("/api/taskers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskerDTO)))
            .andExpect(status().isOk());

        // Validate the Tasker in the database
        List<Tasker> taskerList = taskerRepository.findAll();
        assertThat(taskerList).hasSize(databaseSizeBeforeUpdate);
        Tasker testTasker = taskerList.get(taskerList.size() - 1);
        assertThat(testTasker.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTasker.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTasker.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTasker.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testTasker.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTasker() throws Exception {
        int databaseSizeBeforeUpdate = taskerRepository.findAll().size();

        // Create the Tasker
        TaskerDTO taskerDTO = taskerMapper.toDto(tasker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskerMockMvc.perform(put("/api/taskers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasker in the database
        List<Tasker> taskerList = taskerRepository.findAll();
        assertThat(taskerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTasker() throws Exception {
        // Initialize the database
        taskerRepository.saveAndFlush(tasker);

        int databaseSizeBeforeDelete = taskerRepository.findAll().size();

        // Delete the tasker
        restTaskerMockMvc.perform(delete("/api/taskers/{id}", tasker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tasker> taskerList = taskerRepository.findAll();
        assertThat(taskerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
