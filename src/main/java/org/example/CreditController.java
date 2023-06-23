package org.example;

import lombok.AllArgsConstructor;
import org.example.core.CalculatorImpl;
import org.example.core.entities.Request;
import org.example.core.entities.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CreditController {
    private final CalculatorImpl calculator;

    @GetMapping("/evaluator")
    public String showPage(@ModelAttribute Request request) {
        return "evaluator";
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "redirect:evaluator";
    }


    @PostMapping("/decision")
    public String showDecision(@ModelAttribute("request") Request request, Model model) {
        initCalculator(request);
        try {
            Response response = calculator.calculate();
            model.addAttribute("response", response);
            return "decision";
        } catch (RuntimeException exception) {
            model.addAttribute("message", exception.getMessage());
            return "error";
        }
    }

    private void initCalculator(Request request) {
        calculator.setPersonalCode(request.getPersonalCode());
        calculator.setPeriod(request.getPeriod());
        calculator.setAmount(request.getAmount());
    }
}
