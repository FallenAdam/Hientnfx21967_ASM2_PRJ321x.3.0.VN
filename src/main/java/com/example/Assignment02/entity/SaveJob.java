package com.example.Assignment02.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SAVE_JOB")
@Data
public class SaveJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "RECRUITMENT_ID")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
