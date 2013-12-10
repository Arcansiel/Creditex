package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

/**
 * Кредитный продукт
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "name", "percent", "minCommittee", "minMoney", "maxMoney", "debtPercent", "priorRepaymentPercent"})
public class Product {
    /**
     * ID кредитного продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Название кредитного продукта
     */
    private String name;
    /**
     * Активен ли кредитный продукт (можно ли по нему создавать новые кредиты)
     * <p>Не активный кредитный продукт <b>не виден</b> специалисту по работе с клиентами</p>
     */
    private boolean active;
    /**
     * Процентная ставка по кредитному продукту
     */
    private int percent;
    /**
     * Минимальная сумма для рассмотрения заявки на данный кредитный продукт кредитным комитетом
     */
    private int minCommittee;
    /**
     * Минимальная сумма основного долга, с которой предоставляется данный кредитный продукт
     */
    private int minMoney;
    /**
     * Максимальная сумма основного долга, до которого предоставляется данный кредитный продукт
     */
    private int maxMoney;
    /**
     * Минимальная длительность, с которой возможно предоставление кредитного продукта
     */
    private int minDuration;
    /**
     * Максимальная длительность, на которую предоставляется кредитный продукт
     */
    private int maxDuration;
    /**
     * Процент пени за день просрочки платежа
     * <p>100 процентам соответствует 1000, 0 - 0</p>
     */
    private float debtPercent;//fine for 1 day payment delay, %
    /**
     * Процент штрафа за предварительный возврат
     * <p>100 процентам соответствует 1000, 0 - 0</p>
     */
    private int priorRepaymentPercent;//fine for prior repayment, %
    /**
     * Ссылка на на заявки на данный кредитный продукт {@link Application}
     */
    @OneToMany(mappedBy = "product")
    private List<Application> applications;
    /**
     * Сссылка на кредиты данного кредитного продукта {@link Credit}
     */
    @OneToMany(mappedBy = "product")
    private List<Credit> credits;
    /**
     * Возможно ли предварительное возвращение кредита {@link PriorRepayment}
     */
    @Enumerated(EnumType.STRING)
    private PriorRepayment prior;
    /**
     * Тип кредита {@link ProductType}
     */
    @Enumerated(EnumType.STRING)
    private ProductType type;
}
