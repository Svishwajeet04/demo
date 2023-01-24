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
@Table(name = "task")
public class Task {

    @Id
    private Long id;

    private String name;

    private String description;

    private Timestamp deadline;

    @Column("user_id")
    private Long userId;

    private Frequency frequency;
}
