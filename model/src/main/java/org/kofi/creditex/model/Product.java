package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Название кредитного продукта
     */
    @Size(max = 48)
    @Pattern(regexp = "^\\w+")
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * Активен ли кредитный продукт (можно ли по нему создавать новые кредиты)
     * <p>Не активный кредитный продукт <b>не виден</b> специалисту по работе с клиентами</p>
     */
    private boolean active;
    /**
     * Процентная ставка по кредитному продукту
     */
    @Min(0)
    private float percent;
    /**
     * Минимальная сумма для рассмотрения заявки на данный кредитный продукт кредитным комитетом
     */
    @Min(0)
    private long minCommittee;
    /**
     * Минимальная сумма основного долга, с которой предоставляется данный кредитный продукт
     */
    @Min(0)
    private long minMoney;
    /**
     * Максимальная сумма основного долга, до которого предоставляется данный кредитный продукт
     */
    @Min(0)
    private long maxMoney;
    /**
     * Минимальная длительность, с которой возможно предоставление кредитного продукта
     */
    @Min(0)
    private long minDuration;
    /**
     * Максимальная длительность, на которую предоставляется кредитный продукт
     */
    @Min(0)
    private long maxDuration;
    /**
     * Процент пени за день просрочки платежа
     * <p>100 процентам соответствует 100, 0 - 0</p>
     */
    @Min(0)
    private float debtPercent;//fine for 1 day payment delay, %
    /**
     * Процент штрафа за предварительный возврат
     * <p>100 процентам соответствует 100, 0 - 0</p>
     */
    @Min(0)
    private float priorRepaymentPercent;//fine for prior repayment, %
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorRepayment prior;
    /**
     * Тип кредита {@link ProductType}
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;
}
