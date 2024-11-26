package com.moonyboyvim;

import com.moonyboyvim.controller.ToDoController;

public class App {
    public static void main(String[] args) {
        ToDoController controller = new ToDoController();
        controller.start();
    }
}
