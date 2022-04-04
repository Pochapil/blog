package ru.bagration.spring.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ThemeDtoGet {

    private String id;
    private String name;
    private Integer ageCategory;

    private Long numberOfPublications;
    private Map<String,Long> perAuthor;

}
