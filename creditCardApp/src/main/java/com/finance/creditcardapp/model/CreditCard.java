package com.finance.creditcardapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CREDIT_CARD")
public class CreditCard {
    @Id
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "card_limit", nullable = false)
    private Integer cardLimit;

    @Column(name = "card_balance")
    private Double cardBalance;
}
