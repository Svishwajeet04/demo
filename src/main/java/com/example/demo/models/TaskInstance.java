package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "task_instance")
public class TaskInstance {

    @Id
    private Long id;
    @Column("task_id")
    private Long taskId;

    @Column("start_time")
    private Timestamp startTime;

    @Column("end_time")
    private Timestamp endTime;

    private TaskStatus status;

    private String message;
}
