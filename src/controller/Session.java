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

import model.User;
import model.UserType;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 *
 * @author amr
 */
public class Session {

    private static User current = null;

    public static String register(String username, char[] password) {

        if (!DB.isConnected() && !DB.Connect()) {
            return "Error in connecting to database !";
        }

        try {

            String query = "insert into users (username, password, type) values (\'" + username + "\', " + Arrays.hashCode(password) + ", " + UserType.Student.getValue() + ");";
            DB.getStat().executeUpdate(query);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();

        }

        return null;
    }

    public static String login(String username, char[] password) {

        if (!DB.isConnected() && !DB.Connect()) {
            return "Error in connecting to database !";
        }

        try {

            String query = "select * from users where username=\'" + username + "\'";
            ResultSet res = DB.getStat().executeQuery(query);

            if (res.next()) {
                if (res.getInt("password") != Arrays.hashCode(password)) {
                    return "wrong password";
                }
            }

            current = new User(res.getInt("user_id"), UserType.values()[res.getInt("type")], res.getString("username"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();

        }

        return null;
    }

    public static void logout() {
        current = null;
    }

    public static boolean isLogged() {
        return null != current;
    }

    public static User getCurrent() {
        return current;
    }
}
