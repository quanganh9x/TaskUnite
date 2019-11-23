package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TaskCategoryMapperTest {

    private TaskCategoryMapper taskCategoryMapper;

    @BeforeEach
    public void setUp() {
        taskCategoryMapper = new TaskCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(taskCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCategoryMapper.fromId(null)).isNull();
    }
}
