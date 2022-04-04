package ru.bagration.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Publication;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>, JpaSpecificationExecutor<Publication> {

    Optional<Publication> findByPublicId(String id);

    long countAllByAuthorId(Long authorId);

    long countAllByThemeId(Long themeId);

    @Query("select distinct p.themeId from Publication p where p.authorId=:authorId")
    List<Long> findUniqueThemeIdsByAuthorId(long authorId);

    @Query("select distinct p.authorId from Publication p where p.themeId=:themeId")
    List<Long> findUniqueAuthorIdsByThemeId(long themeId);

    long countAllByAuthorIdAndThemeId(long authorId, long themeId);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByContentIgnoreCase(String content);

    @Query("select p.id from Publication p where p.publicId=:publicId")
    Long getIdByPublicId(String publicId);

}
