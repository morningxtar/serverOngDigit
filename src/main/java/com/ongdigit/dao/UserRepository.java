package com.ongdigit.dao;

import com.ongdigit.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "/byUserConnexion")
    List<User> findUserByEmailAndPassword(@Param("email")String email, @Param("password")String password);

    @RestResource(path = "/byUserConnexionAdmin")
    List<User> findUserByEmailAndPasswordAndUserType(String email, String password, String userType);

    @RestResource(path = "/byUserConnexiont")
    List<User> findUserByEmailAndPasswordOrPhoneNumberAndPassword(String email, String password, String phoneNumber, String password2);


    @RestResource(path = "/byUser")
    List<User> findUserByEmail(@Param("email")String email);

    @RestResource(path = "/byUserType")
    List<User> findUserByUserType(@Param("userType")String userType);

    @RestResource(path = "/byCordPage")
    Page<User> findByFirstNameContainsOrLastNameContainsOrPhoneNumberContainsOrEmailContains(@Param("cord") String firstName, @Param("cord") String lastName, @Param("cord") String phoneNumber, @Param("cord") String email, Pageable pageable);


}
