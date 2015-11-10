/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author amr
 */
public class DBconnectivity {

    private static Connection con;
    private static Statement stat;
    private static String error = "";
    private static boolean connected = false;

    public static boolean Connect() {
        try {
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
            stat = con.createStatement();
            connected = true;
            
        } catch (Exception e) {
            
            error = e.getMessage();
            connected = false;
            
        }
        return connected;
    }

    public static Connection getCon() {
        return con;
    }

    public static Statement getStat() {
        return stat;
    }

    public static String getError() {
        return error;
    }

    public static boolean isConnected() {
        return connected;
    }

}
