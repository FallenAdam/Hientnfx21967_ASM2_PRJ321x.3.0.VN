package com.example.Assignment02.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateUserProfileForm {
    private int id;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String description;

}
