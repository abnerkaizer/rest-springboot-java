package com.abnerkaizer.rest_springboot_java.file.exporter.contract;

import com.abnerkaizer.rest_springboot_java.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> people) throws Exception;
    Resource exportPerson(PersonDTO people) throws Exception;
}
