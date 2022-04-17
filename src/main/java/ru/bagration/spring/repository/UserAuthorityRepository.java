package ru.bagration.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.UserAuthority;

import java.util.List;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long>{

    List<UserAuthority> findAllByUserId(Long userId);

}
