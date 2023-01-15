package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.StockControllerInterface;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.service.StockUpdateService;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class StockController implements StockControllerInterface {

    private final StockUpdateService stockUpdateService;

    public StockController(StockUpdateService stockUpdateService) {
        this.stockUpdateService = stockUpdateService;
    }

    /**
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<Map<String, String>> updateStockFromFile(MultipartFile file) throws CommonInternalException, IOException {
        if (file.isEmpty()) {
            throw new CommonInternalException(HttpStatus.BAD_REQUEST, "Please select a file to upload");
            }
        stockUpdateService.updateStock(file);
        return new ResponseEntity<>(CommonMethods.getSuccessMapResponse(), HttpStatus.OK);
    }
}
