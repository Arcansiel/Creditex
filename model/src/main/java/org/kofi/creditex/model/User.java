package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Пользователь системы (общая сущность для всех ролей)
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "user_credentials")
@EqualsAndHashCode(of = {"id", "username", "password", "enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
@ToString(of = {"id", "username", "password", "enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
public class User implements org.springframework.security.core.userdetails.UserDetails{
    /**
     * ID пользователя
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Имя пользователя
     */
    @Size(max = 46, min = 8)
    @Column(nullable = false, unique = true)
    private String username;
    /**
     * Пароль пользователя
     */
    @Column(nullable = false)
    private String password;
    /**
     * Активен ли пользователь
     */
    private boolean enabled;
    /**
     * Активен ли аккаутнт пользователя
     * (поле Spring Security, не используется системой)
     */
    private boolean accountNonExpired;
    /**
     * Не заблокирован ли аккаунт пользователя
     * (поле Spring Security, не используется системой)
     */
    private boolean accountNonLocked;
    /**
     * Не просрочены ли учётные данные пользователя
     * (поле Spring Security, не используется системой)
     */
    private boolean credentialsNonExpired;
    /**
     * Список заявок клиента {@link Application}
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Application> applicationsTo;
    /**
     * Список заявок, офромленных специалистом по работе с клиентами
     * {@link Application}
     */
    @OneToMany(mappedBy = "accountManager", cascade = CascadeType.ALL)
    private List<Application> applicationsBy;
    /**
     * Список заявок на предварительное погашение кредита,
     * оформленных специалистом по работе с клиентами
     * {@link PriorRepaymentApplication}
     */
    @OneToMany(mappedBy = "accountManager", cascade = CascadeType.ALL)
    private List<PriorRepaymentApplication> priorApplicationsBy;
    /**
     * Список заявок на предварительное погашение кредита клиента банка
     * {@link PriorRepaymentApplication}
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PriorRepaymentApplication> priorApplicationsTo;
    /**
     * Список заявок на пролонгацию кредита клиента банка
     * {@link ProlongationApplication}
     */
    @OneToMany(mappedBy = "accountManager", cascade = CascadeType.ALL)
    private List<ProlongationApplication> prolongationApplicationsBy;
    /**
     * Список заявок на пролонгацию кредита, оформленных специалистом по работе с клиентами
     * {@link ProlongationApplication}
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ProlongationApplication> prolongationApplicationsTo;
    /**
     * Список всех кредитов клиента банка (текущих и прошлых)
     * {@link Credit}
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Credit> credits;
    /**
     * Список всех операций, совершённых операционистом
     * {@link Operation}
     */
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
    private List<Operation> operations;
    /**
     * Роль пользователя {@link Authority}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Authority authority;

    /**
     * Обёртка поля {@link #authority}
     * Реализована для эмуляции отношения многие ко многим (Api Spring Security)
     * @return Список с одной ролью
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>(1);
        result.add(authority);
        return result;
    }

    /**
     * Данные пользователя {@link UserData}
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn
    private UserData userData;
    /**
     * Голоса, сделанные членом кредитного комитета {@link Vote}
     */
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Vote> votes;

    @OneToMany
    private List<Notification> notificationsTo;
    @OneToMany
    private List<Notification> notificationsBy;
}
