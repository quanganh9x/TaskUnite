package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.UserInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInformation} and its DTO {@link UserInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserInformationMapper extends EntityMapper<UserInformationDTO, UserInformation> {

    @Mapping(source = "user.id", target = "userId")
    UserInformationDTO toDto(UserInformation userInformation);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReviews", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "removeNotifications", ignore = true)
    @Mapping(target = "tasker", ignore = true)
    @Mapping(target = "master", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "statistic", ignore = true)
    UserInformation toEntity(UserInformationDTO userInformationDTO);

    default UserInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInformation userInformation = new UserInformation();
        userInformation.setId(id);
        return userInformation;
    }
}
