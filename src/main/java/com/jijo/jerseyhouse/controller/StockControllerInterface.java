package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.exception.CommonInternalException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("stock")
public interface StockControllerInterface {

    @PostMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> updateStockFromFile(@RequestParam("file") MultipartFile file) throws CommonInternalException;

}
