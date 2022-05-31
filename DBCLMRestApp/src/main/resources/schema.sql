CREATE TABLE NACE_DATA
(
    order_no           VARCHAR(10) NOT NULL,
    level_no              INTEGER NULL,
    code               VARCHAR(10) NULL,
    parent             VARCHAR(10) NULL,
    description        VARCHAR(200) NULL,
    item_includes      VARCHAR(6000) NULL,
    item_also_includes VARCHAR(1200) NULL,
    rulings            VARCHAR(300) NULL,
    item_excludes      VARCHAR(1200) NULL,
    reference          VARCHAR(100) NULL,
    PRIMARY KEY (order_no)
);