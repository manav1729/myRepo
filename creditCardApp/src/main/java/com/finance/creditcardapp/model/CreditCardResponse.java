package com.finance.creditcardapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class for Json Response
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponse {
    CreditCardStatus status;
    String message;
    List<CreditCard> data;
}
