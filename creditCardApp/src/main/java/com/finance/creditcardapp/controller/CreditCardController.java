package com.finance.creditcardapp.controller;

import com.finance.creditcardapp.model.CreditCard;
import com.finance.creditcardapp.model.CreditCardResponse;
import com.finance.creditcardapp.model.CreditCardStatus;
import com.finance.creditcardapp.service.CreditCardService;
import com.finance.creditcardapp.validator.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value="/api/v1/creditcard")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    CreditCardValidator creditCardValidator;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public ResponseEntity<CreditCardResponse> getStocks(){
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        CreditCardResponse response = new CreditCardResponse(CreditCardStatus.SUCCESS, "", creditCards);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ResponseEntity<CreditCardResponse> addStock(@RequestBody CreditCard creditCard){

        CreditCardResponse response = new CreditCardResponse();
        List<CreditCard> data = new ArrayList<>();
        data.add(creditCard);
        response.setData(data); // The response returns the Credit Card details to be added in data.
        HttpStatus responseStatus;

        try {
            // Validating Inputs
            response = creditCardValidator.validateCreditCardDetails(response, creditCard);

            if(Objects.equals(response.getStatus(), CreditCardStatus.INVALID_INPUT)){
                // Invalid Input. The error message is already set.
                responseStatus = HttpStatus.BAD_REQUEST;
            } else {
                // Valid Input
                // Calling Insert to table
                boolean created = creditCardService.addCreditCard(creditCard);
                if(created){
                    // Credit Card details saved successfully.
                    response.setStatus(CreditCardStatus.CREATED);
                    response.setMessage("Credit Card is added successfully.");
                    responseStatus = HttpStatus.CREATED;
                } else {
                    // Credit Card details save error.
                    response.setStatus(CreditCardStatus.FAILED);
                    response.setMessage("Adding Credit Card failed.");
                    responseStatus = HttpStatus.EXPECTATION_FAILED;
                }
            }
            return new ResponseEntity<>(response, responseStatus);
        } catch (Exception e){
            // Exception encountered during Credit Card details add.
            response.setStatus(CreditCardStatus.FAILED);
            response.setMessage("Adding Credit Card failed with Exception. "+e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}