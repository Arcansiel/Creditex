package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 07.12.13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@Accessors
@EqualsAndHashCode(of = {"id", "request", "duration", "acceptance"})
public class PriorRepaymentApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn
    private User client;
    @ManyToOne
    @JoinColumn
    private User accountManager;
    @ManyToOne
    @JoinColumn
    private Credit credit;
}
