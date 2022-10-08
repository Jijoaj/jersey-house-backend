package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.model.Country;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("main")
public interface MainControllerInterface {

    @GetMapping("connect")
    ResponseEntity<String> connect();

    @GetMapping("getCountryList")
    ResponseEntity<List<Country>> getCountryList();

}
