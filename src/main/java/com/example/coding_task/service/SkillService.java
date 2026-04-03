package com.example.coding_task.service;

import com.example.coding_task.data.SkillRequest;
import com.example.coding_task.model.Skill;
import com.example.coding_task.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public Skill addSkill(SkillRequest skillRequest){
        if(skillRepository.existsBySkillName(skillRequest.getSkillName())){
            throw new RuntimeException("Skill "+ skillRequest.getSkillName()+" already exists");
        }

        Skill skill = new Skill();
        skill.setSkillName(skillRequest.getSkillName());
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills(){
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id){
        return skillRepository.findById(id).orElseThrow(()-> new RuntimeException("Skill "+ id +" not found"));
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill " + id + " not found");
        }
        skillRepository.deleteById(id);
    }
}
