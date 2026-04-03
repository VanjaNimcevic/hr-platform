package com.example.coding_task.controller;


import com.example.coding_task.data.SkillRequest;
import com.example.coding_task.model.Skill;
import com.example.coding_task.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    @Operation(summary = "Dodaj novu vestinu")
    @PostMapping
    public ResponseEntity<Skill> addSkill(@RequestBody SkillRequest skillRequest){
        Skill skill = skillService.addSkill(skillRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(skill);
    }

    @Operation(summary = "Prikazi sve vestine")
    @GetMapping
    public ResponseEntity<List<Skill>> findAllSkills(){
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @Operation(summary = "Obrisi vestinu")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
