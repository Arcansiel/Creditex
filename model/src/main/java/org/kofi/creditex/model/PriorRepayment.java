package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id", "value"})
public class PriorRepayment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "value")
    private String value;
    @OneToMany(mappedBy = "prior")
    private List<Product> products;
}
