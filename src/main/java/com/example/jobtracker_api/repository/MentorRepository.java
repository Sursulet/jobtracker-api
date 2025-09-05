package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {}
