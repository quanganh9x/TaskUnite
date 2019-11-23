package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserInformationMapper.class, DistrictMapper.class, TaskerMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "dictrict.id", target = "dictrictId")
    @Mapping(source = "tasker.id", target = "taskerId")
    AddressDTO toDto(Address address);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "dictrictId", target = "dictrict")
    @Mapping(source = "taskerId", target = "tasker")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
