package com.pedsouto.taskservice.infra.repository;

import com.pedsouto.taskservice.infra.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Task> findByUserEmail(String email);
}
