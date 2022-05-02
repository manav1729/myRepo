package com.finance.creditcardapp.repository;

import com.finance.creditcardapp.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
}
