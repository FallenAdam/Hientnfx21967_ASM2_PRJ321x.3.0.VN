package com.example.Assignment02.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateUserImageForm {
    private int id;
    private MultipartFile file;

}
