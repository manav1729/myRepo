package com.finance.creditcardapp.controller;

import com.finance.creditcardapp.model.CreditCard;
import com.finance.creditcardapp.model.CreditCardResponse;
import com.finance.creditcardapp.model.CreditCardStatus;
import com.finance.creditcardapp.service.CreditCardService;
import com.finance.creditcardapp.validator.CreditCardValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreditCardControllerTest {

    @Mock
    CreditCardService creditCardService;

    CreditCardValidator creditCardValidator = new CreditCardValidator();

    @InjectMocks
    CreditCardController controller;

    @BeforeEach
    void setup() {
        controller.creditCardValidator = creditCardValidator;
    }

    @Test
    void getStocksTest() {
        when(creditCardService.getAllCreditCards()).thenReturn(getStocks());

        ResponseEntity<CreditCardResponse> result = controller.getStocks();
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.OK));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.SUCCESS));
        assertTrue(Objects.equals(result.getBody().getMessage(), ""));
        assertTrue(Objects.equals(result.getBody().getData().size(), 2));
    }

    @Test
    void addStockTestSuccess() {
        CreditCard cc = new CreditCard("79927398713", "Lucky", 10000, 0.00);
        when(creditCardService.addCreditCard(cc)).thenReturn(true);

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.CREATED));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.CREATED));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Credit Card is added successfully."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestFailed() {
        CreditCard cc = new CreditCard("79927398713", "Lucky", 10000, 0.00);
        when(creditCardService.addCreditCard(cc)).thenReturn(false);

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.EXPECTATION_FAILED));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.FAILED));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Adding Credit Card failed."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestException() {
        CreditCard cc = new CreditCard("79927398713", "Lucky", 10000, 0.00);
        when(creditCardService.addCreditCard(cc)).thenThrow(new RuntimeException("Database Connection Failed"));

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.FAILED));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Adding Credit Card failed with Exception. Database Connection Failed"));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestFailCreditCardNull() {
        CreditCard cc = null;

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.BAD_REQUEST));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.INVALID_INPUT));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Credit Card details cannot be null."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestFailCreditCardNumberNonNumeric() {
        CreditCard cc = new CreditCard("hfdsjfksgdfhj", "Lucky", 10000, 0.00);

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.BAD_REQUEST));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.INVALID_INPUT));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Credit Card Number invalid : Only numeric values allowed."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestFailCreditCardNumberTooLong() {
        CreditCard cc = new CreditCard("12345678901234567890", "Lucky", 10000, 0.00);

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.BAD_REQUEST));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.INVALID_INPUT));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Credit Card Number invalid : Maximum length allowed is 19."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    @Test
    void addStockTestFailCreditCardNumberNonLuhn10() {
        CreditCard cc = new CreditCard("7992739871310", "Lucky", 10000, 0.00);

        ResponseEntity<CreditCardResponse> result = controller.addStock(cc);
        assertTrue(Objects.nonNull(result));
        assertTrue(Objects.nonNull(result.getBody()));
        assertTrue(Objects.nonNull(result.getBody().getData()));

        assertTrue(Objects.equals(result.getStatusCode(), HttpStatus.BAD_REQUEST));
        assertTrue(Objects.equals(result.getBody().getStatus(), CreditCardStatus.INVALID_INPUT));
        assertTrue(Objects.equals(result.getBody().getMessage(), "Credit Card Number invalid : Failed Luhn 10 Algo Check."));
        assertTrue(Objects.equals(result.getBody().getData().size(), 1));
    }

    private List<CreditCard> getStocks() {
        List<CreditCard> creditCards = new ArrayList<>();
        creditCards.add(new CreditCard("79927398713", "J Bond", 10000, 5000.00));
        creditCards.add(new CreditCard("1122112212112", "M Jack", 20000, 1200.00));
        return creditCards;
    }
}