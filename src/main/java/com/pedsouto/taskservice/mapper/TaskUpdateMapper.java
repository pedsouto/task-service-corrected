package com.pedsouto.taskservice.mapper;

import com.pedsouto.taskservice.dto.TaskDto;
import com.pedsouto.taskservice.infra.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskUpdateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEmail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "notificationStatus", ignore = true)
    void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);
}