package com.pedsouto.taskservice.mapper;

import com.pedsouto.taskservice.dto.TaskDto;
import com.pedsouto.taskservice.infra.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);

    List<Task> toEntityList(List<TaskDto> taskDtoList);

    List<TaskDto> toDtoList(List<Task> taskList);
}
