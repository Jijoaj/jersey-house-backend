package com.jijo.jerseyhouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("main")
public interface MainControllerInterface {

    @GetMapping("connect")
    ResponseEntity<String> connect();

}
