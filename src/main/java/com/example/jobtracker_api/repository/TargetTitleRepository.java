package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.TargetTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetTitleRepository extends JpaRepository<TargetTitle, Long> {
    // add custom queries here if needed, e.g. Optional<TargetTitle> findByXxx(...);
}
