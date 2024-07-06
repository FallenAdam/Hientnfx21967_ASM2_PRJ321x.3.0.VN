package com.example.Assignment02.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "`USER`")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "`DESCRIPTION`")
    private String description;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "`PASSWORD`")
    private String passWord;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "`STATUS`")
    private int status;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "CV_ID")
    private Cv cv;

    @OneToMany(mappedBy = "user")
    private List<ApplyPosts> applyPosts;

    @PrePersist
    private void onPrePersist() {
        if (getRole().getId() == 2){
            setStatus(1);
        }else {
            setStatus(0);

        }
    }


}
