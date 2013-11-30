package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id", "number", "sum", "start", "end", "closed"})
public class Payment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "sum")
    private int sum;
    @Column(name = "payment_start")
    private Date start;
    @Column(name = "payment_end")
    private Date end;
    @Column(name = "closed")
    private Boolean closed;
    @ManyToOne
    @JoinColumn(name = "credit", referencedColumnName = "id", nullable = false)
    private Credit credit;
}
