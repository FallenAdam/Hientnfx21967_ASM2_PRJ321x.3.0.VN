package com.example.Assignment02.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CATEGORY")
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = " NAME_CATEGORY")
    private String nameCategory;

    @Column(name = "NUMBER_CHOOSE")
    private int  numberChoose;
}
