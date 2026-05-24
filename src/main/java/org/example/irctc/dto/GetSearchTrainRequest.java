package org.example.irctc.dto;


import lombok.Getter;
import lombok.Setter;

public class GetSearchTrainRequest {
    @Getter @Setter
    private String sourceStation;
    @Getter @Setter
    private String destinationStation;
}
