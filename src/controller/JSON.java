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

import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import model.*;

/**
 *
 * @author amr
 */
public class JSON {

    private static JsonObject jsonObj;
    private static JsonArray users = new JsonArray();
    private static JsonArray quizes = new JsonArray();
    private static JsonArray questions = new JsonArray();

    private static final String fileName = System.getProperty("user.dir") + "/qweez.json";

    public static void writeJson(Quiz qu) {

        try {

            JSON.readJson();

            int index = quizes.size();

            jsonObj = new JsonObject();
            jsonObj.addProperty("quiz_id", ++index);
            jsonObj.addProperty("user_id", Session.getCurrent().getId());
            quizes.add(jsonObj);

            String jsonText = "{ \"quizes\":";
            jsonText += new Gson().toJson(quizes);
            jsonText += ",";

            for (Question q : qu.getQuestions()) {

                jsonObj = new JsonObject();

                jsonObj.addProperty("quiz_id", index);
                if (q instanceof MultipleChoiceQuestion) {
                    jsonObj.addProperty("questionText", q.getQuestion());
                } else {
                    jsonObj.addProperty("questionText", q.getText());
                }
                jsonObj.addProperty("questionType", q.getType());
                jsonObj.addProperty("answers", q.getAnswer());

                questions.add(jsonObj);
            }

            jsonText += "\"questions\":";
            jsonText += new Gson().toJson(questions);
            jsonText += "}";

            /*String jsonText = new Gson().toJson(questions);
             Gson gson = new GsonBuilder().setPrettyPrinting().create();
             JsonParser jp = new JsonParser();
             JsonElement je = jp.parse(jsonText);
             String prettyJsonString = gson.toJson(je);*/
            String prettyJsonString = new GsonBuilder().setPrettyPrinting().create().
                    toJson(new JsonParser().parse(jsonText));
            System.out.println(prettyJsonString);

            FileWriter writer = new FileWriter(fileName);
            writer.write(prettyJsonString);

        } catch (JsonSyntaxException | IOException e) {
            System.out.println(e);
        }

    }

    public static ArrayList<Quiz> readJson() {

        JsonParser parser = new JsonParser();
        jsonObj = new JsonObject();

        ArrayList<Quiz> quizList = new ArrayList<>();

        try {
            jsonObj = (JsonObject) parser.parse(new FileReader(fileName));

            users = (JsonArray) jsonObj.get("users");

            questions = (JsonArray) jsonObj.get("questions");

            quizes = (JsonArray) jsonObj.get("quizes");

            for (JsonElement je : quizes) {
                JsonObject jObj = je.getAsJsonObject();
                Quiz qu = new Quiz(jObj.get("quiz_id").getAsInt(), jObj.get("user_id").getAsInt());
                quizList.add(qu);
            }

            return quizList;

        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
            System.out.println(e);

        }
        return null;
    }

    public static Quiz loadQuiz(int id) {

        JsonParser parser = new JsonParser();
        Quiz qu = new Quiz();

        try {

            id += 1;

            jsonObj = (JsonObject) parser.parse(new FileReader(fileName));

            questions = (JsonArray) jsonObj.get("questions");

            for (JsonElement je : questions) {
                JsonObject jObj = je.getAsJsonObject();
                int quiz_id = jObj.get("quiz_id").getAsInt();

                if (quiz_id != id) {
                    continue;
                }

                String question = jObj.get("questionText").getAsString();
                int questionType = jObj.get("questionType").getAsInt();
                String answers = jObj.get("answers").getAsString();

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

        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
            System.out.println(e);

        }
        return null;
    }
}
