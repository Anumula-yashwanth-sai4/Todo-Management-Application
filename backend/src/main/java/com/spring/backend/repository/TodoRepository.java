package com.spring.backend.repository;

import com.spring.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Task,Integer> {

    List<Task> findByCompleted(boolean completed);
}
