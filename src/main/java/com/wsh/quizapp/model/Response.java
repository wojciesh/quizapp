package com.wsh.quizapp.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {

    @NonNull
    private Integer id;

    @NonNull
    private String answer;

}
