package com.example.Assignment02.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "RECRUITMENT")
@Data
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "CREATED_AT")
    private String createdAt;

    @Column(name = "`DESCRIPTION`")
    private String description;

    @Column(name = "EXPERIENCE")
    private String experience;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "`RANK`")
    private String rank;

    @Column(name = "SALARY")
    private String salary;

    @Column(name = "`STATUS` ")
    private int status;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "`TYPE`")
    private String type;

    @Column(name = "`VIEW`")
    private int view;

    @Column(name = "DEADLINE")
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "recruitment")
    private List<ApplyPosts> applyPosts;

}
