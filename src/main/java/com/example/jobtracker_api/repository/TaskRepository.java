package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // add custom queries here if needed, e.g. Optional<Task> findByXxx(...);
}
