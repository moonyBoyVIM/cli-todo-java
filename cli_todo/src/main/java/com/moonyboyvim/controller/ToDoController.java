package com.moonyboyvim.controller;

import java.util.Scanner;

import com.moonyboyvim.repos.ToDoRepository;

public class ToDoController {
  ToDoRepository repos;

  public ToDoController() {
    this.repos = new ToDoRepository();
  }

  public void start() {
    System.out.println("*********************************************************");
    System.out.println("                      CLI-TODO                           ");
    System.out.println("*********************************************************");
    Scanner scan = new Scanner(System.in);
    for (;;) {
      System.out.print("Enter your choice: ");
      int choice = scan.nextInt();
      switch (choice) {
        case 1:
          repos.getListOfTodo();
          break;
        case 2:
          System.out.print("Enter the title of todo: ");
          String title = scan.nextLine();
          System.out.print("Enter the description of todo: ");
          String description = scan.nextLine();
          repos.createTodo(title, description);
          break;
        default:
          break;
      }
      scan.close();
    }
  }

  private void createTodoInfo(String title, String description) {

  }
}
