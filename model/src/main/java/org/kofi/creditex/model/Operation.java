package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Date;

/**
 * Операция вноса/снятия денег
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id","amount"})
@ToString(of = {"id","amount"})
public class Operation {
    /**
     * ID операции
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Количество денег, участвующих в операции
     */
    @Min(0)
    private long amount;
    /**
     * Ссылка на кредит {@link Credit}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Credit credit;
    /**
     * Тип операции {@link OperationType}
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;
    /**
     * Ссылка на операциониста {@link User}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User operator;
    /**
     * Дата совершения операции
     */
    @Column(nullable = false)
    private Date operationDate;
}
