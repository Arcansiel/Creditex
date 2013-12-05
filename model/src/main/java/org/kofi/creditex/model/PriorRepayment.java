package org.kofi.creditex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.util.List;

public enum PriorRepayment {
    NotAvailable,
    Available,
    AvailableFineInterest,
    AvailableFinePercentSum
}
