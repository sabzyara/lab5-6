package com.example.labuser2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    public User(String username, String password) {}
}
