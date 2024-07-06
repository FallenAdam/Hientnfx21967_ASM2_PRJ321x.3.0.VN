package com.example.Assignment02.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRecruitmentForm {
    private int id;
    private String title;
    private String description;
    private String experience;
    private int quantity;
    private String address;
    private String deadline;
    private String salary;
    private String type;
    private int category_id;


}
