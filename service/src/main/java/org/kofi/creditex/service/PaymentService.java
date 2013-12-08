package org.kofi.creditex.service;

import org.kofi.creditex.model.Payment;
import org.kofi.creditex.web.model.PaymentTableForm;

import java.util.List;

public interface PaymentService {
    List<Payment> GetPaymentsByCreditId(int creditId);
    List<PaymentTableForm> GetPaymentTableFormsByCreditId(int creditId);
}
