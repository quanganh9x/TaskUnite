package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PaymentInformationMapperTest {

    private PaymentInformationMapper paymentInformationMapper;

    @BeforeEach
    public void setUp() {
        paymentInformationMapper = new PaymentInformationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(paymentInformationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentInformationMapper.fromId(null)).isNull();
    }
}
