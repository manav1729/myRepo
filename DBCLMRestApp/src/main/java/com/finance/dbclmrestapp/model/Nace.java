package com.finance.dbclmrestapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "NACE_DATA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Nace {
    @Id
    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @Column(name = "level_no")
    private Integer levelNo;

    @Column(name = "code")
    private String code;

    @Column(name = "parent")
    private String parent;

    @Column(name = "description")
    private String description;

    @Column(name = "item_includes")
    private String itemIncludes;

    @Column(name = "item_also_includes")
    private String itemAlsoIncludes;

    @Column(name = "rulings")
    private String rulings;

    @Column(name = "item_excludes")
    private String itemExcludes;

    @Column(name = "reference")
    private String reference;
}
