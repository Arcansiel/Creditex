package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors
@EqualsAndHashCode(of = {"id", "name", "percent", "minCommittee", "minMoney", "maxMoney", "debtPercent", "priorRepaymentPercent"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int percent;
    private int minCommittee;
    private int minMoney;
    private int maxMoney;
    private int minDuration;
    private int maxDuration;
    private float debtPercent;//fine for 1 day payment delay, %
    private int priorRepaymentPercent;//fine for prior repayment, %
    private float priorRepaymentDebtLimit;//debt limit (1..0) to allow prior repayment
    @OneToMany(mappedBy = "product")
    private List<Application> applications;
    @OneToMany(mappedBy = "product")
    private List<Credit> credits;
    @Enumerated(EnumType.STRING)
    private PriorRepayment prior;
    @Enumerated(EnumType.STRING)
    private ProductType type;
}
