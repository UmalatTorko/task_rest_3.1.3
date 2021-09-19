package ru.gordanov.task_rest_313.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gordanov.task_rest_313.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u join fetch u.roles WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);
}
