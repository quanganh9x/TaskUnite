package com.vbtn.taskunite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class MasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Master.class);
        Master master1 = new Master();
        master1.setId(1L);
        Master master2 = new Master();
        master2.setId(master1.getId());
        assertThat(master1).isEqualTo(master2);
        master2.setId(2L);
        assertThat(master1).isNotEqualTo(master2);
        master1.setId(null);
        assertThat(master1).isNotEqualTo(master2);
    }
}
