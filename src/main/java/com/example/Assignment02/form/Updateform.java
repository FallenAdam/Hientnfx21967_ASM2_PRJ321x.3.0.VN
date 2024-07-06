package com.example.Assignment02.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Updateform {

    private  Long id;
    private  String name;
    private MultipartFile urlImg;

}
