package ru.bagration.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Theme;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>, JpaSpecificationExecutor<Theme> {

    @Query("select t.name from Theme t where t.id = :id")
    String findNameById(Long id);

    @Query("select t.publicId from Theme t where t.id = :id")
    String findPublicIdById(Long id);

    Optional<Theme> findByPublicId(String id);

    @Query("select p.id from Theme p where p.ageCategory <= :ageCategory")
    List<Long> findAllowedThemeIdsByAgeCategory(Integer ageCategory);

    @Query ("select  t.id from Theme t where lower(t.name) like :name")
    List<Long> getIdsByName(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByPublicId(String publicId);

    @Query("select t.id from Theme t where t.publicId=:publicId")
    Long getIdByPublicId(String publicId);

}
