package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
