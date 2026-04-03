package com.example.coding_task.controller;

import com.example.coding_task.model.Candidate;
import com.example.coding_task.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    private Candidate napraviKandidata(){
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setFullName("Marko Markovic");
        candidate.setEmail("marko@gmail.com");
        candidate.setDob(LocalDate.of(1995, 3, 15));
        candidate.setPhone("0641234567");
        candidate.setSkills(new HashSet<>());
        return candidate;
    }

    @Test
    void addCandidate_trebaVratitiCreatedStatus() throws Exception {

        Candidate saved = napraviKandidata();
        when(candidateService.addCandidate(any())).thenReturn(saved);

        mockMvc.perform(
                        post("/api/candidates")
                                .contentType(APPLICATION_JSON)
                                .content("{\"fullName\":\"Marko Markovic\",\"dob\":\"1995-03-15\",\"phone\":\"0641234567\",\"email\":\"marko@gmail.com\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Marko Markovic"))
                .andExpect(jsonPath("$.email").value("marko@gmail.com"));
    }

    @Test
    void getAllCandidates_trebaVratitiListuKandidata() throws Exception {

        List<Candidate> candidates = List.of(napraviKandidata());
        when(candidateService.getCandidates()).thenReturn(candidates);

        mockMvc.perform(get("/api/candidates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Marko Markovic"));
    }

    @Test
    void getCandidateById_trebaVratitiKandidata() throws Exception {

        Candidate candidate = napraviKandidata();
        when(candidateService.getCandidateById(1L)).thenReturn(candidate);

        mockMvc.perform(get("/api/candidates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Marko Markovic"));
    }

    @Test
    void deleteCandidate_trebaVratitiNoContent() throws Exception {

        doNothing().when(candidateService).deleteCandidateById(1L);

        mockMvc.perform(delete("/api/candidates/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchByName_trebaVratitiKandidateSaImenom() throws Exception {

        List<Candidate> candidates = List.of(napraviKandidata());
        when(candidateService.searchByName("Marko")).thenReturn(candidates);

        mockMvc.perform(get("/api/candidates/search?name=Marko"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Marko Markovic"));
    }

    @Test
    void updateCandidate_trebaVratitiAzuriranogKandidata() throws Exception {

        Candidate updated = napraviKandidata();
        when(candidateService.updateCandidate(any(), eq(1L))).thenReturn(updated);

        mockMvc.perform(
                        put("/api/candidates/1")
                                .contentType(APPLICATION_JSON)
                                .content("{\"fullName\":\"Marko Markovic\",\"dob\":\"1995-03-15\",\"phone\":\"0641234567\",\"email\":\"marko@gmail.com\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Marko Markovic"));
    }

    @Test
    void addSkillToCandidate_trebaVratitiKandidataSaVestinom() throws Exception {

        Candidate candidate = napraviKandidata();
        when(candidateService.addSkill(1L, 1L)).thenReturn(candidate);

        mockMvc.perform(post("/api/candidates/1/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void removeSkillFromCandidate_trebaVratitiKandidataBezVestine() throws Exception {

        Candidate candidate = napraviKandidata();
        when(candidateService.deleteSkill(1L, 1L)).thenReturn(candidate);

        mockMvc.perform(delete("/api/candidates/1/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void searchBySkills_trebaVratitiKandidateSaVestinama() throws Exception {

        List<Candidate> candidates = List.of(napraviKandidata());
        when(candidateService.findBySkills(List.of("Java"))).thenReturn(candidates);

        mockMvc.perform(get("/api/candidates/search/skills?skillNames=Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("marko@gmail.com"));
    }
}
