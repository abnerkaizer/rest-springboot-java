package com.abnerkaizer.rest_springboot_java.services;

import com.abnerkaizer.rest_springboot_java.data.dto.PersonDTO;
import com.abnerkaizer.rest_springboot_java.exception.ResourceNotFoundException;
import com.abnerkaizer.rest_springboot_java.model.Person;
import com.abnerkaizer.rest_springboot_java.repositories.PeopleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.abnerkaizer.rest_springboot_java.mapper.ObjectMapper.parseListObjects;
import static com.abnerkaizer.rest_springboot_java.mapper.ObjectMapper.parseObject;

@Service
public class PeopleServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PeopleServices.class.getName());

    @Autowired
    PeopleRepository repository;


    public List<PersonDTO> findAll() {

        logger.info("Finding all People!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {

        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}