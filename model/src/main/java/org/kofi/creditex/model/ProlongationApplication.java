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
 * Заявка на пролонгацию кредита
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "comment"})
@ToString(of = {"id", "comment"})
public class ProlongationApplication {
    /**
     * ID
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Длительность продления кредита
     */
    @Min(0)
    private long duration;
    /**
     * Комментарий клиента о причине пролонгации
     */
    @Size(max = 4000)
    @Column(nullable = false)
    private String comment;
    /**
     * Ссылка на специалиста по работе с клиентами
     */
    @ManyToOne
    @JoinColumn
    private User accountManager;
    /**
     * Ссылка на кредит по заявке
     */
    @ManyToOne
    @JoinColumn
    private Credit credit;
    /**
     * Ссылка на клиента
     */
    @ManyToOne
    @JoinColumn
    private User client;
    /**
     * Принятие заявки
     */
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
