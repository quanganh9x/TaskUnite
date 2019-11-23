package com.vbtn.taskunite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class StatisticTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistic.class);
        Statistic statistic1 = new Statistic();
        statistic1.setId(1L);
        Statistic statistic2 = new Statistic();
        statistic2.setId(statistic1.getId());
        assertThat(statistic1).isEqualTo(statistic2);
        statistic2.setId(2L);
        assertThat(statistic1).isNotEqualTo(statistic2);
        statistic1.setId(null);
        assertThat(statistic1).isNotEqualTo(statistic2);
    }
}
