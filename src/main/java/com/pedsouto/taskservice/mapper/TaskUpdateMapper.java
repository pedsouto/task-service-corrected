package com.pedsouto.taskservice.mapper;

import com.pedsouto.taskservice.dto.TaskDto;
import com.pedsouto.taskservice.infra.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskUpdateMapper {

    void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);
}
