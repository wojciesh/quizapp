package com.wsh.quizapp.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class QuestionWrapper {

        @NonNull
        private Integer id;

        @NonNull
        private String questionTitle;

        @NonNull
        private String option1;

        @NonNull
        private String option2;

        @NonNull
        private String option3;

        @NonNull
        private String option4;

        public static List<QuestionWrapper> wrapQuestions(List<Question> questions) {
                return questions.stream().map(
                        question -> new QuestionWrapper(
                                question.getId(),
                                question.getQuestionTitle(),
                                question.getOption1(),
                                question.getOption2(),
                                question.getOption3(),
                                question.getOption4())).toList();
        }
}
