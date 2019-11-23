package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.TaskerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tasker} and its DTO {@link TaskerDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserInformationMapper.class, TaskCategoryMapper.class})
public interface TaskerMapper extends EntityMapper<TaskerDTO, Tasker> {

    @Mapping(source = "user.id", target = "userId")
    TaskerDTO toDto(Tasker tasker);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    @Mapping(target = "workingAddresses", ignore = true)
    @Mapping(target = "removeWorkingAddresses", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTasks", ignore = true)
    @Mapping(target = "removeTaskCategories", ignore = true)
    Tasker toEntity(TaskerDTO taskerDTO);

    default Tasker fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tasker tasker = new Tasker();
        tasker.setId(id);
        return tasker;
    }
}
