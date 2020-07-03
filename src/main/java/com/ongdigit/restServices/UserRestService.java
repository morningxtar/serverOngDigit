package com.ongdigit.restServices;

import com.ongdigit.dao.UserRepository;
import com.ongdigit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/listUsers")
public class UserRestService {

    @Autowired
    private UserRepository userRepository;

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
}
