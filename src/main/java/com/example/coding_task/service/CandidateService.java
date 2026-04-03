package com.example.coding_task.service;

import com.example.coding_task.data.CandidateRequest;
import com.example.coding_task.data.SkillRequest;
import com.example.coding_task.model.Candidate;
import com.example.coding_task.model.Skill;
import com.example.coding_task.repository.CandidateRepository;
import com.example.coding_task.repository.SkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final SkillService skillService;

    public Candidate addCandidate(CandidateRequest candidateRequest){
        if(candidateRepository.existsByEmail(candidateRequest.getEmail())){
            throw new RuntimeException("candidate with email: " + candidateRequest.getEmail()+" already exists");
        }

        Candidate candidate = new Candidate();
        candidate.setFullName(candidateRequest.getFullName());
        candidate.setDob(candidateRequest.getDob());
        candidate.setPhone(candidateRequest.getPhone());
        candidate.setEmail(candidateRequest.getEmail());

        return candidateRepository.save(candidate);
    }

    public List<Candidate> getCandidates(){
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id){
        return candidateRepository.findById(id).orElseThrow(()-> new RuntimeException("Candidate "+ id +" not found"));
    }

    public void deleteCandidateById(Long id){

        if(!candidateRepository.existsById(id)){
            throw new RuntimeException("Candidate "+ id +" not found");
        }
        candidateRepository.deleteById(id);
    }

    public Candidate updateCandidate(CandidateRequest candidateRequest, Long id){
        Candidate candidate = getCandidateById(id);

        candidate.setFullName(candidateRequest.getFullName());
        candidate.setDob(candidateRequest.getDob());
        candidate.setPhone(candidateRequest.getPhone());
        candidate.setEmail(candidateRequest.getEmail());
        return candidateRepository.save(candidate);
    }

    @Transactional
    public Candidate addSkill(Long skillId, Long id){
        Candidate candidate = getCandidateById(id);
        Skill skill = skillService.getSkillById(skillId);

        candidate.getSkills().add(skill);
        return candidateRepository.save(candidate);
    }

    @Transactional
    public Candidate deleteSkill(Long skillId, Long id){
        Candidate candidate = getCandidateById(id);
        Skill skill = skillService.getSkillById(skillId);
        candidate.getSkills().remove(skill);
        return candidateRepository.save(candidate);
    }

    public List<Candidate> searchByName(String name){
        return candidateRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<Candidate> findBySkills(List<String> skillNames){
        return candidateRepository.findBySkills(skillNames,skillNames.size());
    }

}
