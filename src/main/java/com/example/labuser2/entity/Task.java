package com.example.labuser2.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_status")
    private String status;


    @Column(name = "task_due_date")
    @FutureOrPresent(message = "Date is invalid")
    private LocalDate dueDate;

    @Column(name = "priority")
    private String priority;


    public Task(User user, Category category, String title, String description, String status ) {
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public Long getCategoryId() {
        return category.getCategoryId();
    }
}
