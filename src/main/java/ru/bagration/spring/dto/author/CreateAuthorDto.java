package ru.bagration.spring.dto.author;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAuthorDto {
    @NotNull(message = "author.firstName.require.not.null")
    @NotEmpty(message = "author.firstName.require.not.empty")
    @NotBlank(message = "author.firstName.require.not.blank")
    private String firstName;
    @NotNull(message = "author.lastName.require.not.null")
    @NotEmpty(message = "author.lastName.require.not.empty")
    @NotBlank(message = "author.lastName.require.not.blank")
    private String lastName;
}