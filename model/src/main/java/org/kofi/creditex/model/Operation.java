package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Количество денег, участвующих в операции
     */
    private int amount;
    /**
     * Ссылка на кредит {@link Credit}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Credit credit;
    /**
     * Тип операции {@link OperationType}
     */
    @Enumerated(EnumType.STRING)
    private OperationType type;
    /**
     * Ссылка на операциониста {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User operator;
    /**
     * Дата совершения операции
     */
    @Column(nullable = false)
    private Date operationDate;
}
