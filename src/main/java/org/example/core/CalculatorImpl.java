package org.example.core;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.core.dao.Dao;
import org.example.core.dao.SegmentsDao;
import org.example.core.entities.Response;
import org.example.core.enums.Decision;
import org.example.core.utils.LoggerMessages;

import java.util.ResourceBundle;

@Log4j2
@AllArgsConstructor
@RequiredArgsConstructor
public class CalculatorImpl implements Calculator, Validator {
    private static final ResourceBundle resources = ResourceBundle.getBundle("constants");
    private static final double minAmount = Double.parseDouble(resources.getString("minAmount"));
    private static final double maxAmount = Double.parseDouble(resources.getString("maxAmount"));
    private static final double minPeriod = Double.parseDouble(resources.getString("minPeriod"));
    private static final double maxPeriod = Double.parseDouble(resources.getString("maxPeriod"));

    @NonNull
    private long personalCode;
    private double amount;
    private long period;

    @Override
    public Response calculate() {
        validate();
        long creditModifier = getCreditModifier();
        log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.input",
                String.valueOf(period), String.valueOf(amount), String.valueOf(personalCode)));
        double maxSumm = getMaxSum(creditModifier);
        if (maxSumm < minAmount) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    "0", Decision.DECLINED.name()));
            return new Response(Decision.DECLINED, 0);
        } else if (maxSumm <= maxAmount) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(maxSumm), Decision.APPROVED.name()));
            return new Response(Decision.APPROVED, maxSumm);
        } else {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(maxAmount), Decision.APPROVED.name()));
            return new Response(Decision.APPROVED, maxAmount);
        }
    }

    private double getMaxSum(long creditModifier) {
        return period * creditModifier;
    }

    private long getCreditModifier() {
        Dao segmentDao = new SegmentsDao();
        return segmentDao.getCreditModifier(personalCode);

    }

    @Override
    public boolean validate() throws RuntimeException {
        if (period < minPeriod || period > maxPeriod) {
            log.error(LoggerMessages.getMessage("CalculatorImpl.validate.period",
                    String.valueOf(minPeriod), String.valueOf(maxPeriod)));
            throw new RuntimeException(LoggerMessages.getMessage("CalculatorImpl.validate.period",
                    String.valueOf(minPeriod), String.valueOf(maxPeriod)));
        }
        if (amount < minAmount || amount > maxAmount) {
            log.error(LoggerMessages.getMessage("CalculatorImpl.validate.amount",
                    String.valueOf(minAmount), String.valueOf(maxAmount)));
            throw new RuntimeException(LoggerMessages.getMessage("CalculatorImpl.validate.amount",
                    String.valueOf(minAmount), String.valueOf(maxAmount)));
        }
        return true;
    }
}