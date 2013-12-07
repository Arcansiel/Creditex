package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "comment"})
@ToString(of = {"id", "comment"})
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
