package com.example.labuser2.service;


import com.example.labuser2.entity.Category;
import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    Task addTask(Task task);
    Task getTaskById(long id);
    List<Task> getAllTasks();
    Task updateTask(long id, Task task);
    void deleteTask(long id);
    List<Task> getTasksByUser(User user);
    Page<Task> getTasks(Pageable pageable);
    Page<Task> searchTasks(String search, String status, String category, Pageable pageable);
    List<Category> getAllCategories();
}
