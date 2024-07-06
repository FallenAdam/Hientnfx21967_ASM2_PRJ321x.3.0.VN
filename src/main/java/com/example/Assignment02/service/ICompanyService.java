package com.example.Assignment02.service;

import com.example.Assignment02.dto.ICompanyDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.SaveJob;
import com.example.Assignment02.form.UpdateCompanyImageForm;
import com.example.Assignment02.form.UpdateCompanyProfileForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ICompanyService {
    public List<Company> findAllCompany();

    public ICompanyDTO getCompanieInHome();

    Company findById(int id);

    void updateCompanyProfile(UpdateCompanyProfileForm form);

    String updateCompanyImage(UpdateCompanyImageForm form, MultipartFile file) throws IOException;




    Company findByUserId(int id);
}
