package com.abnerkaizer.rest_springboot_java.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;


public class WrapperBookDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedDTO embedded;

    public WrapperBookDTO() {}

    public BookEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BookEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
