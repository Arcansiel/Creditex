package org.kofi.creditex.service;

import org.kofi.creditex.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> GetPaymentsByCreditId(int creditId);
}
