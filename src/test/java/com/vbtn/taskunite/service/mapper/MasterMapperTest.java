package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MasterMapperTest {

    private MasterMapper masterMapper;

    @BeforeEach
    public void setUp() {
        masterMapper = new MasterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(masterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(masterMapper.fromId(null)).isNull();
    }
}
