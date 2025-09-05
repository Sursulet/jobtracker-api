package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {}
