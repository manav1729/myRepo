package com.finance.creditcardapp.service;

import com.finance.creditcardapp.model.CreditCard;

import java.util.List;

public interface CreditCardService {
    List<CreditCard> getAllCreditCards();

    boolean addCreditCard(CreditCard creditCard);
}
