package com.example.Assignment02.dto;

import com.example.Assignment02.entity.Role;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserLoginDTO extends User implements UserDetails {
    @Getter
    private int id;
    private String fullname;
    private boolean isAdmin;

    private int status;
    private Role role;

    public UserLoginDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserLoginDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, com.example.Assignment02.entity.User user, int status, Role role) {
        super(username, password, authorities);
        this.fullname = user.getFullName();
        this.isAdmin = checkAdmin(authorities);
        this.id = user.getId();
        this.status = status;
        this.role = role;
    }

    public boolean checkAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (Object au : authorities.toArray()) {
            if (au.toString().equals("Manager"))
                return true;
        }
        return false;
    }


    public UserLoginDTO(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
