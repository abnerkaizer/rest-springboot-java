package com.abnerkaizer.rest_springboot_java;

import com.abnerkaizer.rest_springboot_java.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id){
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Abner");
        person.setLastName("Kaizer");
        person.setAddress("Ipatinga - Minas Gerais - Brasil");
        person.setGender("Male");

        return person;
    }
}
