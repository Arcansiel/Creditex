package org.kofi.creditex.model;

/**
 * Возможно ли предварительное возвращение кредита
 */
public enum PriorRepayment {
    /**
     * Невозможно
     */
    NotAvailable("Недоступно"),
    /**
     * Возможно без штрафа
     */
    Available("Доступно"),
    /**
     * Возможно с увеличением процентной в качестве штрафа
     */
    AvailableFineInterest("Доступно со штрафом в виде прибавки к процентной ставке"),
    /**
     * Возможно с штрафом на оставшуюся невыплаченную дополнительную сумму долга
     */
    AvailableFinePercentSum("Доступно со штрафом в виде выплаты части оставшегося процентного долга");

    private final String text;

    private PriorRepayment(final String text){
        this.text = text;
    }


    @Override
    public String toString() {
        return text;
    }
}
