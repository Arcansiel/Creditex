package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Голос за или против
     * <p>true-за</p>
     * <p>false-против</p>
     */
    private boolean acceptance;
    /**
     * Комментарий члена кредитного комитета
     */
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
