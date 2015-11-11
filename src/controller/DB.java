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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import model.MultipleChoiceQuestion;
import model.Question;
import model.Quiz;
import model.ShortAnswerQuestion;
import model.TrueFalseQuestion;

/**
 *
 * @author amr
 */
public class DB {

    private static Connection connection;
    private static Statement statement;
    private static String error = "";
    private static boolean connected = false;

    public static boolean Connect() {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
            statement = connection.createStatement();
            connected = true;

        } catch (Exception e) {

            error = e.getMessage();
            connected = false;

        }
        return connected;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static String getError() {
        return error;
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void writeToDB(QuizController qc) {
        int index = -1;
        try {
            if (!isConnected() && !Connect()) {
                return;
            }

            ResultSet r = DB.getStatement().executeQuery("select count(*) as rowcount from quizes");

            if (index < 0) {
                if (r.next()) {
                    index = r.getInt("rowcount");
                }

                if (index < 0) {
                    System.out.println("Error");
                }

                String query = "";
                query += "insert into quizes values (" + (++index) + "," + Session.getCurrent().getId() + ")";
                DB.getStatement().executeUpdate(query);
            }

            String query = "insert into questions (quiz_id, questionText, questionType,answers) values";

            for (Question q : qc.getQuiz().getQuestions()) {
                if (q instanceof MultipleChoiceQuestion) {

                    query += " (\'" + q.getQuizID() + "\', \'" + q.getQuestion()
                            + "\', " + q.getType() + ", \'" + q.getAnswer() + "\') ,";

                } else {

                    query += " (\'" + q.getQuizID() + "\', \'" + q.getText()
                            + "\', " + q.getType() + ", \'" + q.getAnswer() + "\') ,";

                }

            }

            query = query.substring(0, query.length() - 1);

            query += ";";

            DB.getStatement().executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Quiz> readFromDB() {
        int i = -1;
        try {
            if (!isConnected() && !Connect()) {
                return null;
            }

            ResultSet r = DB.getStatement().executeQuery("select count(*) as rowcount from quizes");

            if (i < 0) {
                if (r.next()) {
                    i = r.getInt("rowcount");
                }

                if (i < 0) {
                    System.out.println("Error");
                }

                QuizController.setQuizCount(i);
            }

            ArrayList<Quiz> quizList = new ArrayList<>();

            r = DB.getStatement().executeQuery("select * from quizes");

            while (r.next()) {
                quizList.add(new Quiz(r.getInt("quiz_id"), r.getInt("user_id")));
            }

            return quizList;

        } catch (Exception e) {
            return null;
        }
    }

    public static Quiz loadQuiz(int id) {

        Quiz qu = new Quiz();

        try {
            if (!isConnected() && !Connect()) {
                return null;
            }

            ResultSet r = DB.getStatement().executeQuery("select * from questions where quiz_id=" + id);

            while (r.next()) {
                String question = r.getString("questionText");
                int questionType = r.getInt("questionType");
                String answers = r.getString("answers");

                switch (questionType) {
                    case 1: //shortans
                        qu.addQuestion(new ShortAnswerQuestion(question, answers));
                        break;
                    case 2: //T or F
                        boolean answer = answers.equalsIgnoreCase("true");
                        qu.addQuestion(new TrueFalseQuestion(question, answer));
                        break;
                    case 3: //mcq
                        MultipleChoiceQuestion q = new MultipleChoiceQuestion(question);
                        String[] choices = new String[3];
                        String temp = question;
                        int newLinePos = temp.indexOf("\n");
                        temp = temp.substring(newLinePos + 1, temp.length());

                        int pos1 = temp.indexOf(",");
                        choices[0] = temp.substring(3, pos1);
                        temp = temp.substring(pos1 + 3, temp.length());

                        int pos2 = temp.indexOf(",");
                        choices[1] = temp.substring(3, pos1);
                        temp = temp.substring(pos2 + 3, temp.length());

                        choices[2] = temp.substring(3, temp.length());

                        int index = -1;

                        for (int i = 0; i < 3; i++) {
                            if (choices[i].equalsIgnoreCase(answers)) {
                                index = i;
                                break;
                            }
                        }

                        boolean[] b = new boolean[3];
                        Arrays.fill(b, false);
                        b[index] = true;

                        for (int i = 0; i < 3; i++) {
                            q.addChoice(choices[i], b[i]);
                        }

                        qu.addQuestion(q);
                }
            }

            return qu;

        } catch (Exception e) {
            return null;
        }

    }

}
