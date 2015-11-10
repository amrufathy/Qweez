/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question;

/**
 *
 * @author amr
 */
public class TrueFalseQuestion extends Question {

    private final boolean answer;
    private static final String s = "\nTrue or False ?";

    public TrueFalseQuestion(String s, boolean usersAnswer) {
        super(s);
        answer = usersAnswer;
    }

    @Override
    public String getQuestion() {
        return question + s;

    }

    @Override
    public String getAnswer() {
        return Boolean.toString(answer);
    }

    @Override
    public boolean checkAnswer(Object usersAnswer) {
        return (Boolean) usersAnswer == answer;
    }

}
