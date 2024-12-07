package com.example.JWT;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "members")
public class MemberPo {
    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "authority",nullable = false)
    private String authority;
    @Column(name = "email",nullable = false)
    private String email;

    // Getter 和 Setter 方法
}