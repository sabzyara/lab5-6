package com.example.labuser2.repository;

import com.example.labuser2.entity.Task;
import com.example.labuser2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select task from Task task order by task.dueDate asc ")
    List<Task> findAll();
    List<Task> findByUser(User user);
    Page<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);
    Page<Task> findByStatusAndCategory_Name(String status,String category, Pageable pageable);
    Page<Task> findByStatus(String status, Pageable pageable);
    Page<Task> findByCategory_Name(String categoryName, Pageable pageable);

}
