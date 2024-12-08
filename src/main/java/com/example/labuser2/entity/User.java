package com.example.labuser2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username" , nullable = false)
    private String username;

    @NotNull
    @Column(name = "password" , nullable = false)
    private String password;

    @NotNull
    @Column(name = "email" , nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at" , nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER";


    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiration")
    private Instant tokenExpiration;

}
