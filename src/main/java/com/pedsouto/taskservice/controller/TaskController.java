package com.pedsouto.taskservice.controller;

import com.pedsouto.taskservice.dto.TaskDto;
import com.pedsouto.taskservice.infra.enums.NotificationStatus;
import com.pedsouto.taskservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> saveTask(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(token, taskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findTasksByUserEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(taskService.findTasksByUserEmail(token));
    }

    @GetMapping("/events")
    public ResponseEntity<List<TaskDto>> findTasksByEventDateBetween(
            @RequestHeader("Authorization") String token,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(taskService.findTasksByEventDateBetween(token, startDate, endDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String id,
            @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(token, taskDto, id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto> updateNotificationStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String id,
            @RequestParam("status") NotificationStatus notificationStatus) {
        return ResponseEntity.ok(taskService.updateNotificationStatus(token, notificationStatus, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String id) {
        taskService.deleteTask(token, id);
        return ResponseEntity.noContent().build();
    }
}