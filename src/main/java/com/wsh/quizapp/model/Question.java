package com.wsh.quizapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @NonNull
    private String rightAnswer;

    @NonNull
    private String difficultyLevel;

    @NonNull
    private String category;

    protected static final Map<DifficultyLevel, Integer> PUNCTUATION = Map.of(
        DifficultyLevel.EASY, 1,
        DifficultyLevel.MEDIUM, 2,
        DifficultyLevel.HARD, 3
    );

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Question question = (Question) o;
        return getId() != null && Objects.equals(getId(), question.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public int getPoints() {
        return getPoints(DifficultyLevel.valueOf(difficultyLevel.toUpperCase()));
    }

    public static int getPoints(DifficultyLevel difficultyLevel) {
        return PUNCTUATION.get(difficultyLevel);
    }
}
