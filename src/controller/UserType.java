/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * Just a complicated enumerator in Java
 *
 * @author amr
 */
public enum UserType {

    Student(1), Instructor(0);

    private final int value;

    private UserType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
