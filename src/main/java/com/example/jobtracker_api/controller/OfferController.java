package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.OfferDTO;
import com.example.jobtracker_api.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping public ResponseEntity<List<OfferDTO>> getAll() { return ResponseEntity.ok(offerService.getAllOffers()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<OfferDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(offerService.getOfferById(id)); }
    @PostMapping public ResponseEntity<OfferDTO> create(@Valid @RequestBody OfferDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(offerService.createOffer(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<OfferDTO> update(@PathVariable Long id, @Valid @RequestBody OfferDTO dto) { return ResponseEntity.ok(offerService.updateOffer(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { offerService.deleteOffer(id); return ResponseEntity.noContent().build(); }
}
