package com.vbtn.taskunite.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vbtn.taskunite.web.rest.TestUtil;

public class UserInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInformationDTO.class);
        UserInformationDTO userInformationDTO1 = new UserInformationDTO();
        userInformationDTO1.setId(1L);
        UserInformationDTO userInformationDTO2 = new UserInformationDTO();
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
        userInformationDTO2.setId(userInformationDTO1.getId());
        assertThat(userInformationDTO1).isEqualTo(userInformationDTO2);
        userInformationDTO2.setId(2L);
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
        userInformationDTO1.setId(null);
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
    }
}
