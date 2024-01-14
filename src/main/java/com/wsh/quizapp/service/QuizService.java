package com.wsh.quizapp.service;

import com.wsh.quizapp.dao.QuestionDao;
import com.wsh.quizapp.dao.QuizDao;
import com.wsh.quizapp.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            Quiz quiz = new Quiz(category, title, questionDao.getRandomQuestionsByCategory(category, numQ));
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

    public ResponseEntity<Score> calculateResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizDao.findById(id).orElseThrow();
            int points = quiz.getQuestions().stream().reduce(0,  // calculate score from all questions in the quiz
                        (acc, question) -> acc +
                                (question.getRightAnswer().equals(  // check if the right answer matches the User's answer
                                    responses.stream().filter(r -> r.getId().equals(question.getId()))  // get the response for this question
                                    .map(Response::getAnswer)       // get User's answer from the response
                                    .findFirst()                    // return answer if response was present
                                    .orElse(null))            // or null if not present
                                ? question.getPoints()              // if answer matches add points for this question
                                : 0),                               // else add 0 points
                        Integer::sum);
            // Streams are lazy,
            // so filter and map will not be executed for all questions,
            // only for the first one that matches (because of findFirst()).

            Score score = new Score(id, points, quiz.getTotalPoints());

            System.out.printf("Points: %d / %d%n", score.getPoints(), score.getMaxPoints());
            System.out.printf("Percentage: %.2f%%%n", score.getPercentage());

            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
