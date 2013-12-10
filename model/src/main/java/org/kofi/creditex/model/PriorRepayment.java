package org.kofi.creditex.model;

/**
 * Возможно ли предварительное возвращение кредита
 */
public enum PriorRepayment {
    /**
     * Невозможно
     */
    NotAvailable,
    /**
     * Возможно без штрафа
     */
    Available,
    /**
     * Возможно с увеличением процентной в качестве штрафа
     */
    AvailableFineInterest,
    /**
     * Возможно с штрафом на оставшуюся невыплаченную дополнительную сумму долга
     */
    AvailableFinePercentSum
}
