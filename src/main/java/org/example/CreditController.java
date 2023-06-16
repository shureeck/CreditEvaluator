package org.example;

import org.example.core.Calculator;
import org.example.core.CalculatorImpl;
import org.example.core.entities.Request;
import org.example.core.entities.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreditController {
    @GetMapping("/evaluator")
    public String showPage(@ModelAttribute Request request) {
        return "evaluator";
    }

    @PostMapping("/decision")
    public String showDecision(@ModelAttribute("request") Request request, Model model) {
        long personalCode = request.getPersonalCode();
        double amount = request.getAmount();
        long period = request.getPeriod();
        Calculator calculator = new CalculatorImpl(personalCode, amount, period);
        Response response = calculator.calculate();
        model.addAttribute("response", response);

        return "decision";

    }
}
