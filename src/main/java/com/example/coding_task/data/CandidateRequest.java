package com.example.coding_task.data;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CandidateRequest {
    private String fullName;
    private LocalDate dob;
    private String phone;
    private String email;
}
