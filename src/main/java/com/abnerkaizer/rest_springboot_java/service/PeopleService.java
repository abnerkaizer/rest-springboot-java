package com.abnerkaizer.rest_springboot_java.service;

import com.abnerkaizer.rest_springboot_java.controllers.PeopleController;
import com.abnerkaizer.rest_springboot_java.data.dto.PersonDTO;
import com.abnerkaizer.rest_springboot_java.exception.BadRequestException;
import com.abnerkaizer.rest_springboot_java.exception.FileStorageException;
import com.abnerkaizer.rest_springboot_java.exception.RequiredObjectIsNullException;
import com.abnerkaizer.rest_springboot_java.exception.ResourceNotFoundException;
import static com.abnerkaizer.rest_springboot_java.mapper.ObjectMapper.parseObject;

import com.abnerkaizer.rest_springboot_java.file.exporter.contract.PersonExporter;
import com.abnerkaizer.rest_springboot_java.file.exporter.factory.FileExporterFactory;
import com.abnerkaizer.rest_springboot_java.file.importer.contract.FileImporter;
import com.abnerkaizer.rest_springboot_java.file.importer.factory.FileImporterFactory;
import com.abnerkaizer.rest_springboot_java.model.Person;
import com.abnerkaizer.rest_springboot_java.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final Logger logger = LoggerFactory.getLogger(PeopleService.class.getName());

    @Autowired
    PeopleRepository repository;

    @Autowired
    FileImporterFactory importer;
    @Autowired
    FileExporterFactory exporter;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;


    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {

        logger.info("Finding all People!");

        var people = repository.findAll(pageable);

        return buildPagedModel(pageable, people);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {

        logger.info("Finding People by name!");

        var people = repository.findPeopleByName(firstName, pageable);

        return buildPagedModel(pageable, people);
    }

    public Resource exportPerson(Long id, String acceptHeader) {
        logger.info("Exporting data from one Person!");

        var person = repository.findById(id)
                .map(entity -> parseObject(entity, PersonDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        try {
            PersonExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportPerson(person);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!",e);
        }
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) {

        logger.info("Exporting a People page!");

        var people = repository.findAll(pageable)
                .map(person -> parseObject(person, PersonDTO.class))
                .getContent();
        try {
            PersonExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportPeople(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!",e);
        }
    }

    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> massCreation(MultipartFile file) {

        logger.info("Importing People from file!");

        if (file.isEmpty()) throw new BadRequestException("Please set a valid file!");

        try(InputStream inputStream = file.getInputStream()){
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(()-> new BadRequestException("File name cannot be null!"));

            FileImporter importer = this.importer.getImporter(filename);

            List<Person> entities = importer.importFile(inputStream).stream()
                    .map(dto -> repository.save(parseObject(dto, Person.class)))
                    .toList();
            return entities.stream().map(entity -> {
                var dto = parseObject(entity, PersonDTO.class);
                addHateoasLinks(dto);
                return dto;
            }).toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file!");
        }
    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {

        logger.info("Disabling one Person!");

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PeopleController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PeopleController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PeopleController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PeopleController.class).findByName(dto.getFirstName(),1,12,"asc")).withRel("findByName").withType("GET"));
        dto.add(linkTo(methodOn(PeopleController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PeopleController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        dto.add(linkTo(methodOn(PeopleController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PeopleController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PeopleController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PeopleController.class)
                .exportPage(1,12,"asc", null))
                .withRel("exportPage")
                .withType("GET")
                .withTitle("Export People"));
    }
}