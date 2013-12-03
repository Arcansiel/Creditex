package org.kofi.creditex.creditcalc.base;

//тип штрафа при досрочном погашении
public enum FineType {
    Interest,//штраф на процентную ставку
    Percents,//штраф на сумму процентов
    None//без штрафа
}
