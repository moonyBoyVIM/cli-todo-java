package com.moonyboyvim.entity;

/**
 * Class of Todo entity which reporesents
 * main properties of this entity
 */
public class ToDoEntity {
  private int id;
  private String title;
  private String description;
  private int status;

  public ToDoEntity(String title, String description) {
    this.id = 0;
    this.title = title;
    this.description = description;
    this.status = 0;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * This method return a String object
   * which represents digital value of
   * todo status
   */
  public String getStatusInString() {
    String statusString = "";
    if (this.status == 0)
      statusString = "Undone";
    else if (this.status == 1)
      statusString = "In progress";
    else if (this.status == 2)
      statusString = "Done";
    return statusString;
  }
}
