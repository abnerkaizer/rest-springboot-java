package com.abnerkaizer.rest_springboot_java.integrationtests.dto.wrappers.xmlandyml;

import com.abnerkaizer.rest_springboot_java.integrationtests.dto.BookDTO;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@XmlRootElement()
public class PagedModelBook implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<BookDTO> content;

    public PagedModelBook() {}

    public List<BookDTO> getContent() {
        return content;
    }

    public void setContent(List<BookDTO> content) {
        this.content = content;
    }
}
