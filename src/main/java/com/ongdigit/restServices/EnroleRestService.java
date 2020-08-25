package com.ongdigit.restServices;

import com.ongdigit.dao.EnroleRepository;
import com.ongdigit.entities.Enrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/listEnroles")
public class EnroleRestService {

    @Autowired
    private EnroleRepository enroleRepository;

    @GetMapping(value = "")
    public List<Enrole> enroleList() {
        return enroleRepository.findAll();
    }
}
