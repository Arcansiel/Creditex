package org.kofi.creditex.model;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Уведомление клиента о событии
 */
@Data
@Entity
public class Notification {
    /**
     * ID уведомления
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Сообщение уведомления
     */
    @Size(max=4000)
    @Column(nullable = false)
    private String message;
    /**
     * Тип уведомления
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    /**
     * Просмотрено ли уведомление
     */
    private boolean viewed;
    /**
     * Дата создания уведомления
     */
    @Column(nullable = false)
    private Date notificationDate;
    /**
     * Ссылка на клиента-получателя
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User client;
    /**
     * Ссылка на специалиста безовасности, отправившего уведомление
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User security;
    /**
     * Ссылка на кредит, по которому выдано данное уведомление
     */
    @ManyToOne
    @JoinColumn
    private Credit credit;
}
