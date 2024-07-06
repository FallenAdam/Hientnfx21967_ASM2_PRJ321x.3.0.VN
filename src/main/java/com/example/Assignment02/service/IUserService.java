package com.example.Assignment02.service;

import com.example.Assignment02.dto.ProfileDTO;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.CreateUserForm;
import com.example.Assignment02.form.UpdateCompanyImageForm;
import com.example.Assignment02.form.UpdateUserImageForm;
import com.example.Assignment02.form.UpdateUserProfileForm;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IUserService extends UserDetailsService {
    List<User> getAllUser();


    void createUser(CreateUserForm form);


    ProfileDTO getProfile(int id);

    void updateUserProfile(UpdateUserProfileForm form) throws IOException;

    String updateUserImage(UpdateUserImageForm form, MultipartFile file) throws IOException;

    void updateUserStatus();
}
