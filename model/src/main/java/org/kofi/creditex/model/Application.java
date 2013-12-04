package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.val;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Data
@Builder
@EqualsAndHashCode(of = {"id", "request", "duration", "acceptance"})
@Accessors(fluent = true)
public class Application {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "request")
    private int request;
    @Column(name = "duration")
    private int duration;
    @Column(name = "acceptance")
    private Boolean acceptance;
    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id", nullable = false)
    private UserCredentials client;
    @ManyToOne
    @JoinColumn(name = "account_manager", referencedColumnName = "id", nullable = false)
    private UserCredentials accountManager;
    @Column(name = "security_acceptance")
    private Boolean securityAcceptance;
    @Column(name = "security_comment")
    private String securityComment;
    @ManyToOne
    @JoinColumn(name = "security", referencedColumnName = "id", nullable = false)
    private UserCredentials security;
    @Column(name = "committee_acceptance")
    private Boolean committee_acceptance;
    @Column(name = "committee_comment")
    private String committeeComment;
    @OneToMany(mappedBy = "application")
    private List<Vote> votes;
    @Column(name = "head_acceptance")
    private Boolean headAcceptance;
    @Column(name = "head_comment")
    private String headComment;
    @ManyToOne
    @JoinColumn(name = "head", referencedColumnName = "id", nullable = false)
    private UserCredentials head;
}
