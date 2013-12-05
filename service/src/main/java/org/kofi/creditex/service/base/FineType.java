package org.kofi.creditex.service.base;

//тип штрафа при досрочном погашении
public enum FineType {
    Interest,//штраф на процентную ставку
    Percents,//штраф на сумму процентов
    None//без штрафа
}
