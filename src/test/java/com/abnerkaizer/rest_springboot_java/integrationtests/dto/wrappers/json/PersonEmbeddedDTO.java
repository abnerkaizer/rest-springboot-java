package com.abnerkaizer.rest_springboot_java.integrationtests.dto.wrappers.json;


import com.abnerkaizer.rest_springboot_java.integrationtests.dto.PersonDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<PersonDTO> people;

    public PersonEmbeddedDTO() {}

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
}
