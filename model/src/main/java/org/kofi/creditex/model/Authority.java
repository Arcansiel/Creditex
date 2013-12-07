package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors
@EqualsAndHashCode(of = {"id", "authority"})
@ToString(of = {"id", "authority"})
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String authority;

    @OneToMany(mappedBy = "authority")
    private List<User> userCredentials;
}
