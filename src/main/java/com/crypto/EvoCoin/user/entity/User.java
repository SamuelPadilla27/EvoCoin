package com.crypto.EvoCoin.user.entity;

import com.crypto.EvoCoin.common.enums.Role;
import com.crypto.EvoCoin.user.model.UserRegistration;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trn_user")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    public User(UserRegistration pUser){
        this.email =pUser.email();
        this.username = pUser.username();
        this.password = pUser.password();
        this.role = pUser.role();
    }
}
