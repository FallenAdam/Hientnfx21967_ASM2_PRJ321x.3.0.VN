package com.example.Assignment02.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="APPLY_POST")
@Data
public class ApplyPosts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CREATED_AT")
    private String createdAt;

    @Column(name = "NAME_CV")
    private String nameCv;

    @Column(name = "`STATUS`")
    private int  status;

    @Column(name = "`TEXT`")
    private String text;

    @ManyToOne
    @JoinColumn(name = "RECRUITMENT_ID")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
