package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Заявка на предварительное погашение кредита
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "comment"})
@ToString(of = {"id", "comment"})
public class PriorRepaymentApplication {
    /**
     * ID
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Комментарий клиента
     */
    @Size(max = 4000)
    @Column(nullable = false)
    private String comment;
    /**
     * Ссылка на клиента
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User client;
    /**
     * Ссылка на специалиста по работе с клиентами
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User accountManager;
    /**
     * Ссылка на кредит
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Credit credit;
    /**
     * Принята ли заявка
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Acceptance acceptance;
    /**
     * Обработана ли заявка
     */
    private boolean processed;
    /**
     * Дата подачи заявки
     */
    @Column(nullable = false)
    private Date applicationDate;
}
