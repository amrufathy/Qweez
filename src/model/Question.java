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
public abstract class Question {

    protected String question;

    public Question(String s) {
        question = s;
    }

    public abstract String getQuestion();

    public abstract String getAnswer();

    /**
     * Function to check correctness of user's answer
     *
     * @param usersAnswer
     * @return true if answer matches
     */
    public abstract boolean checkAnswer(Object usersAnswer);

}
