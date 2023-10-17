package com.roy.user.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "micro_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long userId;
    private String name;
    private String email;
    private String about;
    private String phoneNumber;
    private String dob;

    @Transient
    private List<Rating> ratings = new ArrayList<>();
}
