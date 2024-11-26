package com.moonyboyvim.repos;

public class ToDoRepositoryError {
  private String title;
  private String message;

  public ToDoRepositoryError(String title, String message) {
    this.title = title;
    this.message = message;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

}
