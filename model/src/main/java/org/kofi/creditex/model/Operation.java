package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id","amount"})
public class Operation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "amount")
    private int amount;
    @ManyToOne
    @JoinColumn(name = "credit", referencedColumnName = "id", nullable = false)
    private Credit credit;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private OperationType type;
    @ManyToOne
    @JoinColumn(name = "operator", referencedColumnName = "id", nullable = false)
    private UserCredentials operator;
}
