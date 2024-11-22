package com.example.labuser2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private long categoryId;
    private String name;
    private String description;

    public CategoryDto(long categoryId, String name, String description) {
    }
}
