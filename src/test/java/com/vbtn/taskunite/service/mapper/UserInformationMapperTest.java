package com.vbtn.taskunite.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UserInformationMapperTest {

    private UserInformationMapper userInformationMapper;

    @BeforeEach
    public void setUp() {
        userInformationMapper = new UserInformationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(userInformationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userInformationMapper.fromId(null)).isNull();
    }
}
