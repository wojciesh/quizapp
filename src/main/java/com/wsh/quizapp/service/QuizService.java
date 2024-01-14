package com.wsh.quizapp.service;

import com.wsh.quizapp.dao.QuestionDao;
import com.wsh.quizapp.dao.QuizDao;
import com.wsh.quizapp.model.Question;
import com.wsh.quizapp.model.QuestionWrapper;
import com.wsh.quizapp.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizDao quizDao;
    private final QuestionDao questionDao;

    public QuizService(QuizDao quizDao, QuestionDao questionDao) {
        this.quizDao = quizDao;
        this.questionDao = questionDao;
    }

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        try {
            Quiz quiz = new Quiz();
            quiz.setCategory(category);
            quiz.setTitle(title);
            quiz.setQuestions(questionDao.getRandomQuestionsByCategory(category, numQ));

            quizDao.save(quiz);

            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            return quizDao
                    .findById(id)
                    .map(value -> new ResponseEntity<>(QuestionWrapper.wrapQuestions(value.getQuestions()), HttpStatus.OK))
                    .orElseThrow();
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}
