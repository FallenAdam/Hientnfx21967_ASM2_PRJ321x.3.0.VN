package com.example.Assignment02.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "COMPANY")
@Data
public class Company  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "`DESCRIPTION`")
    private String description;

    @Column(name = "EMAIL" , nullable = false, unique = true)
    private String email;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "NAME_COMPANY")
    private String nameCompany;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "`STATUS`")
    private int status;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "company")
    private List<Recruitment> recruitments;

}
