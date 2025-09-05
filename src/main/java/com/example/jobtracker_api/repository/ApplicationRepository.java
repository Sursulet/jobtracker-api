package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {}
