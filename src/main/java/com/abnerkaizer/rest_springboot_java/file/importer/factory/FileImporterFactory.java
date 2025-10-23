package com.abnerkaizer.rest_springboot_java.file.importer.factory;

import com.abnerkaizer.rest_springboot_java.exception.BadRequestException;
import com.abnerkaizer.rest_springboot_java.file.importer.contract.FileImporter;
import com.abnerkaizer.rest_springboot_java.file.importer.impl.CsvImporter;
import com.abnerkaizer.rest_springboot_java.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{
        logger.info("Importing file!");

        if (fileName.endsWith(".xlsx")){
            return context.getBean(XlsxImporter.class);
        }else if (fileName.endsWith(".csv")){
            return context.getBean(CsvImporter.class);
        }else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
