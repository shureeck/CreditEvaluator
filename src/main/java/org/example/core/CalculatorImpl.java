package org.example.core;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.core.entities.Response;
import org.example.core.enums.Decision;
import org.example.core.utils.LoggerMessages;

import java.util.ResourceBundle;

@Log4j2
@AllArgsConstructor
@RequiredArgsConstructor
public class CalculatorImpl implements Calculator {
    private static final ResourceBundle resources = ResourceBundle.getBundle("constants");
    private static final double minAmount = Double.parseDouble(resources.getString("minAmount"));
    private static final double maxAmount = Double.parseDouble(resources.getString("maxAmount"));

    @NonNull
    private long personalCode;
    private double amount;
    private long period;

    @Override
    public Response calculate() {
        long creditModifier = personalCode; //:TODO Should be implemented with dao
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
        //   double result = period * creditModifier / amount; // :TODO Should be removed. Checked task
        return period * creditModifier;
    }
}