package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.TaskCategory;
import com.vbtn.taskunite.repository.TaskCategoryRepository;
import com.vbtn.taskunite.service.TaskCategoryService;
import com.vbtn.taskunite.service.dto.TaskCategoryDTO;
import com.vbtn.taskunite.service.mapper.TaskCategoryMapper;
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
 * Integration tests for the {@link TaskCategoryResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class TaskCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_PRICE = 1D;
    private static final Double UPDATED_MIN_PRICE = 2D;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Autowired
    private TaskCategoryMapper taskCategoryMapper;

    @Autowired
    private TaskCategoryService taskCategoryService;

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

    private MockMvc restTaskCategoryMockMvc;

    private TaskCategory taskCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskCategoryResource taskCategoryResource = new TaskCategoryResource(taskCategoryService);
        this.restTaskCategoryMockMvc = MockMvcBuilders.standaloneSetup(taskCategoryResource)
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
    public static TaskCategory createEntity(EntityManager em) {
        TaskCategory taskCategory = new TaskCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .minPrice(DEFAULT_MIN_PRICE)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return taskCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCategory createUpdatedEntity(EntityManager em) {
        TaskCategory taskCategory = new TaskCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .minPrice(UPDATED_MIN_PRICE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return taskCategory;
    }

    @BeforeEach
    public void initTest() {
        taskCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskCategory() throws Exception {
        int databaseSizeBeforeCreate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory
        TaskCategoryDTO taskCategoryDTO = taskCategoryMapper.toDto(taskCategory);
        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCategory testTaskCategory = taskCategoryList.get(taskCategoryList.size() - 1);
        assertThat(testTaskCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskCategory.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testTaskCategory.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testTaskCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskCategory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTaskCategory.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testTaskCategory.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createTaskCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory with an existing ID
        taskCategory.setId(1L);
        TaskCategoryDTO taskCategoryDTO = taskCategoryMapper.toDto(taskCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaskCategories() throws Exception {
        // Initialize the database
        taskCategoryRepository.saveAndFlush(taskCategory);

        // Get all the taskCategoryList
        restTaskCategoryMockMvc.perform(get("/api/task-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.saveAndFlush(taskCategory);

        // Get the taskCategory
        restTaskCategoryMockMvc.perform(get("/api/task-categories/{id}", taskCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskCategory() throws Exception {
        // Get the taskCategory
        restTaskCategoryMockMvc.perform(get("/api/task-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.saveAndFlush(taskCategory);

        int databaseSizeBeforeUpdate = taskCategoryRepository.findAll().size();

        // Update the taskCategory
        TaskCategory updatedTaskCategory = taskCategoryRepository.findById(taskCategory.getId()).get();
        // Disconnect from session so that the updates on updatedTaskCategory are not directly saved in db
        em.detach(updatedTaskCategory);
        updatedTaskCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .minPrice(UPDATED_MIN_PRICE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        TaskCategoryDTO taskCategoryDTO = taskCategoryMapper.toDto(updatedTaskCategory);

        restTaskCategoryMockMvc.perform(put("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeUpdate);
        TaskCategory testTaskCategory = taskCategoryList.get(taskCategoryList.size() - 1);
        assertThat(testTaskCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskCategory.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testTaskCategory.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testTaskCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskCategory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTaskCategory.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testTaskCategory.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskCategory() throws Exception {
        int databaseSizeBeforeUpdate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory
        TaskCategoryDTO taskCategoryDTO = taskCategoryMapper.toDto(taskCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCategoryMockMvc.perform(put("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.saveAndFlush(taskCategory);

        int databaseSizeBeforeDelete = taskCategoryRepository.findAll().size();

        // Delete the taskCategory
        restTaskCategoryMockMvc.perform(delete("/api/task-categories/{id}", taskCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
