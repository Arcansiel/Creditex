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
@Accessors(fluent = true)
@EqualsAndHashCode(of = {"id", "username", "password", "enabled"})
public class UserCredentials {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(mappedBy = "client")
    private List<Application> applicationsTo;
    @OneToMany(mappedBy = "accountManager")
    private List<Application> applicationsBy;
    @OneToMany(mappedBy = "userCredentials")
    private List<Credit> credits;
    @OneToMany(mappedBy = "operator")
    private List<Operation> operations;
    @ManyToOne
    @JoinColumn(name = "authority", referencedColumnName = "id", nullable = false)
    private Authorities authorities;
    @OneToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
    @OneToMany(mappedBy = "manager")
    private List<Vote> votes;
}
