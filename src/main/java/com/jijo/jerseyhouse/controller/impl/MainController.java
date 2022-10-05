package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.MainControllerInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController implements MainControllerInterface {
    @Override
    public ResponseEntity<String> connect() {
        return new ResponseEntity<>("Successfully connected to Jersey house !",HttpStatus.OK);
    }
}
