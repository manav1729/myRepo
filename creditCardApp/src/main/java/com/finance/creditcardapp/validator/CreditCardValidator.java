package com.finance.creditcardapp.validator;

import com.finance.creditcardapp.model.CreditCard;
import com.finance.creditcardapp.model.CreditCardResponse;
import com.finance.creditcardapp.model.CreditCardStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.h2.util.StringUtils.isNumber;

/**
 * Input Validation Class
 */
@Component
public class CreditCardValidator {
    public CreditCardResponse validateCreditCardDetails(final CreditCardResponse response ,
                                                        final CreditCard creditCard) {
        if(Objects.isNull(creditCard)){
            response.setStatus(CreditCardStatus.INVALID_INPUT);
            response.setMessage("Credit Card details cannot be null.");
        } else if(!validCreditCardNumberIsNumeric(creditCard.getCardNumber())) {
            response.setStatus(CreditCardStatus.INVALID_INPUT);
            response.setMessage("Credit Card Number invalid : Only numeric values allowed.");
        } else if(!validCreditCardNumberLength(creditCard.getCardNumber())) {
            response.setStatus(CreditCardStatus.INVALID_INPUT);
            response.setMessage("Credit Card Number invalid : Maximum length allowed is 19.");
        } else if(!validCreditCardNumberByLuhn10Algo(creditCard.getCardNumber())) {
            response.setStatus(CreditCardStatus.INVALID_INPUT);
            response.setMessage("Credit Card Number invalid : Failed Luhn 10 Algo Check.");
        } else {
            response.setStatus(CreditCardStatus.VALID_INPUT);
            response.setMessage("Credit Card details valid.");
        }

        return response;
    }

    /**
     *
     * @param cardNumber
     * @return true if card number has only numeric values
     */
    private boolean validCreditCardNumberIsNumeric(final String cardNumber) {
        return isNumber(cardNumber);
    }

    /**
     *
     * @param cardNumber
     * @return true if card number length less than 20
     */
    private boolean validCreditCardNumberLength(final String cardNumber) {
        // Card number length can be max 19
        return cardNumber.length() < 20 ? true : false;
    }

    /**
     * According to Luhn10 Algo or Mod Algo used by Credit Card companies
     * While traversing the Credit Card number from the end
     * first double every second digit then,
     * if it's less than 10 take the digit as is else add the digits of the obtained number to get single digit number.
     * Get the sum of all such resultant digits along with the other untouched digits to find the sum.
     * This sum should be divisible by 10.
     *
     *
     * @param cardNumber
     * @return true if valid else thow error
     */
    private boolean validCreditCardNumberByLuhn10Algo(String cardNumber) {
        List<Integer> digitList = new ArrayList<>();
        for (char c : cardNumber.toCharArray()) {
            digitList.add(Integer.parseInt(String.valueOf(c)));
        }

        // reversing the list for ease of traversal
        Collections.reverse(digitList);

        // Simply finding the sum
        Integer oddPosSum = IntStream.range(0, digitList.size())
                .filter(i -> i % 2 == 0).map(i -> digitList.get(i)).sum();

        // Doubling every second number, using it as is if less than 10 else adding the digits.
        Integer evenPosSum = IntStream.range(0, digitList.size())
                .filter(i -> i % 2 == 1).map(i -> digitList.get(i) * 2 > 9
                        ? ((digitList.get(i) * 2) / 10) + ((digitList.get(i) * 2) % 10)
                        : digitList.get(i) * 2).sum();

        // The sum must be divisible by 10
        return (oddPosSum + evenPosSum) % 10 == 0 ? true : false;
    }
}
