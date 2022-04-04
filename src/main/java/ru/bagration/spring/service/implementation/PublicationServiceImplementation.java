package ru.bagration.spring.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bagration.spring.dto.PublicationDto;
import ru.bagration.spring.dto.PublicationDtoGet;
import ru.bagration.spring.entity.Publication;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.repository.AuthorRepository;
import ru.bagration.spring.repository.PublicationRepository;
import ru.bagration.spring.repository.ThemeRepository;
import ru.bagration.spring.service.PublicationService;
import ru.bagration.spring.utils.ResponseData;
import ru.bagration.spring.utils.Utility;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.bagration.spring.utils.ResponseData.*;

@Service
@RequiredArgsConstructor
public class PublicationServiceImplementation implements PublicationService {


    private final PublicationRepository publicationRepository;
    private final AuthorRepository authorRepository;
    private final ThemeRepository themeRepository;



    public ResponseEntity<?> addNewPublication (String authorId, String themeId, String title, String content){

        if(!authorRepository.existsByPublicId(authorId)){
            throw new BadRequestException("no such author", HttpStatus.BAD_REQUEST);
        }

        if(!themeRepository.existsByPublicId(themeId)){
            throw new BadRequestException("no such theme", HttpStatus.BAD_REQUEST);
        }

        var publication = new Publication();
        publication.setPublicId(Utility.generateUID());
        publication.setAuthorId(authorRepository.getIdByPublicId(authorId));
        publication.setThemeId(themeRepository.getIdByPublicId(themeId));
        publication.setTitle(title);
        publication.setContent(content);

        publicationRepository.save(publication);
        return response(Collections.singletonMap("message","success"));

    }


    public ResponseEntity<?> getAllPublications() {
        var publications = publicationRepository.findAll();
        var dtoS = publications.stream().map(this::mapToDto).collect(Collectors.toList());
        return response(dtoS);

    }




    @Override
    public ResponseEntity<?> createPublication(Long authorId, String title, Long themeId, String content) {
        var publication = new Publication();
        publication.setAuthorId(authorId);
        publication.setTitle(title);
        publication.setThemeId(themeId);
        publication.setContent(content);
        publication.setPublicId(UUID.randomUUID().toString());
        publicationRepository.save(publication);
        return response("publication was successfully created");
    }

    public ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable){
        var authorNameParam = "%" + authorName.trim().toLowerCase() + "%";
        var themeNameParam = "%" + themeName.trim().toLowerCase() + "%";

        var authorIds = authorRepository.findIdsByName(authorNameParam);
        var themeIds = themeRepository.getIdsByName(themeNameParam);

        var publicationPage = publicationRepository
                .findAll(getFilteringSpecification(authorIds, themeIds), pageable);

        return response(publicationPage);
    }


    private Specification<Publication> getFilteringSpecification(List<Long>  authorIds, List<Long> themeIds){
        return ((root, query, criteriaBuilder)->{
            /*
            select * from publications where author_id in (1,2,3,4,5) and theme_id in (1,2,3,4);
             */
            var predicates = new ArrayList<Predicate>();
            var authorPredicates = new ArrayList<Predicate>();
            var themePredicates = new ArrayList<Predicate>();

            for(var id: authorIds){
                authorPredicates.add(criteriaBuilder.equal(root.get("authorId"),id));
            }

            predicates.add(criteriaBuilder.or(authorPredicates.toArray(new Predicate[0])));

            for(var id: themeIds){
                themePredicates.add(criteriaBuilder.equal(root.get("themeId"),id));
            }

            predicates.add(criteriaBuilder.or(themePredicates.toArray(new Predicate[0])));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }



    @Override
    public ResponseEntity<?> getPublicationList(String searchString, String title, String content, Boolean detailed, Pageable pageable) {
        var publicationList = publicationRepository
                .findAll(getFilteringSpecification(searchString, title, content, detailed), pageable)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return response(publicationList);
    }


    @Override
    public ResponseEntity<?> getDetailsById(String id) {
        var publicationOptional = publicationRepository.findByPublicId(id);
        if (publicationOptional.isPresent()) {
            return response(mapToPublicationDtoGet(publicationOptional.get()));
        } else return responseBadRequest("no such publication");
    }

    private PublicationDto mapToDto(Publication publication) {
        var dto = new PublicationDto();
        dto.setId(publication.getPublicId());
        dto.setAuthorId(publication.getAuthorId());
        dto.setTitle(publication.getTitle());
        dto.setThemeId(publication.getThemeId());
        dto.setContent(publication.getContent());
        return dto;
    }

    private PublicationDtoGet mapToPublicationDtoGet(Publication publication) {
        var dto = new PublicationDtoGet();
        dto.setId(publication.getPublicId());
        dto.setAuthorId(authorRepository.findPublicIdById(publication.getAuthorId()));
        dto.setAuthorName(authorRepository.findNameById(publication.getAuthorId()));
        dto.setTitle(publication.getTitle());
        dto.setThemeId(themeRepository.findPublicIdById(publication.getThemeId()));
        dto.setThemeName(themeRepository.findNameById(publication.getThemeId()));
        dto.setContent(publication.getContent());
        return dto;
    }

    private Specification<Publication> getFilteringSpecification(String searchString, String title, String content, Boolean detailed) {

        return ((root, query, criteriaBuilder) -> {
            if (!detailed) {

                var queryString = "%" + searchString.toLowerCase() + "%";

                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), queryString);

                Predicate contentPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("content")), queryString);

                return criteriaBuilder.or(titlePredicate, contentPredicate);


            } else {
                var predicates = new ArrayList<Predicate>();

                if (title != null) {

                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                            "%" + title.toLowerCase() + "%"));
                }

                if (content != null) {

                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
                            "%" + content.toLowerCase() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

        });

    }

}
