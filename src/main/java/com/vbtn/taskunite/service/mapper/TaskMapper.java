package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class, TaskCategoryMapper.class, TaskerMapper.class, MasterMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "tasker.id", target = "taskerId")
    @Mapping(source = "master.id", target = "masterId")
    TaskDTO toDto(Task task);

    @Mapping(source = "roomId", target = "room")
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "removeReviews", ignore = true)
    @Mapping(target = "removeTaskCategories", ignore = true)
    @Mapping(source = "taskerId", target = "tasker")
    @Mapping(source = "masterId", target = "master")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
