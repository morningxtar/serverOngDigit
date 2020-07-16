package com.ongdigit.restServices;

import com.ongdigit.dao.PlaceRepository;
import com.ongdigit.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/listPlaces")
public class PlaceRestService {

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping(value = "")
    public List<Place> placeList() {
        return placeRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Place place(@PathVariable(name = "id") Long id) {
        return placeRepository.findById(id).get();
    }

    @PutMapping(value = "/{id}")
    public Place update(@PathVariable(name = "id") Long id, @RequestBody Place place) {
        place.setId(id);
        return placeRepository.save(place);
    }

    @PostMapping(value = "")
    public Place saveUser(@RequestBody Place place) {
        return placeRepository.save(place);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        placeRepository.deleteById(id);
    }

    @GetMapping(value = "/search/byUser")
    public List<Place> placesByEmail(@RequestParam("email") String email) {
        return placeRepository.findPlacesByUserEmailAndAccessOrderByDateReservationDescTimeReservationDesc(email, true);
    }

    @GetMapping(value = "/search/byAccessUser")
    public List<Place> placesByEmailAccess(@RequestParam("email") String email) {
        return placeRepository.findPlacesByAccessAndUserEmailAndPresence(true, email, false);
    }

    @GetMapping(value = "/search/byNoAccessUser")
    public List<Place> placesByEmailNonCompleted(@RequestParam("email") String email) {
        return placeRepository.findPlacesByAccessAndUserEmailAndPresence(false, email, false);
    }

    @GetMapping(value = "/search/completedUser")
    public List<Place> placesByEmailCompleted(@RequestParam("email") String email) {
        return placeRepository.findPlacesByAccessAndUserEmailAndPresence(false, email, true);
    }

    @GetMapping(value = "/search/coordonnees")
    public String placesByCoordonnee(@RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("machine") String machine) {

        int timeDeb = Integer.parseInt(time.split(",")[0]);
        int timeFin = Integer.parseInt(time.split(",")[1]);
        List<Place> place;
        List<Place> place1;
        List<Place> place2;
        List<Place> place3;
        List<Place> place4;
        boolean check = false;
        place = placeRepository.findPlacesByDateReservationAndTimeReservationContainsAndComputerNumber(date, (timeDeb + ","), machine);
        place1 = placeRepository.findPlacesByDateReservationAndTimeReservationContainsAndComputerNumber(date, ((timeDeb - 1) + ","), machine);
        if (place1.size() != 0)
            check = (timeFin == Integer.parseInt(place1.get(0).getTimeReservation().split(",")[1]));
        place3 = placeRepository.findPlacesByDateReservationAndTimeReservationContainsAndComputerNumber(date, ((timeFin) + ","), machine);
        place2 = placeRepository.findPlacesByDateReservationAndTimeReservationContainsAndComputerNumber(date, (",") + (timeFin - 1), machine);
        place4 = placeRepository.findPlacesByDateReservationAndTimeReservationContainsAndComputerNumber(date, ("," + (timeDeb)), machine);
        if (place.size() == 0) {
            if ((place1.size() != 0 && check)) {
                System.out.println("check" + place1);
                System.out.println(place2);
                return "Quelqu'un a déjà réservé de " + place1.get(0).getTimeReservation().split(",")[0] + "h à " + place1.get(0).getTimeReservation().split(",")[1] + "h";
            } else if ((place1.size() != 0 && place2.size() == 0) || (place1.size() == 0 && place3.size() == 0) || (place4.size() != 0)) {
                System.out.println("check" + timeFin);
                System.out.println(place2);
                return "";
            }
                 else {
                    return "Quelqu'un a déjà réservé de " + place2.get(0).getTimeReservation().split(",")[0] + "h à " + place2.get(0).getTimeReservation().split(",")[1] + "h";
                }
            }
         else {
            return "Quelqu'un a déjà réservé de " + place.get(0).getTimeReservation().split(",")[0] + "h à " + place.get(0).getTimeReservation().split(",")[1] + "h";
        }
    }

    @GetMapping(value = "/search/nbHourMonth")
    public int nbHourMonth(@RequestParam("dateReservation") String dateReservation, @RequestParam("presence") boolean presence) {
        AtomicInteger nombre = new AtomicInteger();
        List<Place> places = placeRepository.findPlacesByDateReservationContainsAndAccessAndPresence(dateReservation, false, presence);
        places.forEach(place -> {
            nombre.set(nombre.get() + Integer.parseInt(place.getTimeReservation().split(",")[1]) - Integer.parseInt(place.getTimeReservation().split(",")[0]));
        });
        return nombre.get();
    }

    @GetMapping(value = "/search/nbHourUser")
    public int nbHourUser(@RequestParam("userEmail") String userEmail, @RequestParam("presence") boolean presence) {
        AtomicInteger nombre = new AtomicInteger();
        List<Place> places = placeRepository.findPlacesByAccessAndPresenceAndUserEmail(false, presence, userEmail);
        places.forEach(place -> {
            nombre.set(nombre.get() + Integer.parseInt(place.getTimeReservation().split(",")[1]) - Integer.parseInt(place.getTimeReservation().split(",")[0]));
        });
        return nombre.get();
    }

    @GetMapping(value = "/search/nbHourUserMonth")
    public int nbHourUserByMonth(@RequestParam("userEmail") String userEmail, @RequestParam("presence") boolean presence, @RequestParam("dateReservation") String dateReservation) {
        AtomicInteger nombre = new AtomicInteger();
        List<Place> places = placeRepository.findPlacesByAccessAndPresenceAndUserEmailAndDateReservationContains(false, presence, userEmail, dateReservation);
        places.forEach(place -> {
            nombre.set(nombre.get() + Integer.parseInt(place.getTimeReservation().split(",")[1]) - Integer.parseInt(place.getTimeReservation().split(",")[0]));
        });
        return nombre.get();
    }
}
