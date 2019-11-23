package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.PlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plan} and its DTO {@link PlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface PlanMapper extends EntityMapper<PlanDTO, Plan> {

    @Mapping(source = "task.id", target = "taskId")
    PlanDTO toDto(Plan plan);

    @Mapping(source = "taskId", target = "task")
    Plan toEntity(PlanDTO planDTO);

    default Plan fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plan plan = new Plan();
        plan.setId(id);
        return plan;
    }
}
