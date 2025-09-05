package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // add custom queries here if needed, e.g. Optional<Role> findByXxx(...);
}
