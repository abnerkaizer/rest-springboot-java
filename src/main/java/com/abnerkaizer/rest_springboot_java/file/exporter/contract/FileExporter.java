package com.abnerkaizer.rest_springboot_java.file.exporter.contract;

import com.abnerkaizer.rest_springboot_java.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
}
