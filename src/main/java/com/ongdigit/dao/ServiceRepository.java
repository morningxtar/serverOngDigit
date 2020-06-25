package com.ongdigit.dao;

import com.ongdigit.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface ServiceRepository extends JpaRepository<Service, Long> {
}

