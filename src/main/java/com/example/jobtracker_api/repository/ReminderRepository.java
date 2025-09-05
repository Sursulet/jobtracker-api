package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    // add custom queries here if needed, e.g. Optional<Reminder> findByXxx(...);
}
