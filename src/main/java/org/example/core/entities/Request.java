package org.example.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    @Setter
    @Getter
    @JsonProperty("id")
    long personalCode;
    @Setter
    @Getter
    @JsonProperty("amount")
    double amount;
    @Setter
    @Getter
    @JsonProperty("period")
    long period;

}
