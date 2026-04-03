package com.example.coding_task.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.coding_task.data.CandidateRequest;
import com.example.coding_task.model.Candidate;
import com.example.coding_task.model.Skill;
import com.example.coding_task.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {
    @Mock
    SkillService skillService;
    @Mock
    CandidateRepository candidateRepository;
    @InjectMocks
    CandidateService candidateService;


    //helper metoda
    private CandidateRequest napraviRequest() {
        CandidateRequest request = new CandidateRequest();
        request.setFullName("Marko Markovic");
        request.setDob(LocalDate.of(1995, 3, 15));
        request.setPhone("0641234567");
        request.setEmail("marko@gmail.com");
        return request;
    }
    //helper metoda
    private Candidate napraviKandidata() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setFullName("Marko Markovic");
        candidate.setDob(LocalDate.of(1995, 3, 15));
        candidate.setPhone("0641234567");
        candidate.setEmail("marko@gmail.com");
        candidate.setSkills(new HashSet<>());
        return candidate;
    }

    @Test
    void addCandidate_kadaEmailNePostoji_trebaUspesnoSacuvati() {

        CandidateRequest request = napraviRequest();
        Candidate saved = napraviKandidata();

        when(candidateRepository.existsByEmail("marko@gmail.com")).thenReturn(false);
        when(candidateRepository.save(any())).thenReturn(saved);

        Candidate result = candidateService.addCandidate(request);

        assertEquals("Marko Markovic", result.getFullName());
        assertEquals("marko@gmail.com", result.getEmail());
        verify(candidateRepository, times(1)).save(any());
    }

    @Test
    void addCandidate_kadaEmailVecPostoji_trebaBackitiGresku() {

        CandidateRequest request = napraviRequest();

        when(candidateRepository.existsByEmail("marko@gmail.com")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> candidateService.addCandidate(request)
        );

        assertTrue(exception.getMessage().contains("already exists"));

        verify(candidateRepository, never()).save(any());
    }

    @Test
    void getCandidateById_kadaPostoji_trebaVratitiKandidata() {

        Candidate candidate = napraviKandidata();
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        Candidate result = candidateService.getCandidateById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Marko Markovic", result.getFullName());
    }

    @Test
    void getCandidateById_kadaNePostoji_trebaBackitiGresku() {


        when(candidateRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> candidateService.getCandidateById(99L)
        );

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void deleteCandidate_kadaPostoji_trebaUspesnoBrisati() {

        when(candidateRepository.existsById(1L)).thenReturn(true);

        candidateService.deleteCandidateById(1L);

        verify(candidateRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCandidate_kadaNePostoji_trebaBackitiGresku() {

        when(candidateRepository.existsById(99L)).thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> candidateService.deleteCandidateById(99L)
        );

        verify(candidateRepository, never()).deleteById(any());
    }

    @Test
    void searchByName_trebaVratitiKandidateSaImenom() {

        Candidate candidate = napraviKandidata();
        when(candidateRepository.findByFullNameContainingIgnoreCase("Marko"))
                .thenReturn(List.of(candidate));

        List<Candidate> result = candidateService.searchByName("Marko");

        assertEquals(1, result.size());
        assertEquals("Marko Markovic", result.get(0).getFullName());
    }

    @Test
    void addSkillToCandidate_trebaUspesnoDodatiVestinu() {

        Candidate candidate = napraviKandidata();
        Skill skill = new Skill(1L, "Java");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(skillService.getSkillById(1L)).thenReturn(skill);
        when(candidateRepository.save(any())).thenReturn(candidate);

        Candidate result = candidateService.addSkill(1L, 1L);

        assertTrue(result.getSkills().contains(skill));
        verify(candidateRepository, times(1)).save(any());
    }

    @Test
    void updateCandidate_kadaPostoji_trebaUspesnoAzurirati() {

        Candidate candidate = napraviKandidata();
        CandidateRequest request = napraviRequest();
        request.setFullName("Marko Petrovic");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(any())).thenReturn(candidate);

        Candidate result = candidateService.updateCandidate(request, 1L);

        assertNotNull(result);
        verify(candidateRepository, times(1)).save(any());
    }

    @Test
    void removeSkillFromCandidate_trebaUspesnoUklonitiVestinu() {

        Candidate candidate = napraviKandidata();
        Skill skill = new Skill(1L, "Java");
        candidate.getSkills().add(skill);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(skillService.getSkillById(1L)).thenReturn(skill);
        when(candidateRepository.save(any())).thenReturn(candidate);

        Candidate result = candidateService.deleteSkill(1L, 1L);

        assertFalse(result.getSkills().contains(skill));
        verify(candidateRepository, times(1)).save(any());
    }

    @Test
    void searchBySkills_trebaVratitiKandidateSaVestinama() {


        Candidate candidate = napraviKandidata();
        List<String> skillNames = List.of("Java");
        when(candidateRepository.findBySkills(skillNames, 1L))
                .thenReturn(List.of(candidate));

        List<Candidate> result = candidateService.findBySkills(skillNames);

        assertEquals(1, result.size());
        verify(candidateRepository, times(1)).findBySkills(skillNames, 1L);
    }
}
