package com.example.todayrecipe.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "adminid")
    private String adminid;

    @Column(name = "adminpassword")
    private String adminpassword;


}
