package com.ongdigit.restServices;

import com.ongdigit.dao.MachineRepository;
import com.ongdigit.dao.ServiceRepository;
import com.ongdigit.entities.Machine;
import com.ongdigit.entities.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/listMachines")
public class MachineRestService {

    @Autowired
    private MachineRepository machineRepository;

    @GetMapping(value = "")
    public List<Machine> machineList() {
        return machineRepository.findAll();
    }
}
