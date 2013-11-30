package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@Accessors(fluent = true)
@EqualsAndHashCode(exclude = {"userCredentials"})
public class UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "first")
    private String first;
    @Column(name = "last")
    private String last;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birth")
    private Date birth;
    @OneToOne(mappedBy = "userDetails")
    private UserCredentials userCredentials;

    @Column(name = "passport_series")
    private String series;
    @Column(name = "passport_number")
    private int number;
    @Column(name = "passport_start")
    private Date start;
    @Column(name = "passport_end")
    private Date end;
    @Column(name = "passport_emission_organisation")
    private String emission;
    @Column(name = "passport_identification_number")
    private String identification;

    @Column(name = "work_name")
    private String name;
    @Column(name = "work_position")
    private String position;
    @Column(name = "work_income")
    private int income;

    @Column(name = "active")
    private boolean active;
}
