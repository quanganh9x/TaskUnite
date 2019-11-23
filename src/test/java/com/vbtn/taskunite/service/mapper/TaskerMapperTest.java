package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TaskerMapperTest {

    private TaskerMapper taskerMapper;

    @BeforeEach
    public void setUp() {
        taskerMapper = new TaskerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(taskerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskerMapper.fromId(null)).isNull();
    }
}
