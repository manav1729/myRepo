CREATE TABLE CREDIT_CARD (
    card_number VARCHAR(19) NOT NULL,
    given_name VARCHAR(128) NULL,
    card_limit INTEGER NOT NULL,
    card_balance DECIMAL DEFAULT 0.0,
    PRIMARY KEY (card_number)
);