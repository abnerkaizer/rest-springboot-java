package com.abnerkaizer.rest_springboot_java.config;

public interface TestConfigs {
    int  SERVER_PORT = 8888;

    String HEADER_PARAM_AUTH = "Authorization";

    String HEADER_PARAM_ORIGIN = "Origin";
    String ORIGIN_LOCAL = "http://localhost:8080";
    String ORIGIN_ABNERKAIZER = "https://abnerkaizer.com";
    String ORIGIN_PCLOUD = "https://pcloud.live";
}
