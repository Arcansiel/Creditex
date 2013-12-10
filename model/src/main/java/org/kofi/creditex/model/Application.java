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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Требуемые деньги
     */
    private int request;
    /**
     * Продолжительность кредита
     */
    private int duration;
    /**
     * Принята ли заявка
     * <p>Если принята - true</p>
     * <p>Если в рассмотрении - Null</p>
     * <p>Если отвергнута - false</p>
     */
    private Boolean acceptance;
    /**
     * Дата подачи заявки
     */
    private Date applicationDate;
    /**
     * Ссылка на требуемый кредитный продукт {@link Product}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
    /**
     * Ссылка на клиента банка {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User client;
    /**
     * Ссылка на специалиста по работе с клиентами {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User accountManager;
    /**
     * Принята ли заявка специалистом службы безопасности
     * <p>Если принята - true</p>
     * <p>Если в рассмотрении - Null</p>
     * <p>Если отвергнута - false</p>
     */
    private Boolean securityAcceptance;
    /**
     * Комментарий специалиста службы безопасности
     */
    private String securityComment;
    /**
     * Ссылка на специалиста службы безопасности {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User security;
    /**
     * Количество голосов за принятие заявки кредитным комитетом
     */
    private int voteAcceptance;
    /**
     * Количество голосов против принятия заявки кредитным комитетом
     */
    private int voteRejection;
    /**
     * Закрыто ли голосование кредитным комитетом
     */
    private boolean votingClosed;
    /**
     * Принял ли заявку кредитный комитет
     * <p>Если принята - true</p>
     * <p>Если в рассмотрении - Null</p>
     * <p>Если отвергнута - false</p>
     */
    private Boolean committeeAcceptance;
    /**
     * Список всех голосов {@link Vote}
     */
    @OneToMany(mappedBy = "application")
    private List<Vote> votes;
    /**
     * Принял ли заявку глава кредитного отдела
     * <p>Если принята - true</p>
     * <p>Если в рассмотрении - Null</p>
     * <p>Если отвергнута - false</p>
     */
    private Boolean headAcceptance;
    /**
     * Комментарий кредитного отдела
     */
    private String headComment;
    /**
     * Ссылка на главу кредитного отдела {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User head;
}
