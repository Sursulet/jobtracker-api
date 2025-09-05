package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // add custom queries here if needed, e.g. Optional<Category> findByXxx(...);
}
