package com.pedsouto.taskservice.service;

import com.pedsouto.taskservice.dto.TaskDto;
import com.pedsouto.taskservice.infra.entity.Task;
import com.pedsouto.taskservice.infra.enums.NotificationStatus;
import com.pedsouto.taskservice.infra.exception.ResourceNotFoundException;
import com.pedsouto.taskservice.infra.repository.TaskRepository;
import com.pedsouto.taskservice.infra.security.JwtUtil;
import com.pedsouto.taskservice.mapper.TaskMapper;
import com.pedsouto.taskservice.mapper.TaskUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final JwtUtil jwtUtil;
    private final TaskUpdateMapper taskUpdateMapper;

    public TaskDto saveTask(String token, TaskDto taskDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Task task = taskMapper.toEntity(taskDto);

        task.setUserEmail(email);
        task.setCreatedAt(LocalDateTime.now());
        task.setNotificationStatus(NotificationStatus.PENDING);

        return taskMapper.toDto(taskRepository.save(task));
    }

    public List<TaskDto> findTasksByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return taskMapper.toDtoList(taskRepository.findByEventDateBetween(startDate, endDate));
    }

    public List<TaskDto> findTasksByUserEmail(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        List<Task> tasks = taskRepository.findByUserEmail(email);
        return taskMapper.toDtoList(tasks);
    }

    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDto updateNotificationStatus(NotificationStatus notificationStatus, String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        task.setNotificationStatus(notificationStatus);
        return taskMapper.toDto(taskRepository.save(task));
    }

    public TaskDto updateTask(TaskDto taskDto, String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        taskUpdateMapper.updateTaskFromDto(taskDto, task);
        task.setUpdatedAt(LocalDateTime.now());

        return taskMapper.toDto(taskRepository.save(task));
    }
}
