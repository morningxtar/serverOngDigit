package com.ongdigit.dao;

import com.ongdigit.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findPlacesByUserEmailAndAccessOrderByDateReservationDescTimeReservationDesc(String userEmail, boolean access);

    List<Place> findPlacesByDateReservationAndTimeReservationAndComputerNumber(String dateReservation, String timeReservation, String computerNumber);

    @RestResource(path = "/byPresence")
    List<Place> findPlacesByPresence(boolean presence);

    @RestResource(path = "/byPresenceAccess")
    List<Place> findPlacesByPresenceAndAccess(boolean presence, boolean access);

    @RestResource(path = "/byPresenceAccessDate")
    List<Place> findPlacesByPresenceAndAccessAndDateReservation(boolean presence, boolean access, String dateReservation);

    @RestResource(path = "/nbHourMonth")
    int countPlacesByDateReservationContainsAndAccessAndPresence(String dateReservation, boolean access, boolean presence);

    @RestResource(path = "/nbHourMonthUser")
    int countPlacesByAccessAndPresenceAndUserEmail(boolean access, boolean presence, String userEmail);

    @RestResource(path = "/nbHourMont")
    int countPlacesByDateReservationContains(String dateReservation);
}
