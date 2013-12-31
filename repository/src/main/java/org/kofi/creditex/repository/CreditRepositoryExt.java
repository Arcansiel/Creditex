package org.kofi.creditex.repository;

import org.kofi.creditex.model.Credit;

import java.sql.Date;
import java.util.List;

public interface CreditRepositoryExt {
    List<Credit> findCreditsToClose(Date currentDate);
}
