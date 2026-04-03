package com.example.coding_task.service;
import com.example.coding_task.data.SkillRequest;
import com.example.coding_task.model.Skill;
import com.example.coding_task.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillService skillService;

    @Test
    void addSkill_kadaVestinaNePostoji() {
        SkillRequest request = new SkillRequest();
        request.setSkillName("Java");

        Skill savedSkill = new Skill(1L, "Java");

        when(skillRepository.existsBySkillName("Java")).thenReturn(false);
        when(skillRepository.save(any())).thenReturn(savedSkill);

        Skill result = skillService.addSkill(request);

        assertEquals("Java", result.getSkillName());
    }

    @Test
    void addSkill_kadaVestinaVecPostoji_trebaBackitiGresku() {

        SkillRequest request = new SkillRequest();
        request.setSkillName("Java");

        when(skillRepository.existsBySkillName("Java")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> skillService.addSkill(request)
        );

        assertTrue(exception.getMessage().contains("already exists"));

        verify(skillRepository, never()).save(any());
    }

    @Test
    void getSkillById_kadaPostoji_trebaVratitiVestinu() {

        Skill skill = new Skill(1L, "Java");
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(1L);

        assertEquals("Java", result.getSkillName());
    }
    @Test
    void getSkillById_kadaNePostoji_trebaBackitiGresku() {


        when(skillRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> skillService.getSkillById(99L)
        );

        assertTrue(exception.getMessage().contains("not found"));
    }
}
