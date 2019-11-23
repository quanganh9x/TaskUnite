package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.PaymentInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentInformation} and its DTO {@link PaymentInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentInformationMapper extends EntityMapper<PaymentInformationDTO, PaymentInformation> {

    @Mapping(source = "user.id", target = "userId")
    PaymentInformationDTO toDto(PaymentInformation paymentInformation);

    @Mapping(source = "userId", target = "user")
    PaymentInformation toEntity(PaymentInformationDTO paymentInformationDTO);

    default PaymentInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setId(id);
        return paymentInformation;
    }
}
