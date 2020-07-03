package com.ongdigit.restServices;

import com.ongdigit.dao.PlaceRepository;
import com.ongdigit.dao.UserRepository;
import com.ongdigit.entities.Place;
import com.ongdigit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/search/coordonnees")
    public List<Place> placesByCoordonnee(@RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("machine") String machine) {
        return placeRepository.findPlacesByDateReservationAndTimeReservationAndComputerNumber(date, time, machine);
    }



}
