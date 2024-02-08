package userserver.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import userserver.payload.response.SuccessMessageResponse;

@Slf4j
@RestController
@RequestMapping("/v1/api/test")
public class TestController {

    @GetMapping("/all")
    public ResponseEntity<?> allEnable() {
        return ResponseEntity.ok().body(new SuccessMessageResponse("Public Content"));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userEnable() {
        return ResponseEntity.ok().body(new SuccessMessageResponse("User Content"));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminEnable() {
        return ResponseEntity.ok().body(new SuccessMessageResponse("Admin Content"));
    }
}
