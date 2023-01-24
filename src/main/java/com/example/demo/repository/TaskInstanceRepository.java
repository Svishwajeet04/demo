package com.example.demo.repository;

import com.example.demo.models.TaskInstance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskInstanceRepository extends ReactiveCrudRepository<TaskInstance , Long> {
}
