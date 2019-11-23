package com.vbtn.taskunite.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class TaskCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCategoryDTO.class);
        TaskCategoryDTO taskCategoryDTO1 = new TaskCategoryDTO();
        taskCategoryDTO1.setId(1L);
        TaskCategoryDTO taskCategoryDTO2 = new TaskCategoryDTO();
        assertThat(taskCategoryDTO1).isNotEqualTo(taskCategoryDTO2);
        taskCategoryDTO2.setId(taskCategoryDTO1.getId());
        assertThat(taskCategoryDTO1).isEqualTo(taskCategoryDTO2);
        taskCategoryDTO2.setId(2L);
        assertThat(taskCategoryDTO1).isNotEqualTo(taskCategoryDTO2);
        taskCategoryDTO1.setId(null);
        assertThat(taskCategoryDTO1).isNotEqualTo(taskCategoryDTO2);
    }
}
