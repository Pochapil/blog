package ru.bagration.spring.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateThemeDto {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    private Integer ageCategory;


}
