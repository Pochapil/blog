package ru.bagration.spring.dto;


import lombok.Data;

@Data
public class PublicationDto {

    private String id;
    private Long authorId;
    private String title;
    private Long themeId;
    private String content;

}
