package com.example.coding_task.repository;

import com.example.coding_task.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsBySkillName(String skillName);
}