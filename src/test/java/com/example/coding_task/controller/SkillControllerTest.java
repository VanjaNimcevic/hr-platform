package com.example.coding_task.controller;


import com.example.coding_task.model.Skill;
import com.example.coding_task.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

@WebMvcTest(SkillController.class)
@AutoConfigureMockMvc
public class SkillControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @Test
    void addSkill_trebaVratitiCreatedStatus() throws Exception {
        Skill skill = new Skill(1L, "Java");
        when(skillService.addSkill(any())).thenReturn(skill);

        mockMvc.perform(
                post("/api/skills")
                .contentType(APPLICATION_JSON)
                .content("{\"skillName\":\"Java\"}")
        ).andExpect(status().isCreated())
         .andExpect(jsonPath("$.skillName").value("Java"));
    }

    @Test
    void getAllSkills_trebaVratitiListuVestina() throws Exception {

        List<Skill> skills = List.of(
                new Skill(1L, "Java"),
                new Skill(2L, "SQL")
        );
        when(skillService.getAllSkills()).thenReturn(skills);

        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[1].skillName").value("SQL"));
    }
}
