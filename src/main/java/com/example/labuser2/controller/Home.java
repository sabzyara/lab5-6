package com.example.labuser2.controller;

import com.example.labuser2.entity.Category;
import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;
import com.example.labuser2.service.TaskService;
import com.example.labuser2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/posgr")
@AllArgsConstructor
public class Home {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/home")
    public String getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            Model model) {

        List<Category> categories = taskService.getAllCategories();
        model.addAttribute("categories", categories);

        Page<Task> taskPage;
        if ((search != null && !search.trim().isEmpty()) || status != null || category != null) {
            taskPage = taskService.searchTasks(search, status, category, PageRequest.of(page, size));
        } else {
            taskPage = taskService.getTasks(PageRequest.of(page, size));
        }


        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("currentPage", taskPage.getNumber());
        model.addAttribute("totalPages", taskPage.getTotalPages());
        model.addAttribute("search", search == null ? "" : search);
        model.addAttribute("status", status);
        model.addAttribute("category", category);

        User authenticatedUser = userService.getAuthenticatedUser();

        if ("ROLE_ADMIN".equals(authenticatedUser.getRole())) {
            return "home";
        } else {
            return "homeUser";
        }
    }

}




