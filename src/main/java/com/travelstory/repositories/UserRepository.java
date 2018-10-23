package com.travelstory.repositories;

import com.travelstory.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    User findUserById(Long id);

    List<User> getAllBy();

    Page<User> findByFirstNameIsStartingWithOrLastNameIsStartingWith(String firstName, String lastName,
            Pageable pageable);

    Page<User> findByLastNameIsStartingWith(String lastName, Pageable pageable);

    Page<User> findByFirstNameIsStartingWithAndLastNameIsStartingWith(String firstName, String lastName,
            Pageable pageable);

    Page<User> findAllByFollowersId(Long userId, Pageable pageable);

    Page<User> findAllByFollowingId(Long userId, Pageable pageable);

}
