package com.finance.creditcardapp.service;

import com.finance.creditcardapp.model.CreditCard;
import com.finance.creditcardapp.repository.CreditCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private CreditCardRepo creditCardRepo;

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepo.findAll();
    }

    @Override
    public boolean addCreditCard(final CreditCard creditCard) {
        creditCard.setCardBalance(Objects.isNull(creditCard.getCardBalance()) ? 0.00 : creditCard.getCardBalance());
        return Objects.nonNull(creditCardRepo.save(creditCard)) ? true : false;
    }
}
