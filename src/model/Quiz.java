/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import model.Question;

/**
 *
 * @author amr
 */
public class Quiz {

    private ArrayList<Question> questions;
    private String name;
    private int score;

    public Quiz(String name) {
        this.name = name;
        this.score = 0;
        questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

}
