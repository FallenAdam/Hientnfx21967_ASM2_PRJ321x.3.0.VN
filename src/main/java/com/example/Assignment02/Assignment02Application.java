package com.example.Assignment02;

import com.example.Assignment02.entity.Role;
import com.example.Assignment02.form.CreateUserForm;
import com.example.Assignment02.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Assignment02Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment02Application.class, args);

	}

}
