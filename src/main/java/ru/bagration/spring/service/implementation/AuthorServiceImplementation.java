package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.AuthorDto;
import ru.bagration.spring.dto.AuthorListDto;
import ru.bagration.spring.entity.Author;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.AuthorService;
import ru.bagration.spring.service.PublicationService;
import ru.bagration.spring.utils.ResponseData;
import ru.bagration.spring.utils.Utility;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.*;


@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PublicationRepository publicationRepository;
    private final ThemeRepository themeRepository;


    @Override
    public ResponseEntity<?> addNewAuthor(String firstName, String secondName) {

        if (authorRepository.existsByFirstNameIgnoreCase(firstName) && authorRepository.existsBySecondNameIgnoreCase(secondName))
            throw new BadRequestException("such author already exists", HttpStatus.CONFLICT);
        var author = new Author();

        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setPublicId(Utility.generateUID());
        authorRepository.save(author);
        return response("author was successfully created");
    }

    public ResponseEntity<?> getAllAuthors() {
        var authors = authorRepository.findAll();
        var dtoS = authors.stream().map(this::mapToDto).collect(Collectors.toList());
        return response(dtoS);
    }

    //todo delete !!
    @Override
    public ResponseEntity<?> createAuthor(String firstName, String secondName) {
        var author = new Author();

        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setPublicId(UUID.randomUUID().toString());
        authorRepository.save(author);
        return response("author was successfully created");
    }

    @Override
    public ResponseEntity<?> getAuthorList(String name, String firstName, String secondName, Boolean detailed, Pageable pageable) {
        var authorList = authorRepository
                .findAll(getFilteringSpecification(name, firstName, secondName, detailed), pageable)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return response(authorList);
    }

    @Override
    public ResponseEntity<?> getDetailsById(String id) {

        var authorOptional = authorRepository.findByPublicId(id);

        if (authorOptional.isPresent()){
            return response(mapToAuthorDto(authorOptional.get()));
        }else return responseBadRequest("no such author");

    }

    private AuthorListDto mapToDto(Author author) {
        var dto = new AuthorListDto();
        dto.setId(author.getPublicId());
        dto.setFirstName(author.getFirstName());
        dto.setSecondName(author.getSecondName());
        return dto;
    }


    private AuthorDto mapToAuthorDto(Author author){
        var dto = new AuthorDto();
        dto.setId(author.getPublicId());
        dto.setFirstName(author.getFirstName());
        dto.setSecondName(author.getSecondName());
        dto.setNumberOfPublications(
                publicationRepository
                        .countAllByAuthorId(
                                author.getId()));

        var perTheme  = new HashMap<String, Long>();

        System.out.println(publicationRepository.findUniqueThemeIdsByAuthorId(author.getId()));

        var themeIds = publicationRepository.findUniqueThemeIdsByAuthorId(author.getId());

        for(var themeId: themeIds){

            var themeName = themeRepository.findNameById(themeId);

            var count = publicationRepository.countAllByAuthorIdAndThemeId(author.getId(),themeId);
            perTheme.put(themeName,count);

        }
        dto.setPerTheme(perTheme);
        return dto;

    }



    private Specification<Author> getFilteringSpecification(String name, String firstName, String secondName, Boolean detailed) {
        return ((root, query, criteriaBuilder) -> {
            if (!detailed) {

                var queryString = "%" + name.toLowerCase() + "%";

                Predicate firstNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstName")), queryString);


                Predicate secondNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("secondName")), queryString);

                return criteriaBuilder.or(firstNamePredicate, secondNamePredicate);

            } else {
                var predicates = new ArrayList<Predicate>();

                if (firstName != null) {

                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                            "%" + firstName.toLowerCase() + "%"));
                }

                if (secondName != null) {

                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("secondName")),
                            "%" + secondName.toLowerCase() + "%"));

                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            }
        });
    }

}
