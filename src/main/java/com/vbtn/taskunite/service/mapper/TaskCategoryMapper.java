package com.vbtn.taskunite.service.mapper;

import com.vbtn.taskunite.domain.*;
import com.vbtn.taskunite.service.dto.TaskCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskCategory} and its DTO {@link TaskCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskCategoryMapper extends EntityMapper<TaskCategoryDTO, TaskCategory> {


    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTasks", ignore = true)
    @Mapping(target = "taskers", ignore = true)
    @Mapping(target = "removeTaskers", ignore = true)
    TaskCategory toEntity(TaskCategoryDTO taskCategoryDTO);

    default TaskCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setId(id);
        return taskCategory;
    }
}
