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
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Дата начала кредита
     */
    @Column(nullable = false)
    private Date creditStart;
    /**
     * Продолжительность кредита в месяцах
     */
    @Min(0)
    private long duration;
    /**
     * Дата завершения кредита
     */
    @Column(nullable = false)
    private Date creditEnd;
    /**
     * Текущий основной долг кредита
     */
    @Min(0)
    private long currentMainDebt;
    /**
     * Текущий процентный долг (сколько осталось выплатить сверх основного долга)
     */
    @Min(0)
    private long currentPercentDebt;
    /**
     * Текущие деньги на счету
     * <p>Изначально равны {@link #originalMainDebt}</p>
     */
    @Min(0)
    private long currentMoney;
    /**
     * Сумма всех просроченных платежей
     */
    @Min(0)
    private long mainFine;
    /**
     * Пеня по всем просроченным платежам
     */
    @Min(0)
    private long percentFine;
    /**
     * Первичный основной долг (сколько пользователь взял у банка)
     */
    @Min(0)
    private long originalMainDebt;
    /**
     * Активен ли кредит в данный момент
     */
    private boolean running;
    /**
     * Просрочен ли кредит
     */
    private boolean expired;
    /**
     * Ссылка на кредитный продукт {@link Product}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
    @OneToOne
    @JoinColumn(nullable = false)
    private Application creditApplication;
    /**
     * Ссылка на клента банка {@link User}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    /**
     * Список платежей по кредиту {@link Payment}
     */
    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    /**
     * Все уведомления по данному кредиту
     */
    @OneToMany(mappedBy = "credit")
    private List<Notification> notifications;
    /**
     * Дата последнего уведомления
     */
    private Date lastNotificationDate;
    /**
     * Является ли кредит невозвращённым
     */
    private boolean unreturned;
    /**
     * Количество удовлетворённых пролонгаций по данному кредиту
     */
    private long prolongations;
    /**
     * Предварительный возврат кредита
     */
    private boolean priorRepayment;
}
