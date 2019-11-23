package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.StatisticDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Statistic} and its DTO {@link StatisticDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserInformationMapper.class})
public interface StatisticMapper extends EntityMapper<StatisticDTO, Statistic> {

    @Mapping(source = "user.id", target = "userId")
    StatisticDTO toDto(Statistic statistic);

    @Mapping(source = "userId", target = "user")
    Statistic toEntity(StatisticDTO statisticDTO);

    default Statistic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Statistic statistic = new Statistic();
        statistic.setId(id);
        return statistic;
    }
}
