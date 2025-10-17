package com.abnerkaizer.rest_springboot_java.integrationtests.controllers.withjson;

import com.abnerkaizer.rest_springboot_java.config.TestConfigs;
import com.abnerkaizer.rest_springboot_java.integrationtests.dto.BookDTO;
import com.abnerkaizer.rest_springboot_java.integrationtests.dto.wrappers.json.WrapperBookDTO;
import com.abnerkaizer.rest_springboot_java.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BooksControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        book = new BookDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ABNERKAIZER)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus", createdBook.getAuthor());
        assertNotNull(createdBook.getLaunchDate());
        assertEquals("Linux", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        book.setAuthor("Linus Benedict Torvalds");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus Benedict Torvalds", createdBook.getAuthor());
        assertNotNull(createdBook.getLaunchDate());
        assertEquals("Linux", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Linus Benedict Torvalds", createdBook.getAuthor());
        assertNotNull(createdBook.getLaunchDate());
        assertEquals("Linux", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());
    }

    @Test
    @Order(4)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperBookDTO wrapper = objectMapper.readValue(content, WrapperBookDTO.class);
        List<BookDTO> books = wrapper.getEmbedded().getBooks();
        BookDTO bookOne = books.get(0);

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);

        assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", bookOne.getTitle());
        assertEquals(54D, bookOne.getPrice());

        BookDTO bookFour = books.get(4);

        assertNotNull(bookFour.getId());
        assertTrue(bookFour.getId() > 0);

        assertEquals("Eric Evans", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals("Domain Driven Design", bookFour.getTitle());
        assertEquals(92D, bookFour.getPrice());
    }

    private void mockBook() {
        book.setAuthor("Linus");
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Linux");
    }
}