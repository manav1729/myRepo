package com.finance.dbclmrestapp.controller;

import com.finance.dbclmrestapp.model.DbClmRestResponse;
import com.finance.dbclmrestapp.model.Nace;
import com.finance.dbclmrestapp.helper.CSVHelper;
import com.finance.dbclmrestapp.service.DbClmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/dbclm")
public class DbClmRestController {

    @Autowired
    private DbClmService dbClmService;

    @Autowired
    private CSVHelper csvHelper;

    @RequestMapping(value = "/putNaceDetails", method = RequestMethod.POST)
    public ResponseEntity<DbClmRestResponse> putNaceDetails(@RequestParam("file") MultipartFile file) {
        log.info("Inside DbClmRestController.putNaceDetails method.");
        String message;
        List<Nace> naceList;
        if (csvHelper.hasCSVFormat(file)) {
            try {
                naceList = dbClmService.putNaceDetails(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return new ResponseEntity<>(new DbClmRestResponse(message, naceList), HttpStatus.OK);

            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseEntity<>(new DbClmRestResponse(message, null), HttpStatus.EXPECTATION_FAILED);

            }
        }
        message = "Please upload a csv file!";
        return new ResponseEntity<>(new DbClmRestResponse(message, null), HttpStatus.EXPECTATION_FAILED);
    }

    @RequestMapping(value = "/getNaceDetails/{order}", method = RequestMethod.GET)
    public ResponseEntity<DbClmRestResponse> getNaceDetails(@PathVariable String order) {
        log.info("Inside DbClmRestController.getNaceDetails method.");
        List<Nace> naceList = new ArrayList<>();
        String message;
        HttpStatus status;

        Nace nace = dbClmService.getNaceDetails(order);
        if(Objects.nonNull(nace)){
            status = HttpStatus.OK;
            message = "NACE Details found for order :"+order;
            naceList.add(nace);
        } else {
            status = HttpStatus.NOT_FOUND;
            message = "NACE Details not found for order :"+order;
        }

        log.info("Fetched Nace Details for order : {} is : {}", order, nace);
        return new ResponseEntity<>(new DbClmRestResponse(message, naceList), status);
    }

    @RequestMapping(value = "/getAllNaceDetails", method = RequestMethod.GET)
    public ResponseEntity<DbClmRestResponse> getAllNaceDetails() {
        log.info("Inside DbClmRestController.getAllNaceDetails method.");
        String message;
        HttpStatus status;
        List<Nace> naceList = dbClmService.getAllNaceDetails();

        if(Objects.nonNull(naceList) && naceList.size() > 0){
            status = HttpStatus.OK;
            message = "All NACE Details found.";
        } else {
            status = HttpStatus.NOT_FOUND;
            message = "No NACE Details found.";
        }

        log.info("All Fetched Nace Details is : {}", naceList);
        return new ResponseEntity<>(new DbClmRestResponse(message, naceList), status);
    }
}