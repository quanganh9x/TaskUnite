package com.vbtn.taskunite.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class PaymentInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentInformationDTO.class);
        PaymentInformationDTO paymentInformationDTO1 = new PaymentInformationDTO();
        paymentInformationDTO1.setId(1L);
        PaymentInformationDTO paymentInformationDTO2 = new PaymentInformationDTO();
        assertThat(paymentInformationDTO1).isNotEqualTo(paymentInformationDTO2);
        paymentInformationDTO2.setId(paymentInformationDTO1.getId());
        assertThat(paymentInformationDTO1).isEqualTo(paymentInformationDTO2);
        paymentInformationDTO2.setId(2L);
        assertThat(paymentInformationDTO1).isNotEqualTo(paymentInformationDTO2);
        paymentInformationDTO1.setId(null);
        assertThat(paymentInformationDTO1).isNotEqualTo(paymentInformationDTO2);
    }
}
