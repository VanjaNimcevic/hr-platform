package com.example.coding_task.controller;


import com.example.coding_task.data.CandidateRequest;
import com.example.coding_task.model.Candidate;
import com.example.coding_task.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Operation(summary = "Dodaj novog kandidata")
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody CandidateRequest candidateRequest) {
        Candidate created = candidateService.addCandidate(candidateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Uzmi sve kandidate")
    @GetMapping
    public ResponseEntity<List<Candidate>> findAll() {
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @Operation(summary = "Uzmi kandidata po ID-u")
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @Operation(summary = "Azuriraj kandidata")
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody CandidateRequest candidateRequest) {
        return ResponseEntity.ok(candidateService.updateCandidate(candidateRequest, id));
    }

    @Operation(summary = "Obrisi kandidata")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidateById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Dodaj vestinu kandidatu")
    @PostMapping("/{candidateId}/skills/{skillId}")
    public ResponseEntity<Candidate> addSkillToCandidate(
            @PathVariable Long candidateId,
            @PathVariable Long skillId) {
        return ResponseEntity.ok(candidateService.addSkill(skillId, candidateId));
    }

    @Operation(summary = "Ukloni vestinu kandidata")
    @DeleteMapping("/{candidateId}/skills/{skillId}")
    public ResponseEntity<Candidate> removeSkillFromCandidate(
            @PathVariable Long candidateId,
            @PathVariable Long skillId) {
        return ResponseEntity.ok(candidateService.deleteSkill(skillId, candidateId));
    }

    @Operation(summary = "Pretrzi kandidate po imenu")
    @GetMapping("/search")
    public ResponseEntity<List<Candidate>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(candidateService.searchByName(name));
    }

    @Operation(summary = "Pretrazi kandidate po vestinama")
    @GetMapping("/search/skills")
    public ResponseEntity<List<Candidate>> searchBySkills(@RequestParam List<String> skillNames) {
        return ResponseEntity.ok(candidateService.findBySkills(skillNames));
    }


}
