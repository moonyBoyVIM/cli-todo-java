package com.moonyboyvim;

import com.moonyboyvim.controller.ToDoController;

public class App {
    public static void main(String[] args) {
        ToDoController controller = new ToDoController();
        try {
            controller.start();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again...");
        }
    }
}
