package org.example;

import org.example.core.CalculatorImpl;
import org.example.core.entities.Request;
import org.example.core.utils.CreditUtils;

public class Main {
    public static void main(String... args) {
        String json = "{\n" +
                "    \"id\": \"49002010976\",\n" +
                "    \"amount\": 5000,\n" +
                "    \"period\": 30\n" +
                "\n" +
                "}";
        CalculatorImpl calculator = new CalculatorImpl(300,11000, 60);
        calculator.calculate();
        Request e = CreditUtils.readJson(json, Request.class);
        int i = 0;
    }
}
