package com.example.Assignment02.service.impl;

import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.Role;
import com.example.Assignment02.repository.IRoleRepository;
import com.example.Assignment02.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
            Optional<Role> optional = roleRepository.findById(id);
            if (!optional.isPresent())
                throw new RuntimeException("not found id");
            return optional.get();
        }


}
