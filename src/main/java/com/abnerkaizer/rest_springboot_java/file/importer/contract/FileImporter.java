package com.abnerkaizer.rest_springboot_java.file.importer.contract;

import com.abnerkaizer.rest_springboot_java.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
