package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.CompanyDTO;
import com.example.jobtracker_api.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companys")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping public ResponseEntity<List<CompanyDTO>> getAll() { return ResponseEntity.ok(companyService.getAllCompanys()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<CompanyDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(companyService.getCompanyById(id)); }
    @PostMapping public ResponseEntity<CompanyDTO> create(@Valid @RequestBody CompanyDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<CompanyDTO> update(@PathVariable Long id, @Valid @RequestBody CompanyDTO dto) { return ResponseEntity.ok(companyService.updateCompany(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { companyService.deleteCompany(id); return ResponseEntity.noContent().build(); }
}
