package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // add custom queries here if needed, e.g. Optional<Note> findByXxx(...);
}
