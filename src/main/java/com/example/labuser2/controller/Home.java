package com.example.labuser2.controller;


import com.example.labuser2.entity.Task;
import com.example.labuser2.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posgr")
@AllArgsConstructor
public class Home {

TaskService taskService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("task", tasks);
        return "home";
    }
}

