package com.ongdigit.restServices;

import com.ongdigit.dao.ServiceRepository;
import com.ongdigit.entities.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/listServices")
public class ServiceRestService {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping(value = "")
    public List<Service> serviceList() {
        return serviceRepository.findAll();
    }
}
