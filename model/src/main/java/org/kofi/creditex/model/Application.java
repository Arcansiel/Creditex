package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

/**
 * Заявка на кредит
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "request", "duration", "acceptance", "applicationDate"})
@ToString(of = {"id", "request", "duration", "acceptance", "applicationDate"})
public class Application {
    /**
     * ID заявки на кредит
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Требуемые деньги
     */
    @Min(0)
    private long request;
    /**
     * Продолжительность кредита
     */
    @Min(0)
    private long duration;
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
    /**
     * Ссылка на требуемый кредитный продукт {@link Product}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
    /**
     * Ссылка на клиента банка {@link User}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User client;
    /**
     * Ссылка на специалиста по работе с клиентами {@link User}
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User accountManager;
    /**
     * Принята ли заявка специалистом службы безопасности
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Acceptance securityAcceptance;
    /**
     * Комментарий специалиста службы безопасности
     */
    @Size(max = 4000)
    private String securityComment;
    /**
     * Ссылка на специалиста службы безопасности {@link User}
     */
    @ManyToOne
    @JoinColumn
    private User security;
    /**
     * Количество голосов за принятие заявки кредитным комитетом
     */
    @Min(0)
    private long voteAcceptance;
    /**
     * Количество голосов против принятия заявки кредитным комитетом
     */
    @Min(0)
    private long voteRejection;
    /**
     * Закрыто ли голосование кредитным комитетом
     */
    private boolean votingClosed;
    /**
     * Принял ли заявку кредитный комитет
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Acceptance committeeAcceptance;
    /**
     * Список всех голосов {@link Vote}
     */
    @OneToMany(mappedBy = "application")
    private List<Vote> votes;
    /**
     * Принял ли заявку глава кредитного отдела
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Acceptance headAcceptance;
    /**
     * Комментарий кредитного отдела
     */
    @Size(max = 4000)
    private String headComment;
    /**
     * Ссылка на главу кредитного отдела {@link User}
     */
    @ManyToOne
    @JoinColumn
    private User head;
    /**
     * Ссылка на кредит, который был создан по данной заявке
     */
    @OneToOne(mappedBy = "creditApplication", fetch = FetchType.EAGER)
    private Credit credit;
}
