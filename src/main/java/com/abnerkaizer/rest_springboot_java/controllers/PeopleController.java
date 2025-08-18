package com.abnerkaizer.rest_springboot_java.controllers;

import com.abnerkaizer.rest_springboot_java.services.PeopleServices;
import com.abnerkaizer.rest_springboot_java.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PeopleController {

    @Autowired
    private PeopleServices service;
    //private PeopleServices service = new PeopleServices();
    //http://localhost:8080/person
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll(){
        return service.findAll();
    }
    //http://localhost:8080/person/1
    @GetMapping(value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(@PathVariable("id") Long id){
        return service.findById(id);
    }
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create(@RequestBody Person person){
        return service.create(person);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@RequestBody Person person){
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
