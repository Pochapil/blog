package ru.bagration.spring.dto.publication;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NewPublicationDto {
    @NotNull(message = "publication.authorId.require.not.null")
    @NotEmpty(message = "publication.authorId.require.not.empty")
    @NotBlank(message = "publication.authorId.require.not.blank")
    private String authorId;
    @NotNull(message = "publication.themeId.require.not.null")
    @NotEmpty(message = "publication.themeId.require.not.empty")
    @NotBlank(message = "publication.themeId.require.not.blank")
    private String themeId;
    @NotNull(message = "publication.title.require.not.null")
    @NotEmpty(message = "publication.title.require.not.empty")
    @NotBlank(message = "publication.title.require.not.blank")
    private String title;
    @NotNull(message = "publication.content.require.not.null")
    @NotEmpty(message = "publication.content.require.not.empty")
    @NotBlank(message = "publication.content.require.not.blank")
    private String content;
}
