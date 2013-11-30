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
@EqualsAndHashCode(of = {"id", "authority"})
@Accessors(fluent = true)
public class Authorities {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "authority")
    private String authority;
    @OneToMany(mappedBy = "authorities")
    private List<UserCredentials> userCredentials;
}
