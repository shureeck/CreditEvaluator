package org.example.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.core.enums.Decision;

@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Getter
    @Setter
    private Decision decision;
    @Getter
    @Setter
    private double amount;
}
