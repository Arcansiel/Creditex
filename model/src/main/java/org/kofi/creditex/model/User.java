package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Accessors
@Table(name = "usercredentials")
@EqualsAndHashCode(of = {"id", "username", "password", "enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
@ToString(of = {"id", "username", "password", "enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
public class User implements org.springframework.security.core.userdetails.UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @OneToMany(mappedBy = "client")
    private List<Application> applicationsTo;
    @OneToMany(mappedBy = "accountManager")
    private List<Application> applicationsBy;
    @OneToMany(mappedBy = "user")
    private List<Credit> credits;
    @OneToMany(mappedBy = "operator")
    private List<Operation> operations;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Authority authority;

    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>(1);
        result.add(authority);
        return result;
    }
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private UserData userData;
    @OneToMany(mappedBy = "manager")
    private List<Vote> votes;
}
