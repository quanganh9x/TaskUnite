package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.Master;
import com.vbtn.taskunite.repository.MasterRepository;
import com.vbtn.taskunite.service.MasterService;
import com.vbtn.taskunite.service.dto.MasterDTO;
import com.vbtn.taskunite.service.mapper.MasterMapper;
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
 * Integration tests for the {@link MasterResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class MasterResourceIT {

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private MasterService masterService;

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

    private MockMvc restMasterMockMvc;

    private Master master;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MasterResource masterResource = new MasterResource(masterService);
        this.restMasterMockMvc = MockMvcBuilders.standaloneSetup(masterResource)
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
    public static Master createEntity(EntityManager em) {
        Master master = new Master()
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return master;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Master createUpdatedEntity(EntityManager em) {
        Master master = new Master()
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return master;
    }

    @BeforeEach
    public void initTest() {
        master = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaster() throws Exception {
        int databaseSizeBeforeCreate = masterRepository.findAll().size();

        // Create the Master
        MasterDTO masterDTO = masterMapper.toDto(master);
        restMasterMockMvc.perform(post("/api/masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
            .andExpect(status().isCreated());

        // Validate the Master in the database
        List<Master> masterList = masterRepository.findAll();
        assertThat(masterList).hasSize(databaseSizeBeforeCreate + 1);
        Master testMaster = masterList.get(masterList.size() - 1);
        assertThat(testMaster.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMaster.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMaster.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testMaster.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = masterRepository.findAll().size();

        // Create the Master with an existing ID
        master.setId(1L);
        MasterDTO masterDTO = masterMapper.toDto(master);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasterMockMvc.perform(post("/api/masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Master in the database
        List<Master> masterList = masterRepository.findAll();
        assertThat(masterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMasters() throws Exception {
        // Initialize the database
        masterRepository.saveAndFlush(master);

        // Get all the masterList
        restMasterMockMvc.perform(get("/api/masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(master.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getMaster() throws Exception {
        // Initialize the database
        masterRepository.saveAndFlush(master);

        // Get the master
        restMasterMockMvc.perform(get("/api/masters/{id}", master.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(master.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaster() throws Exception {
        // Get the master
        restMasterMockMvc.perform(get("/api/masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster() throws Exception {
        // Initialize the database
        masterRepository.saveAndFlush(master);

        int databaseSizeBeforeUpdate = masterRepository.findAll().size();

        // Update the master
        Master updatedMaster = masterRepository.findById(master.getId()).get();
        // Disconnect from session so that the updates on updatedMaster are not directly saved in db
        em.detach(updatedMaster);
        updatedMaster
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        MasterDTO masterDTO = masterMapper.toDto(updatedMaster);

        restMasterMockMvc.perform(put("/api/masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
            .andExpect(status().isOk());

        // Validate the Master in the database
        List<Master> masterList = masterRepository.findAll();
        assertThat(masterList).hasSize(databaseSizeBeforeUpdate);
        Master testMaster = masterList.get(masterList.size() - 1);
        assertThat(testMaster.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMaster.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMaster.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testMaster.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingMaster() throws Exception {
        int databaseSizeBeforeUpdate = masterRepository.findAll().size();

        // Create the Master
        MasterDTO masterDTO = masterMapper.toDto(master);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterMockMvc.perform(put("/api/masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Master in the database
        List<Master> masterList = masterRepository.findAll();
        assertThat(masterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaster() throws Exception {
        // Initialize the database
        masterRepository.saveAndFlush(master);

        int databaseSizeBeforeDelete = masterRepository.findAll().size();

        // Delete the master
        restMasterMockMvc.perform(delete("/api/masters/{id}", master.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Master> masterList = masterRepository.findAll();
        assertThat(masterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
