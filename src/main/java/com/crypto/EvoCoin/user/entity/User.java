package com.crypto.EvoCoin.user.entity;

import com.crypto.EvoCoin.common.enums.Role;
import com.crypto.EvoCoin.user.model.UserRegistration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(UserRegistration pUser, String pHashedPassword){
        this.email =pUser.email();
        this.username = pUser.username();
        this.password = pHashedPassword;
        this.role = pUser.role();
        this.createdAt = LocalDateTime.now();
    }
}
