package ru.bagration.spring.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.bagration.spring.utils.ResponseData;


public interface PublicationService {

    ResponseEntity<?> getAllPublications();

    ResponseEntity<?> createPublication(Long authorId, String title, Long themeId, String content);

    ResponseEntity<?> getPublicationList(String searchString, String title, String context, Boolean detailed, Pageable pageable);

    ResponseEntity<?> getDetailsById(String id);

    ResponseEntity<?> getData(String authorName, String themeName, Pageable pageable);

    ResponseEntity<?> addNewPublication(String authorId, String themeId, String title, String content);
}
