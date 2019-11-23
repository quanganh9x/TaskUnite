package com.vbtn.taskunite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class TaskCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCategory.class);
        TaskCategory taskCategory1 = new TaskCategory();
        taskCategory1.setId(1L);
        TaskCategory taskCategory2 = new TaskCategory();
        taskCategory2.setId(taskCategory1.getId());
        assertThat(taskCategory1).isEqualTo(taskCategory2);
        taskCategory2.setId(2L);
        assertThat(taskCategory1).isNotEqualTo(taskCategory2);
        taskCategory1.setId(null);
        assertThat(taskCategory1).isNotEqualTo(taskCategory2);
    }
}
