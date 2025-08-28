package com.abnerkaizer.rest_springboot_java.data.dto.v1;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.abnerkaizer.rest_springboot_java.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
//@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
//    @JsonProperty("first_name")
    private String firstName;
//    @JsonProperty("last_name")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private String phoneNumber;
//

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date birthday;
    private String address;
//    @JsonIgnore
//    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

//    private String sensitiveData;

    public PersonDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
