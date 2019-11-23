package com.vbtn.taskunite.web.rest;

import com.vbtn.taskunite.TaskUniteApp;
import com.vbtn.taskunite.domain.PaymentInformation;
import com.vbtn.taskunite.repository.PaymentInformationRepository;
import com.vbtn.taskunite.service.PaymentInformationService;
import com.vbtn.taskunite.service.dto.PaymentInformationDTO;
import com.vbtn.taskunite.service.mapper.PaymentInformationMapper;
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
 * Integration tests for the {@link PaymentInformationResource} REST controller.
 */
@SpringBootTest(classes = TaskUniteApp.class)
public class PaymentInformationResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private PaymentInformationMapper paymentInformationMapper;

    @Autowired
    private PaymentInformationService paymentInformationService;

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

    private MockMvc restPaymentInformationMockMvc;

    private PaymentInformation paymentInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentInformationResource paymentInformationResource = new PaymentInformationResource(paymentInformationService);
        this.restPaymentInformationMockMvc = MockMvcBuilders.standaloneSetup(paymentInformationResource)
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
    public static PaymentInformation createEntity(EntityManager em) {
        PaymentInformation paymentInformation = new PaymentInformation()
            .balance(DEFAULT_BALANCE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return paymentInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentInformation createUpdatedEntity(EntityManager em) {
        PaymentInformation paymentInformation = new PaymentInformation()
            .balance(UPDATED_BALANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return paymentInformation;
    }

    @BeforeEach
    public void initTest() {
        paymentInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentInformation() throws Exception {
        int databaseSizeBeforeCreate = paymentInformationRepository.findAll().size();

        // Create the PaymentInformation
        PaymentInformationDTO paymentInformationDTO = paymentInformationMapper.toDto(paymentInformation);
        restPaymentInformationMockMvc.perform(post("/api/payment-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentInformation in the database
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findAll();
        assertThat(paymentInformationList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentInformation testPaymentInformation = paymentInformationList.get(paymentInformationList.size() - 1);
        assertThat(testPaymentInformation.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testPaymentInformation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentInformation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPaymentInformation.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createPaymentInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentInformationRepository.findAll().size();

        // Create the PaymentInformation with an existing ID
        paymentInformation.setId(1L);
        PaymentInformationDTO paymentInformationDTO = paymentInformationMapper.toDto(paymentInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentInformationMockMvc.perform(post("/api/payment-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentInformation in the database
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findAll();
        assertThat(paymentInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentInformations() throws Exception {
        // Initialize the database
        paymentInformationRepository.saveAndFlush(paymentInformation);

        // Get all the paymentInformationList
        restPaymentInformationMockMvc.perform(get("/api/payment-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentInformation() throws Exception {
        // Initialize the database
        paymentInformationRepository.saveAndFlush(paymentInformation);

        // Get the paymentInformation
        restPaymentInformationMockMvc.perform(get("/api/payment-informations/{id}", paymentInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentInformation.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentInformation() throws Exception {
        // Get the paymentInformation
        restPaymentInformationMockMvc.perform(get("/api/payment-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentInformation() throws Exception {
        // Initialize the database
        paymentInformationRepository.saveAndFlush(paymentInformation);

        int databaseSizeBeforeUpdate = paymentInformationRepository.findAll().size();

        // Update the paymentInformation
        PaymentInformation updatedPaymentInformation = paymentInformationRepository.findById(paymentInformation.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentInformation are not directly saved in db
        em.detach(updatedPaymentInformation);
        updatedPaymentInformation
            .balance(UPDATED_BALANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        PaymentInformationDTO paymentInformationDTO = paymentInformationMapper.toDto(updatedPaymentInformation);

        restPaymentInformationMockMvc.perform(put("/api/payment-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentInformationDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentInformation in the database
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findAll();
        assertThat(paymentInformationList).hasSize(databaseSizeBeforeUpdate);
        PaymentInformation testPaymentInformation = paymentInformationList.get(paymentInformationList.size() - 1);
        assertThat(testPaymentInformation.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testPaymentInformation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentInformation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPaymentInformation.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentInformation() throws Exception {
        int databaseSizeBeforeUpdate = paymentInformationRepository.findAll().size();

        // Create the PaymentInformation
        PaymentInformationDTO paymentInformationDTO = paymentInformationMapper.toDto(paymentInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentInformationMockMvc.perform(put("/api/payment-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentInformation in the database
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findAll();
        assertThat(paymentInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentInformation() throws Exception {
        // Initialize the database
        paymentInformationRepository.saveAndFlush(paymentInformation);

        int databaseSizeBeforeDelete = paymentInformationRepository.findAll().size();

        // Delete the paymentInformation
        restPaymentInformationMockMvc.perform(delete("/api/payment-informations/{id}", paymentInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findAll();
        assertThat(paymentInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
