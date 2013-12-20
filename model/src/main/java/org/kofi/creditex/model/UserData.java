package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.annotation.RegEx;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Имя
     */
    @Size(max = 46)
    @Pattern(regexp = "^\\w+")
    @Column(nullable = false)
    private String first;
    /**
     * Фамилия
     */
    @Size(max = 46)
    @Pattern(regexp = "^\\w+")
    @Column(nullable = false)
    private String last;
    /**
     * Отчество
     */
    @Size(max = 46)
    @Pattern(regexp = "^\\w+")
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
    @Size(max = 2, min = 2)
    @Pattern(regexp = "[A-Z]{2}")
    @Column(nullable = false)
    private String passportSeries;
    /**
     * Номер паспорта
     */
    @Min(0)
    @Max(9999999)
    private int passportNumber;
    /**
     * Место работы
     */
    @Size(max = 46)
    @Pattern(regexp = "^\\w+")
    @Column(nullable = false)
    private String workName;
    /**
     * Занимаемая позиция
     */
    @Size(max = 46)
    @Pattern(regexp = "^\\w+")
    @Column(nullable = false)
    private String workPosition;
    /**
     * Доход
     */
    @Min(0)
    private long workIncome;
}
