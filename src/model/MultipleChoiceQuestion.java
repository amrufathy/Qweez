/*
 * The MIT License
 *
 * Copyright 2015 amr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        this.type = 3;
    }

    public void addChoice(String choice, boolean type) {
        choices.add(choice);
        if (type) {
            index = choices.size() - 1;
        }
    }

    public String getChoice(int index) {
        return choices.get(index);
    }

    @Override
    public String getQuestion() {
        String answers = "\n";
        char[] alphabet = {'a', 'b', 'c'};
        int i = 0;
        for (String s : choices) {
            answers += alphabet[i++] + ") " + s + ",  ";
        }
        answers = answers.substring(0, answers.length() - 3);
        return question + answers;
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
