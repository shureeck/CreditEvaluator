package org.example.core;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.example.core.dao.Dao;
import org.example.core.entities.Response;
import org.example.core.enums.Decision;
import org.example.core.utils.LoggerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Log4j2
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class CalculatorImpl implements Calculator, Validator {
    private static final ResourceBundle resources = ResourceBundle.getBundle("constants");
    private static final double minAmount = Double.parseDouble(resources.getString("minAmount"));
    private static final double maxAmount = Double.parseDouble(resources.getString("maxAmount"));
    private static final long minPeriod = Long.parseLong(resources.getString("minPeriod"));
    private static final long maxPeriod = Long.parseLong(resources.getString("maxPeriod"));

    @Setter
    private long personalCode;
    @Setter
    private double amount;
    @Setter
    private long period;
    @NonNull
    private Dao segmentDao;

    @Override
    public Response calculate() {
        validate();
        long creditModifier = getCreditModifier();
        if (creditModifier == 0) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.debt"));
            return new Response(Decision.DECLINED, 0, period);
        }
        log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.input",
                String.valueOf(period), String.valueOf(amount), String.valueOf(personalCode)));
        double maxSumm = getMaxSum(creditModifier);
        if (maxSumm < minAmount) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.period"));
            return getAlternativePeriod(creditModifier);
        } else if (maxSumm <= maxAmount) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(maxSumm), Decision.APPROVED.name(), String.valueOf(period)));
            return new Response(Decision.APPROVED, maxSumm, period);
        } else {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(maxAmount), Decision.APPROVED.name(), String.valueOf(period)));
            return new Response(Decision.APPROVED, maxAmount, period);
        }
    }

    private Response getAlternativePeriod(long creditModifier) {
        long alternativePeriod = (long) (amount / creditModifier);
        if (alternativePeriod >= minPeriod) {
            if (alternativePeriod > maxPeriod) {
                period = maxPeriod;
                return calculate();
            }
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(amount), Decision.APPROVED.name(), String.valueOf(alternativePeriod)));
            return new Response(Decision.APPROVED, amount, alternativePeriod);
        }
        alternativePeriod = (long) (minAmount / creditModifier);
        if (alternativePeriod >= minPeriod) {
            log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                    String.valueOf(minAmount), Decision.APPROVED.name(), String.valueOf(alternativePeriod)));
            return new Response(Decision.APPROVED, minAmount, alternativePeriod);
        }
        log.info(LoggerMessages.getMessage("CalculatorImpl.calculate.decision",
                String.valueOf(0), Decision.DECLINED.name(), String.valueOf(period)));
        return new Response(Decision.DECLINED, 0, period);
    }

    private double getMaxSum(long creditModifier) {
        return period * creditModifier;
    }

    private long getCreditModifier() {
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