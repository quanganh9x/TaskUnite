package com.vbtn.taskunite.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class MasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterDTO.class);
        MasterDTO masterDTO1 = new MasterDTO();
        masterDTO1.setId(1L);
        MasterDTO masterDTO2 = new MasterDTO();
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
        masterDTO2.setId(masterDTO1.getId());
        assertThat(masterDTO1).isEqualTo(masterDTO2);
        masterDTO2.setId(2L);
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
        masterDTO1.setId(null);
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
    }
}
