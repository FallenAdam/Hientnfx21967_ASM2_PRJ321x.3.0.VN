package com.example.Assignment02.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserForm {
    private String email;
    private String fullName;
    private String password;
    private String rePassword;
    private int role_id;
}
