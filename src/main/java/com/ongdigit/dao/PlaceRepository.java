package com.ongdigit.dao;

import com.ongdigit.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @RestResource(path = "/byEmail")
    List<Place> findPlacesByUserEmailAndAccessOrderByDateReservationDescTimeReservationDesc(String userEmail, boolean access);

    @RestResource(path = "/byEmail")
    List<Place> findPlacesByDateReservationAndTimeReservationAndComputerNumber(String dateReservation, String timeReservation, String computerNumber);
}
