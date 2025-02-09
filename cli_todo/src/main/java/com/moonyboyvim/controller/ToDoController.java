package com.moonyboyvim.controller;

import java.util.List;
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
    boolean programStatus = true;
    System.out.println("*********************************************************");
    System.out.println("                      CLI-TODO                           ");
    System.out.println("*********************************************************");
    help();
    while (programStatus) {
      System.out.print("Enter your [ CHOICE ]: ");
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
          System.out.print("Enter the [ TITLE ] of todo: ");
          String title = scan.nextLine();
          System.out.print("Enter the [ DESCRIPTION ] of todo: ");
          String description = scan.nextLine();
          repos.createTodo(title, description);
          break;
        case 3:
          System.out.print("Enter the [ ID ] of todo which you wanna see [ DETAILS ]: ");
          int id = scan.nextInt();
          scan.nextLine();
          this.getTodoByIdInfo(id);
          break;
        case 4:
          System.out.print("Enter the [ ID ] of todo which you [ EDIT/UPDATE ]: ");
          int idToEdit = scan.nextInt();
          scan.nextLine();
          System.out.print("Enter the [ NEW TITLE ] of todo: ");
          String editTitle = scan.nextLine();
          System.out.print("Enter the [ NEW DESCRIPTION ] of todo: ");
          String editDescription = scan.nextLine();
          repos.editTodoById(idToEdit, editTitle, editDescription);
          break;
        case 5:
          System.out.print("Enter the [ ID ] of todo which you wanna to [ CHANGE STATUS ]: ");
          int idToStatus = scan.nextInt();
          scan.nextLine();
          System.out.print("Enter [ NEW STATUS ] of todo: ");
          String newStatus = scan.nextLine();
          this.repos.setStatusTodoById(idToStatus, newStatus);
          break;
        case 6:
          System.out.print("Enter the [ STATUS ] of todo which you wanna to see ([d]one/in [p]rogress/[u]ndone): ");
          String st = scan.nextLine();
          this.displaySortedList(st);
          break;
        case 7:
          System.out.print("Enter the [ ID ] of todo which need to [ DELETE ]: ");
          int idToDelete = scan.nextInt();
          scan.nextLine();
          repos.removeTodoById(idToDelete);
          break;
        case 8:
          System.out.println("Bye-bye!!!");
          programStatus = false;
          break;
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
    System.out.println("* [5] - Change status of todo                         *");
    System.out.println("* [6] - Sort list by status                           *");
    System.out.println("* [7] - Remove todo by id                             *");
    System.out.println("* [8] - Quit                                          *");
    System.out.println("*******************************************************");
  }

  private void getTodoByIdInfo(int id) {
    ToDoEntity presentTodo = repos.getTodoById(id).get();
    if (presentTodo.equals(null)) {
      System.out.println("This todo doesn't exist in the list");
    } else {
      System.out.println(presentTodo.getId() + ". " + presentTodo.getTitle());
      System.out.println("*\t" + presentTodo.getDescription());
    }
  }

  private void displaySortedList(String st) {
    List<ToDoEntity> list = this.repos.sortListByStatus(st);
    if (list.equals(null) || list.size() == 0)
      System.out.printf("List with value '%s' is empty\n", st);
    for (ToDoEntity el : list)
      System.out.println(el.getId() + ". " + el.getTitle() + " -> " + el.getStatusInString());
  }
}
