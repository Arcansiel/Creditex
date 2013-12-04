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
@EqualsAndHashCode(of = {"id", "name", "percent", "minCommittee", "minMoney", "maxMoney", "debtPercent", "priorRepaymentPercent"})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "percent")
    private int percent;
    @Column(name = "min_committee")
    private int minCommittee;
    @Column(name = "min_money")
    private int minMoney;
    @Column(name = "max_money")
    private int maxMoney;
    @Column(name = "debt_percent")
    private float debtPercent;//fine for 1 day payment delay, %
    @Column(name = "prior_repayment_percent")
    private int priorRepaymentPercent;//fine for prior repayment, %
    @Column(name = "prior_repayment_debt_limit")
    private float priorRepaymentDebtLimit;//debt limit (1..0) to allow prior repayment
    @OneToMany(mappedBy = "product")
    private List<Application> applications;
    @OneToMany(mappedBy = "product")
    private List<Credit> credits;
    @ManyToOne
    @JoinColumn(name = "prior", referencedColumnName = "id", nullable = false)
    private PriorRepayment prior;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private ProductType type;
}
