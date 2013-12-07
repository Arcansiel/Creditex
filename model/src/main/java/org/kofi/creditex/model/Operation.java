package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

@Entity
@Data
@Builder
@Accessors
@EqualsAndHashCode(of = {"id","amount"})
@ToString(of = {"id","amount"})
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int amount;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Credit credit;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User operator;
}
