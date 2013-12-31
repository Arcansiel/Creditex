package org.kofi.creditex.repository;

import org.kofi.creditex.model.Payment;

import java.sql.Date;
import java.util.List;

public interface PaymentRepositoryExt {
    List<Payment> findReadyToPayBills(Date currentDate);
    List<Payment> findReadyToPayFine();
}
