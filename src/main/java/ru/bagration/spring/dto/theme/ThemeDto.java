package ru.bagration.spring.dto.theme;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ThemeDto {
    @NotNull(message = "theme.name.require.not.null")
    @NotEmpty(message = "theme.name.require.not.empty")
    @NotBlank(message = "theme.name.require.not.blank")
    private String name;

    @Min(value = 0, message = "theme.ageCategory.require.value.bigger")
    @Max(value = 18, message = "theme.ageCategory.require.value.less")
    @NotNull(message = "theme.ageCategory.require.not.null")
    private Integer ageCategory;
}