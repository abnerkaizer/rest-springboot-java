package com.abnerkaizer.rest_springboot_java.repositories;

import com.abnerkaizer.rest_springboot_java.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<Person, Long> {

}
