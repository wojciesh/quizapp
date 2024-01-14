package com.wsh.quizapp.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Score {

    @NonNull
    private Integer questionId;

    @NonNull
    private Integer points;

    @NonNull
    private Integer maxPoints;

    public Double getPercentage() {
        return (double) points / (double) maxPoints * 100.0;
    }

}
