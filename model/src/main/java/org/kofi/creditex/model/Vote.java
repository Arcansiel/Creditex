package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

@Entity
@Data
@Accessors
@EqualsAndHashCode(of = {"id", "acceptance", "comment"})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean acceptance;
    private String comment;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Application application;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User manager;
}
