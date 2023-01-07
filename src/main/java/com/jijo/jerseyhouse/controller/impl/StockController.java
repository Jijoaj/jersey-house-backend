package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.StockControllerInterface;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class StockController implements StockControllerInterface {
    /**
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<Map<String, String>> updateStockFromFile(MultipartFile file) throws CommonInternalException {
        if (file.isEmpty()) {
            throw new CommonInternalException("Please select a file to upload");
            }
        return new ResponseEntity<>(CommonMethods.getSuccessMapResponse(), HttpStatus.OK);
    }
}
