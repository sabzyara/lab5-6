package com.example.labuser2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private long id;
    private long userId;
    private long categoryId;
    private String title;
    private String description;
    private String status;
    private Date dueDate;
}

