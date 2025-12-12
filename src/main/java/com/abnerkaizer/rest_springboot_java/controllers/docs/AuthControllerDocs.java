package com.abnerkaizer.rest_springboot_java.controllers.docs;

import com.abnerkaizer.rest_springboot_java.data.dto.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthControllerDocs {
    @Operation(summary = "Authenticates an user and returns a token")
    @PostMapping("/signin")
    ResponseEntity<?> signIn(@RequestBody AccountCredentialsDTO credentials);

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping("/refresh/{username}")
    ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                   @RequestHeader("Authorization") String refreshToken);
}
