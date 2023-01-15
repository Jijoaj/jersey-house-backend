package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.exception.CommonInternalException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StockUpdateService {
    void updateStock(MultipartFile file) throws CommonInternalException, IOException;
}
