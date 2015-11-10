/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author amr
 */
public class ShortAnswerQuestion extends Question {

    private final String answer;

    public ShortAnswerQuestion(String s, String usersAnswer) {
        super(s);
        answer = usersAnswer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean checkAnswer(Object usersAnswer) {
        return getAnswer().equalsIgnoreCase((String) usersAnswer);
    }

}
