package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Кредит
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "creditStart", "duration", "currentMainDebt", "currentMoney", "originalMainDebt", "currentPercentDebt"})
@ToString(of = {"id", "creditStart", "duration", "currentMainDebt", "currentMoney", "originalMainDebt", "currentPercentDebt"})
public class Credit {
    /**
     * ID кредита
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Дата начала кредита
     */
    private Date creditStart;
    /**
     * Продолжительность кредита в месяцах
     */
    private int duration;
    /**
     * Дата завершения кредита
     */
    private Date creditEnd;
    /**
     * Текущий основной долг кредита
     */
    private int currentMainDebt;
    /**
     * Текущий процентный долг (сколько осталось выплатить сверх основного долга)
     */
    private int currentPercentDebt;
    /**
     * Текущие деньги на счету
     * <p>Изначально равны {@link #originalMainDebt}</p>
     */
    private int currentMoney;
    /**
     * Сумма всех просроченных платежей
     */
    private int mainFine;
    /**
     * Пеня по всем просроченным платежам
     */
    private int percentFine;
    /**
     * Первичный основной долг (сколько пользователь взял у банка)
     */
    private int originalMainDebt;
    /**
     * Активен ли кредит в данный момент
     */
    private boolean running;
    /**
     * Ссылка на кредитный продукт {@link Product}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
    /**
     * Ссылка на клента банка {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User user;
    /**
     * Список платежей по кредиту {@link Payment}
     */
    @OneToMany(mappedBy = "credit")
    private List<Payment> payments;
    /**
     * Список всех операций с деньгами на счету {@link Operation}
     */
    @OneToMany(mappedBy = "credit")
    private List<Operation> operations;
    /**
     * Список всех заявлений на пролонгацию кредита {@link ProlongationApplication}
     */
    @OneToMany(mappedBy = "credit")
    private List<ProlongationApplication> prolongationApplications;
    /**
     * Список всех заявлений на предварительное погашение кредита {@link PriorRepaymentApplication}
     */
    @OneToMany(mappedBy = "credit")
    private List<PriorRepaymentApplication> priorApplications;
}
