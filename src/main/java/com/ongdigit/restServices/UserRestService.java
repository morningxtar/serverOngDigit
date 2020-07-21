package com.ongdigit.restServices;

import com.ongdigit.dao.UserRepository;
import com.ongdigit.entities.Place;
import com.ongdigit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/listUsers")
public class UserRestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRestService placeRestService;

    @GetMapping(value = "")
    public List<User> userList() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public User user(@PathVariable(name = "id") Long id) {
        return userRepository.findById(id).get();
    }

    @PutMapping(value = "/{id}")
    public User update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @PostMapping(value = "")
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping(value = "/search/byUserConnexion")
    public List<User> users(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @GetMapping(value = "/search/byUserConnexiont")
    public List<User> userst(@RequestParam(required = false) String email, @RequestParam(required = false) String password, @RequestParam(required = false)  String number, @RequestParam(required = false) String password2) {
        return userRepository.findUserByEmailAndPasswordOrPhoneNumberAndPassword(email, password, number, password2);
    }

    @GetMapping(value = "/search/byUser")
    public List<User> usersByEmail(@RequestParam("email") String email) {
        return userRepository.findUserByEmail(email);
    }

    @GetMapping(value = "/schedule")
    public void fixedDelaySch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Fixed Delay scheduler:: " + strDate);
        List<Place> placeList = placeRestService.placeList();
        //System.out.println("placeid" + placeList);
        placeRestService.placeList().forEach(
                place -> {
                    try {
                        changeAccess(place);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void changeAccess(Place place) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date datePlace = sdf.parse(place.getDateReservation());
        int hourPlace = Integer.parseInt(place.getTimeReservation().split(",")[1]);
        Date now = new Date();
//        System.out.println("ici " + place.getId());
//        System.out.println("ici " + place.getDateReservation());
//        System.out.println("ici " + compareDate(datePlace, now));
        switch (compareDate(datePlace, now)){
            case 1:
                place.setAccess(false);
                placeRestService.update(place.getId(), place);
                break;
            case 0:
                if (hourPlace < now.getHours()) {
//                    System.out.println("ici0 " + place.getId());
//                    System.out.println("ici0 " + place.getComputerNumber());
                    place.setAccess(false);
                    System.out.println(placeRestService.update(place.getId(), place));
                }
                break;
        }
    }

    int compareDate(Date datePlace, Date now){

        int check = 2;

        if (datePlace.getDate() == now.getDate() && datePlace.getMonth() == now.getMonth() && datePlace.getYear() == now.getYear()){
            check = 0;
        }
        else if ((datePlace.getDate() < now.getDate() && datePlace.getMonth() == now.getMonth() && datePlace.getYear() == now.getYear()) || (datePlace.getMonth() < now.getMonth() && datePlace.getYear() == now.getYear()) || datePlace.getYear() < now.getYear()){
            check = 1;
        }

        return check;
    }
}
