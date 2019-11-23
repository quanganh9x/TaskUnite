package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.UserInformation;
import com.vbtn.taskunite.repository.UserInformationRepository;
import com.vbtn.taskunite.service.UserInformationService;
import com.vbtn.taskunite.service.dto.UserInformationDTO;
import com.vbtn.taskunite.service.mapper.UserInformationMapper;
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
 * Integration tests for the {@link UserInformationResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class UserInformationResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserInformationMapper userInformationMapper;

    @Autowired
    private UserInformationService userInformationService;

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

    private MockMvc restUserInformationMockMvc;

    private UserInformation userInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserInformationResource userInformationResource = new UserInformationResource(userInformationService);
        this.restUserInformationMockMvc = MockMvcBuilders.standaloneSetup(userInformationResource)
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
    public static UserInformation createEntity(EntityManager em) {
        UserInformation userInformation = new UserInformation()
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return userInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInformation createUpdatedEntity(EntityManager em) {
        UserInformation userInformation = new UserInformation()
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return userInformation;
    }

    @BeforeEach
    public void initTest() {
        userInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInformation() throws Exception {
        int databaseSizeBeforeCreate = userInformationRepository.findAll().size();

        // Create the UserInformation
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);
        restUserInformationMockMvc.perform(post("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeCreate + 1);
        UserInformation testUserInformation = userInformationList.get(userInformationList.size() - 1);
        assertThat(testUserInformation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserInformation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserInformation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserInformation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserInformation.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createUserInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInformationRepository.findAll().size();

        // Create the UserInformation with an existing ID
        userInformation.setId(1L);
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInformationMockMvc.perform(post("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserInformations() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        // Get all the userInformationList
        restUserInformationMockMvc.perform(get("/api/user-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        // Get the userInformation
        restUserInformationMockMvc.perform(get("/api/user-informations/{id}", userInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userInformation.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserInformation() throws Exception {
        // Get the userInformation
        restUserInformationMockMvc.perform(get("/api/user-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        int databaseSizeBeforeUpdate = userInformationRepository.findAll().size();

        // Update the userInformation
        UserInformation updatedUserInformation = userInformationRepository.findById(userInformation.getId()).get();
        // Disconnect from session so that the updates on updatedUserInformation are not directly saved in db
        em.detach(updatedUserInformation);
        updatedUserInformation
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(updatedUserInformation);

        restUserInformationMockMvc.perform(put("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isOk());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeUpdate);
        UserInformation testUserInformation = userInformationList.get(userInformationList.size() - 1);
        assertThat(testUserInformation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserInformation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserInformation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserInformation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserInformation.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInformation() throws Exception {
        int databaseSizeBeforeUpdate = userInformationRepository.findAll().size();

        // Create the UserInformation
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInformationMockMvc.perform(put("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        int databaseSizeBeforeDelete = userInformationRepository.findAll().size();

        // Delete the userInformation
        restUserInformationMockMvc.perform(delete("/api/user-informations/{id}", userInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
