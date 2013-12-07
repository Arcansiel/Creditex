package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "request", "duration", "acceptance"})
@ToString(of = {"id", "request", "duration", "acceptance"})
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

    private int voteAcceptance;
    private int voteRejection;
    private boolean votingClosed;
    @OneToMany(mappedBy = "application")
    private List<Vote> votes;

    private Boolean headAcceptance;
    private String headComment;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User head;
}
