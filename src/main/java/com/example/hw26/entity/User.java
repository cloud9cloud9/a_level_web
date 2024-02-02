package com.example.hw26.entity;

import com.example.hw26.annotation.Column;
import com.example.hw26.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;
}
