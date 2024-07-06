package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.ProfileDTO;
import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.CreateUserForm;
import com.example.Assignment02.form.UpdateCompanyImageForm;
import com.example.Assignment02.form.UpdateUserImageForm;
import com.example.Assignment02.form.UpdateUserProfileForm;
import com.example.Assignment02.repository.ICompanyRepository;
import com.example.Assignment02.repository.IFollowCompanyRepository;
import com.example.Assignment02.repository.IRoleRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PrePersist;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IFollowCompanyRepository followCompanyRepository;

    @Autowired
    private ICompanyRepository companyRepository;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public final  String UPLOAD_DIRECTORY_USER = System.getProperty("user.dir") + "/src/main/resources/static/assets/images/";


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(CreateUserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setFullName(form.getFullName());
        user.setPassWord(bCryptPasswordEncoder.encode(form.getPassword()));
        user.setRole(roleRepository.findById(form.getRole_id()).get());
        user.setEmail(form.getEmail());
        userRepository.save(user);
    }

    @Override
    public ProfileDTO getProfile(int id) {
        User user = userRepository.findById(id).get();
        Company company  = companyRepository.findByUserId(id);
        ProfileDTO profileDTO = new ProfileDTO(user, company);
        return profileDTO;
    }
    @PrePersist
    public void updateUserStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().equals("anonymousUser"))
            throw new RuntimeException("not found");

        org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserLoginDTO userLoginDTO = (UserLoginDTO) loadUserByUsername(userLogin.getUsername());

        Optional<User> optionalUser = userRepository.findById(userLoginDTO.getId());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.getStatus() == 0)
                user.setStatus(1);
            userRepository.save(user);
        }else {
                throw new RuntimeException("Not Found ID");
            }
        }



    @Override
    public void updateUserProfile(UpdateUserProfileForm form) throws IOException {
        Optional<User> optional= userRepository.findById(form.getId());
        if (!optional.isPresent())
            throw new RuntimeException("Not Found ID :" +form.getId());
        User user = optional.get();
        user.setEmail(form.getEmail());
        user.setFullName(form.getFullName());
        user.setAddress(form.getAddress());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setDescription(form.getDescription());
        userRepository.save(user);

    }

    @Override
    public String updateUserImage(UpdateUserImageForm form, MultipartFile file) throws IOException {
        Optional<User> optional= userRepository.findById(form.getId());
        if (!optional.isPresent())
            throw new RuntimeException("Not Found ID :" +form.getId());
        User user = optional.get();

        // Thêm anh vào thư muc /assets/images/ và database
        String[] arr = file.getOriginalFilename().split("\\.");
        String tailImg = ""; // Lấy đuôi của ảnh (.pjg)
        if (arr.length>0)
            tailImg = arr[arr.length-1];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");//abc.jpg
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_USER, simpleDateFormat.format(new Date())+ "."+ tailImg);
        Files.write(fileNameAndPath, file.getBytes());//  ghi file vao thu muc tuong ung

        //Lưu ảnh vào User
        user.setImage(simpleDateFormat.format(new Date())+ "."+ tailImg);
        userRepository.save(user);

        return user.getImage();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // tifm user theo email
        User user = userRepository.findByEmail(username);
        if (Objects.isNull(user)){
            throw new RuntimeException("Not Found UserName");
        }
        //tra ra userdetail
        return new UserLoginDTO(username, user.getPassWord(), AuthorityUtils.createAuthorityList(user.getRole().getRoleName()), user, user.getStatus(), user.getRole());
    }
}
