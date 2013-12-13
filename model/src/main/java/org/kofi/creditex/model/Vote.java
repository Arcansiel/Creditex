package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Голос члена кредитного комитета
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "acceptance", "comment"})
public class Vote {
    /**
     * ID голоса
     */
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Голос за или против
     * <p>true-за</p>
     * <p>false-против</p>
     */
    private boolean acceptance;
    /**
     * Комментарий члена кредитного комитета
     */
    @Size(max = 4000)
    private String comment;
    /**
     * Заявка, за которую происходит голосование {@link Application}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Application application;
    /**
     * Ссылка на члена кредитного комитета {@link User}
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User manager;
}
