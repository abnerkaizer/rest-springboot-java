package com.abnerkaizer.rest_springboot_java.exception;

import java.util.Date;

public record ExceptionResponse(Date timeastamp, String message, String details) {
}
