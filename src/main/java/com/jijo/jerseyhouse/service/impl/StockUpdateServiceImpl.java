package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.service.StockUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class StockUpdateServiceImpl implements StockUpdateService {
    /**
     * @param file
     */
    @Override
    public void updateStock(MultipartFile file) throws CommonInternalException, IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null && extension.equals("csv")) {
            BufferedReader fileReader = new BufferedReader(new
                    InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        } else {
            throw new CommonInternalException(HttpStatus.BAD_REQUEST, "Please select a csv file to upload");
        }
    }
}
