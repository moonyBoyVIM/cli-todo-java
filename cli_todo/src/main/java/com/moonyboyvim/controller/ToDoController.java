package com.moonyboyvim.controller;

import java.util.Scanner;

import com.moonyboyvim.entity.ToDoEntity;
import com.moonyboyvim.repos.ToDoRepository;

public class ToDoController {
  ToDoRepository repos;
  Scanner scan;

  public ToDoController() {
    this.repos = new ToDoRepository();
    this.scan = new Scanner(System.in);
  }

  public void start() {
    System.out.println("*********************************************************");
    System.out.println("                      CLI-TODO                           ");
    System.out.println("*********************************************************");
    help();
    for (;;) {
      System.out.print("Enter your choice: ");
      int choice = scan.nextInt();
      scan.nextLine();
      switch (choice) {
        case 0:
          help();
          break;
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
        case 3:
          System.out.print("Enter the id of todo which you wanna see details: ");
          int id = scan.nextInt();
          scan.nextLine();
          this.getTodoByIdInfo(id);
          break;
        case 4:
          System.out.print("Enter the id of todo which you edit/update: ");
          int idToEdit = scan.nextInt();
          scan.nextLine();
          System.out.print("Enter the new title of todo: ");
          String editTitle = scan.nextLine();
          System.out.print("Enter the new description of todo: ");
          String editDescription = scan.nextLine();
          repos.editTodoById(idToEdit, editTitle, editDescription);
          break;
        case 5:
          System.out.print("Enter the id of todo which need to delete: ");
          int idToDelete = scan.nextInt();
          scan.nextLine();
          repos.removeTodoById(idToDelete);
        default:
          System.out.println("Invalid input please try again");
          break;
      }
    }
  }

  private void help() {
    System.out.println("*******************************************************");
    System.out.println("* [0] - Tutorial                                      *");
    System.out.println("* [1] - Get list of all todos                         *");
    System.out.println("* [2] - Create new todo                               *");
    System.out.println("* [3] - Get todo by id                                *");
    System.out.println("* [4] - Edit/Update todo by id                        *");
    System.out.println("* [5] - Remove todo by id                             *");
    System.out.println("*******************************************************");
  }

  private void getTodoByIdInfo(int id) {
    ToDoEntity presentTodo = repos.getTodoById(id).get();
    if (presentTodo.equals(null)) {
      System.out.println("This todo doesn't exist in the list");
    } else {
      System.out.println(presentTodo.getId() + ": " + presentTodo.getTitle());
      System.out.println(presentTodo.getDescription());
    }
  }
}
