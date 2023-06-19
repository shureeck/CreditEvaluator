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
    Long personalCode;
    @Setter
    @Getter
    @JsonProperty("amount")
    Double amount;
    @Setter
    @Getter
    @JsonProperty("period")
    Long period;

}
