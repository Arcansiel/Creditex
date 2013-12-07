package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Accessors
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String first;
    @Column(nullable = false)
    private String last;
    @Column(nullable = false)
    private String patronymic;
//    private Date birth;
    @OneToOne(mappedBy = "userData")
    private User user;
    @Column(nullable = false)
    private String passportSeries;
    private int passportNumber;
//    // start -> emissionDate
//    private Date usageStart;
//    // end -> outOfUse
//    private Date outOfUse;
//    // emission -> emissionOrganisation
//    private String emissionOrganisation;
//    private String identification;
    @Column(nullable = false)
    private String workName;
    @Column(nullable = false)
    private String workPosition;
    private int workIncome;
}
