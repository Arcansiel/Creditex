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

/**
 * Права и роль пользователя
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "authority"})
@ToString(of = {"id", "authority"})
public class Authority implements GrantedAuthority {
    /**
     * ID роли пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Текстовое представление роли
     */
    private String authority;
    /**
     * Все пользователи с данной ролью {@link User}
     */
    @OneToMany(mappedBy = "authority")
    private List<User> userCredentials;
}
