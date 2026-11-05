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
        String email = extractEmail(token);
        Task task = taskMapper.toEntity(taskDto);

        task.setId(null);
        task.setUserEmail(email);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(null);
        task.setNotificationStatus(NotificationStatus.PENDING);

        return taskMapper.toDto(taskRepository.save(task));
    }

    public List<TaskDto> findTasksByEventDateBetween(String token, LocalDateTime startDate, LocalDateTime endDate) {
        validateDateRange(startDate, endDate);
        String email = extractEmail(token);
        return taskMapper.toDtoList(
                taskRepository.findByUserEmailAndEventDateBetween(email, startDate, endDate));
    }

    public List<TaskDto> findTasksByUserEmail(String token) {
        String email = extractEmail(token);
        return taskMapper.toDtoList(taskRepository.findByUserEmail(email));
    }

    public void deleteTask(String token, String id) {
        Task task = findOwnedTask(id, extractEmail(token));
        taskRepository.delete(task);
    }

    public TaskDto updateNotificationStatus(String token, NotificationStatus notificationStatus, String id) {
        Task task = findOwnedTask(id, extractEmail(token));
        task.setNotificationStatus(notificationStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return taskMapper.toDto(taskRepository.save(task));
    }

    public TaskDto updateTask(String token, TaskDto taskDto, String id) {
        Task task = findOwnedTask(id, extractEmail(token));
        taskUpdateMapper.updateTaskFromDto(taskDto, task);
        task.setUpdatedAt(LocalDateTime.now());
        return taskMapper.toDto(taskRepository.save(task));
    }

    private Task findOwnedTask(String id, String email) {
        return taskRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    }

    private String extractEmail(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")
                || authorizationHeader.length() <= 7) {
            throw new IllegalArgumentException("Token Bearer inválido");
        }
        return jwtUtil.extractUsername(authorizationHeader.substring(7));
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("O período informado é inválido");
        }
    }
}