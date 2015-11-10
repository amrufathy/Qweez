/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author amr
 */
public class MultipleChoiceQuestion extends Question {

    private final ArrayList<String> choices;
    private int index;

    public MultipleChoiceQuestion(String s) {
        super(s);
        choices = new ArrayList<>();
        index = -1;
    }

    public void addChoice(String choice, boolean type) {
        choices.add(choice);
        if (type) {
            index = choices.size() - 1;
        }
    }

    @Override
    public String getQuestion() {
        String answers = "";
        int i = 0;
        for (String s : choices) {
            answers += (i++) + ")" + s + ", ";
        }
        answers = answers.substring(0, answers.length() - 2);
        return answers;
    }

    @Override
    public String getAnswer() {
        return choices.get(index);
    }

    @Override
    public boolean checkAnswer(Object usersAnswer) {
        return getAnswer().equalsIgnoreCase((String) usersAnswer);
    }

}
