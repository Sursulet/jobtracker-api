package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {}
