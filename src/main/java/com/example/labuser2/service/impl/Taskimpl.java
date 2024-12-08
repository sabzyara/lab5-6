package com.example.labuser2.service.impl;

import com.example.labuser2.entity.Category;
import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;
import com.example.labuser2.repository.TaskRepository;
import com.example.labuser2.repository.UserRepository;
import com.example.labuser2.repository.CategoryRepository;
import com.example.labuser2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Taskimpl implements TaskService {

    @Autowired
    private  TaskRepository taskRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  CategoryRepository categoryRepository;


    @Override
    public Task addTask(Task task) {
        User user = userRepository.findById(task.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(task.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }


    @Override
    public Task getTaskById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setDueDate(task.getDueDate());
        Task updatedTask = taskRepository.save(existingTask);
        return updatedTask;
    }



    @Override
    public void deleteTask(long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Page<Task> searchTasks(String search, String status, String category, Pageable pageable) {
        if ((search == null || search.isEmpty()) && (status == null || status.isEmpty()) && (category == null || category.isEmpty())) {
            return taskRepository.findAll(pageable);
        }

        if (status != null && !status.isEmpty() && (category == null || category.isEmpty())) {
            return taskRepository.findByStatus(status, pageable);
        }

        if ((status == null || status.isEmpty()) && category != null && !category.isEmpty()) {
            return taskRepository.findByCategory_Name(category, pageable);
        }

        if (status != null && !status.isEmpty() && category != null && !category.isEmpty()) {
            return taskRepository.findByStatusAndCategory_Name(status, category, pageable);
        }

        return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


}

