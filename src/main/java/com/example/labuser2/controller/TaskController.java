package com.example.labuser2.controller;


import com.example.labuser2.entity.Category;
import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;
import com.example.labuser2.service.CategoryService;
import com.example.labuser2.service.EmailService;
import com.example.labuser2.service.TaskService;
import com.example.labuser2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/posgr/task")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final EmailService emailService;

    public TaskController(TaskService taskService, CategoryService categoryService, UserService userService, EmailService emailService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/tasks")
    public String getTasks(Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        List<Task> tasks = taskService.getTasksByUser(currentUser);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categories);
        model.addAttribute("users", List.of(currentUser));
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute("task") Task task) {
        User assignedUser = userService.getUserById(task.getUser().getUserId());
        if (assignedUser == null || assignedUser.getEmail() == null) {
            System.err.println("Error");
            return "redirect:/posgr/task/tasks?error=user_not_found";
        }

        task.setUser(assignedUser);
        taskService.addTask(task);

        String email = assignedUser.getEmail();
        String subject = "New Task";
        String message = String.format(
                "Hello, %s!\n" +
                        "\n" +
                        "You have been assigned a new task: %s.\n" +
                        "Description: %s\n" +
                        "Deadline: %s",
                assignedUser.getUsername(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate()
        );

        try {
            emailService.sendNotif(email, subject, message);
        } catch (Exception e) {
            System.err.println("Failed to send the notification: " + e.getMessage());
        }

        return "redirect:/posgr/task/tasks";
    }


    @PostMapping("/updateTask")
    public String updateTask(@RequestParam long taskId, @ModelAttribute Task task) {
        taskService.updateTask(taskId, task);
        return "redirect:/posgr/task/tasks";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }
}


