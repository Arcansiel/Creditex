package org.kofi.creditex.model;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Entity
public class Notification {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Size(max=4000)
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private boolean viewed;
    @ManyToOne
    private User client;
    @ManyToOne
    private User security;
    @ManyToOne
    private Credit credit;
}
