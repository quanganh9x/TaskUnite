package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StatisticMapperTest {

    private StatisticMapper statisticMapper;

    @BeforeEach
    public void setUp() {
        statisticMapper = new StatisticMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(statisticMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(statisticMapper.fromId(null)).isNull();
    }
}
