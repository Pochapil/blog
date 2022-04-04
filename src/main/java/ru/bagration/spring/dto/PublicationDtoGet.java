package ru.bagration.spring.dto;

import lombok.Data;

@Data
public class PublicationDtoGet {

    private String id;
    private String authorId;
    private String authorName;
    private String title;
    private String themeId;
    private String themeName;
    private String content;

}
