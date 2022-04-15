package ru.bagration.spring.dto.theme;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ThemeDto {
    @NotNull(message = "theme.name.require.not.null")
    @NotEmpty(message = "theme.name.require.not.empty")
    @NotBlank(message = "theme.name.require.not.blank")
    private String name;

    @Min(0)
    @Max(18)
    @NotNull(message = "theme.ageCategory.require.not.null")
    private Integer ageCategory;
}