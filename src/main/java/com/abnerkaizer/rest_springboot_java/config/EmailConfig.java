package com.abnerkaizer.rest_springboot_java.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String from;
    private Boolean ssl;

    public EmailConfig() {}

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmailConfig that = (EmailConfig) o;
        return Objects.equals(getHost(), that.getHost()) && Objects.equals(getPort(), that.getPort()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getFrom(), that.getFrom()) && Objects.equals(getSsl(), that.getSsl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHost(), getPort(), getUsername(), getPassword(), getFrom(), getSsl());
    }
}
