package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
