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
 * Платёж по кредиту
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "number", "requiredPayment", "paymentStart", "paymentEnd", "paymentClosed"})
@ToString(of = {"id", "number", "requiredPayment", "paymentStart", "paymentEnd", "paymentClosed"})
public class Payment {
    /**
     * ID платежа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Номер платежа
     */
    private int number;
    /**
     * Требуемая сумма платежа
     */
    private int requiredPayment;
    /**
     * Сколько дополнительного долга в сумме платежа (в руб)
     */
    //requiredPayment = mainDebt + percents
    private int percents;
    /**
     * Дата начала интервала платежа
     */
    // start -> paymentStart
    private Date paymentStart;
    /**
     * Дата завершения интервала платежа
     */
    // end -> paymentEnd
    private Date paymentEnd;
    /**
     * Был ли оплачен платёж
     */
    // closed -> paymentClosed
    private boolean paymentClosed;
    /**
     * Был ли платёж просрочен
     */
    private boolean paymentExpired;
    /**
     * Была ли добавлена сумма платежа к основной части штрафа
     */
    private boolean paymentExpiredProcessed;
    /**
     * Ссылка на кредит {@link Credit}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Credit credit;
}
