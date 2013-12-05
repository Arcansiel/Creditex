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
@EqualsAndHashCode(of = {"id", "number", "requiredPayment", "start", "end", "closed"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number;
    // sum -> requiredPayment
    private int requiredPayment;
    // start -> paymentStart
    private Date paymentStart;
    // end -> paymentEnd
    private Date paymentEnd;
    // closed -> paymentClosed
    private boolean paymentClosed;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Credit credit;
}
