package com.vbtn.taskunite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class PaymentInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentInformation.class);
        PaymentInformation paymentInformation1 = new PaymentInformation();
        paymentInformation1.setId(1L);
        PaymentInformation paymentInformation2 = new PaymentInformation();
        paymentInformation2.setId(paymentInformation1.getId());
        assertThat(paymentInformation1).isEqualTo(paymentInformation2);
        paymentInformation2.setId(2L);
        assertThat(paymentInformation1).isNotEqualTo(paymentInformation2);
        paymentInformation1.setId(null);
        assertThat(paymentInformation1).isNotEqualTo(paymentInformation2);
    }
}
