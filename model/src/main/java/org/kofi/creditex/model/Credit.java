package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "start", "duration", "currentMainDebt", "currentMoney", "originalMainDebt", "currentPercentDebt"})
@ToString(of = {"id", "start", "duration", "currentMainDebt", "currentMoney", "originalMainDebt", "currentPercentDebt"})
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date start;
    private int duration;
    // sum -> currentMainDebt
    private int currentMainDebt;
    // percentSum -> fine
    private int currentPercentDebt;
    // money -> currentMoney
    private int currentMoney;
    // debt -> originalMainDebt
    private int mainFine;
    private int percentFine;
    private int originalMainDebt;
    private boolean running;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "credit")
    private List<Payment> payments;
    @OneToMany(mappedBy = "credit")
    private List<Operation> operations;
    @OneToMany(mappedBy = "credit")
    private List<ProlongationApplication> prolongationApplications;
    @OneToMany(mappedBy = "credit")
    private List<PriorRepaymentApplication> priorApplications;
}
