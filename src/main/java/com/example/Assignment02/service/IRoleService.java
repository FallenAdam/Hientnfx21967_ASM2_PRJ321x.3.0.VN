package com.example.Assignment02.service;

import com.example.Assignment02.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRole();

    Role findById(int id);

}
