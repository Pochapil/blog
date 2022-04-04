package ru.bagration.spring.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePublicationDto {

    @NotNull
    private Long authorId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    @NotNull
    private Long themeId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String content;
}
