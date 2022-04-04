package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.ThemeDto;
import ru.bagration.spring.dto.ThemeDtoGet;
import ru.bagration.spring.entity.Theme;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.ThemeService;
import ru.bagration.spring.utils.ResponseData;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.*;

@Service
@RequiredArgsConstructor
public class ThemeServiceImplementation implements ThemeService {


    private final AuthorRepository authorRepository;
    private final PublicationRepository publicationRepository;
    private final ThemeRepository themeRepository;


    @Override
    public ResponseEntity<?> addNewTheme(String name, Integer ageCategory) {

        if (themeRepository.existsByNameIgnoreCase(name))
            throw new BadRequestException("such name already exists", HttpStatus.CONFLICT);

        createTheme(name , ageCategory);
        return ResponseEntity.ok(Collections.singletonMap("anyway", "hello"));

    }


    @Override
    public ResponseEntity<?> getAllThemes() {
        var themes = themeRepository.findAll();
        var dtoS = themes.stream().map(this::mapToDto).collect(Collectors.toList());

        return response(dtoS);
    }

    @Override
    public ResponseEntity<?> createTheme(String name, Integer ageCategory) {

        var theme = new Theme();
        theme.setName(name);
        theme.setAgeCategory(ageCategory);
        theme.setPublicId(UUID.randomUUID().toString());
        themeRepository.save(theme);
        return response("theme was successfully created");
    }

    @Override
    public ResponseEntity<?> getThemeList(String name, Pageable pageable) {
        var themeList = themeRepository
                //.findAll(pageable)
                .findAll(getFilteredSpecification(name), pageable)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return response(themeList);
    }


    @Override
    public ResponseEntity<?> getDetailsById(String id) {
        var themeOptional = themeRepository.findByPublicId(id);
        if (themeOptional.isPresent()) {
            return response(mapToThemeDtoGet(themeOptional.get()));
        } else return responseBadRequest("no such theme");
    }


    private ThemeDto mapToDto(Theme theme) {
        var dto = new ThemeDto();
        dto.setId(theme.getPublicId());
        dto.setName(theme.getName());
        dto.setAgeCategory(theme.getAgeCategory());
        return dto;
    }


    private ThemeDtoGet mapToThemeDtoGet(Theme theme) {
        var dto = new ThemeDtoGet();
        dto.setId(theme.getPublicId());
        dto.setName(theme.getName());
        dto.setAgeCategory(theme.getAgeCategory());
        dto.setNumberOfPublications(
                publicationRepository
                        .countAllByThemeId(
                                theme.getId()));

        var perAuthor = new HashMap<String, Long>();

        //System.out.println(publicationRepository.findUniqueAuthorIdsByThemeId(theme.getId()));

        var authorIds = publicationRepository.findUniqueAuthorIdsByThemeId(theme.getId());

        for (var authorId : authorIds) {

            var authorName = authorRepository.findNameById(authorId);

            var count = publicationRepository.countAllByAuthorIdAndThemeId(authorId, theme.getId());
            perAuthor.put(authorName, count);
        }
        dto.setPerAuthor(perAuthor);
        return dto;
    }

    private Specification<Theme> getFilteredSpecification(String searchString) {
        return (((root, query, criteriaBuilder) -> {
            var queryString = "%" + searchString.toLowerCase() + "%";

            Predicate namePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), queryString);

            return criteriaBuilder.or(namePredicate, namePredicate);
        }));
    }

}
