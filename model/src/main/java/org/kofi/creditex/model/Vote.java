package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id", "acceptance", "comment"})
public class Vote {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "acceptance")
    private boolean acceptance;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "application", referencedColumnName = "id", nullable = false)
    private Application application;
    @ManyToOne
    @JoinColumn(name = "manager", referencedColumnName = "id", nullable = false)
    private UserCredentials manager;
}
