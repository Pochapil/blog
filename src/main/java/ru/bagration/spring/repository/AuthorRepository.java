package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    boolean existsByPublicId(String id);

    Optional<Author> findByPublicId(String id);

    @Query("select t.firstName,t.lastName from Author t where t.id = :id")
    String findNameById(Long id);

    @Query("select t.publicId from Author t where t.id = :id")
    String findPublicIdById(Long id);

    @Query("select a.id from Author a where lower(a.firstName) like :name or lower(a.lastName) like :name")
    List<Long> findIdsByName(String name);

    boolean existsByFirstNameIgnoreCase(String firstName);

    boolean existsBySecondNameIgnoreCase(String secondName);

    boolean existsByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);

    @Query("select a.id from Author a where a.publicId=:publicId")
    Long getIdByPublicId(String publicId);
}


