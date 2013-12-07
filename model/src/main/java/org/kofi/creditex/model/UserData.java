package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(exclude = {"user"})
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String first;
    private String last;
    private String patronymic;
    private Date birth;
    @OneToOne(mappedBy = "userData")
    private User user;

    private String series;
    private int number;
    // start -> emissionDate
    private Date usageStart;
    // end -> outOfUse
    private Date outOfUse;
    // emission -> emissionOrganisation
    private String emissionOrganisation;
    private String identification;

    private String name;
    private String position;
    private int income;
}
