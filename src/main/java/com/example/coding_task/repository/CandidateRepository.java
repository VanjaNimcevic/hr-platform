package com.example.coding_task.repository;

import com.example.coding_task.model.Candidate;
import com.example.coding_task.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByFullNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM Candidate c JOIN c.skills s " +
            "WHERE s.skillName IN :skillNames " +
            "GROUP BY c " +
            "HAVING COUNT(DISTINCT s) = :skillCount")
    List<Candidate> findBySkills(
            @Param("skillNames") List<String> skillNames,
            @Param("skillCount") long skillCount
    );

    boolean existsByEmail(String email);
}