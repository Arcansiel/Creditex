package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id", "start", "duration", "sum", "money", "debt", "percentSum"})
public class Credit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "start")
    private Date start;
    @Column(name = "duration")
    private int duration;
    @Column(name = "sum")
    private int sum;
    @Column(name = "percent_sum")
    private int percentSum;
    @Column(name = "money")
    private int money;
    @Column(name = "debt")
    private int debt;
    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_credentials", referencedColumnName = "id", nullable = false)
    private UserCredentials userCredentials;
    @OneToMany(mappedBy = "credit")
    private List<Payment> payments;
    @OneToMany(mappedBy = "credit")
    private List<Operation> operations;
}
