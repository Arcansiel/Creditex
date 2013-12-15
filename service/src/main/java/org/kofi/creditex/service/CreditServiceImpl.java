package org.kofi.creditex.service;

import com.google.common.base.Function;
import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.Payment;
import org.kofi.creditex.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с кредитами
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService{
    @Autowired
    private CreditRepository creditRepository;

    /**
     * Получить кредит по ID
     * @param id Значение Id требуемого кредита
     * @return Кредит
     */
    @Override
    public Credit GetCreditById(long id) {
        return creditRepository.findOne(id);
    }

    @Override
    public List<Credit> findByUsername(String username) {
        return creditRepository.findByUserUsername(username);
    }

    @Override
    public Credit findByUsernameAndRunning(String username, boolean running) {
        return creditRepository.findByRunningAndUserUsername(running, username);
    }
}
