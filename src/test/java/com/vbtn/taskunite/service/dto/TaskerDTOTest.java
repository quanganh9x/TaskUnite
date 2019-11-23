package com.vbtn.taskunite.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class TaskerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskerDTO.class);
        TaskerDTO taskerDTO1 = new TaskerDTO();
        taskerDTO1.setId(1L);
        TaskerDTO taskerDTO2 = new TaskerDTO();
        assertThat(taskerDTO1).isNotEqualTo(taskerDTO2);
        taskerDTO2.setId(taskerDTO1.getId());
        assertThat(taskerDTO1).isEqualTo(taskerDTO2);
        taskerDTO2.setId(2L);
        assertThat(taskerDTO1).isNotEqualTo(taskerDTO2);
        taskerDTO1.setId(null);
        assertThat(taskerDTO1).isNotEqualTo(taskerDTO2);
    }
}
