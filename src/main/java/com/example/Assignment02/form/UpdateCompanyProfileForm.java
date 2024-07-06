package com.example.Assignment02.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCompanyProfileForm {
//    private int id;
    private String email;
    private String nameCompany;
    private String address;
    private String phoneNumber;
    private String description;
    private Integer idCompany;
    private Integer user_id;

}
