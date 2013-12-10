package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;

/**
 * Данные пользователя
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class UserData {
    /**
     * ID данных пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Имя
     */
    @Column(nullable = false)
    private String first;
    /**
     * Фамилия
     */
    @Column(nullable = false)
    private String last;
    /**
     * Отчество
     */
    @Column(nullable = false)
    private String patronymic;
    /**
     * Ссылка на пользователя {@link User}
     */
    @OneToOne(mappedBy = "userData")
    private User user;
    /**
     * Серия паспорта
     */
    @Column(nullable = false)
    private String passportSeries;
    /**
     * Номер паспорта
     */
    private int passportNumber;
    /**
     * Место работы
     */
    @Column(nullable = false)
    private String workName;
    /**
     * Занимаемая позиция
     */
    @Column(nullable = false)
    private String workPosition;
    /**
     * Доход
     */
    private int workIncome;
}
