package ru.bagration.spring.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ThemeDtoCreate {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Max(18)
    @Min(0)
    private Integer ageCategory;

}
