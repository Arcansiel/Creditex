package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@EqualsAndHashCode(of = {"id", "request", "duration", "acceptance"})
@Accessors(fluent = true)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int request;
    private int duration;
    private Boolean acceptance;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User client;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User accountManager;

    private Boolean securityAcceptance;
    private String securityComment;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User security;

    private Boolean committeeAcceptance;
    private String committeeComment;

    @OneToMany(mappedBy = "application")
    private List<Vote> votes;

    private Boolean headAcceptance;
    private String headComment;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User head;
}
