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
package controller;

import model.*;

/**
 *
 * @author amr
 */
public class QuizController {

    private static int quizCount = 0;
    private Quiz quiz = new Quiz();

    public static int getQuizCount() {
        return quizCount;
    }

    public static void setQuizCount(int quizCount) {
        QuizController.quizCount = quizCount;
    }

    public void addMCQuestion(String[] s, boolean[] b) {
        MultipleChoiceQuestion q = new MultipleChoiceQuestion(s[0]);
        q.setQuizID(quizCount);
        q.addChoice(s[1], b[0]);
        q.addChoice(s[2], b[1]);
        q.addChoice(s[3], b[2]);
        quiz.addQuestion(q);
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void addShortQuestion(String[] s) {
        ShortAnswerQuestion q = new ShortAnswerQuestion(s[0], s[1]);
        q.setQuizID(quizCount);
        quiz.addQuestion(q);
    }

    public void addTFQuestion(String s, boolean b) {
        TrueFalseQuestion q = new TrueFalseQuestion(s, b);
        q.setQuizID(quizCount);
        quiz.addQuestion(q);
    }

    public static void incrementQuizCount() {
        quizCount++;
    }

}
