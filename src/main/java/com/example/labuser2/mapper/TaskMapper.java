package com.example.labuser2.mapper;

import com.example.labuser2.dto.TaskDto;
import com.example.labuser2.entity.Category;
import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;

//public class TaskMapper {
//
//    public static TaskDto toDto(Task task) {
//        return new TaskDto(
//                task.getTaskId(),
//                task.getUser().getUserId(),
//                task.getCategory().getCategoryId(),
//                task.getTitle(),
//                task.getDescription(),
//                task.getStatus(),
//                task.getDueDate()
//        );
//    }

//    public static Task toEntity(TaskDto taskDto, User user, Category category) {
//        return new Task(
//                taskDto.getId(),
//                user,
//                category,
//                taskDto.getTitle(),
//                taskDto.getDescription(),
//                taskDto.getStatus(),
//                taskDto.getDueDate()
//        );
//    }

