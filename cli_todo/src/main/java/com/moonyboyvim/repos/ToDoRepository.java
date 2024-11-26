package com.moonyboyvim.repos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.moonyboyvim.entity.ToDoEntity;

public class ToDoRepository {
  /* Title constants */
  private final int MIN_TITLE_LENGTH = 10;
  private final int MAX_TITLE_LENGTH = 40;
  /* Description constants */
  private final int MIN_DESCRIPTION_LENGTH = 10;
  private final int MAX_DESCRIPTION_LENGTH = 100;
  private List<ToDoEntity> listOfTodos = new ArrayList<>();

  public List<ToDoEntity> getListOfTodo() {
    return this.listOfTodos;
  }

  public boolean createTodo(String title, String description) {
    boolean res = this.validation(title, description);
    if (!res) {
      return false;
    } else {
      ToDoEntity todo = new ToDoEntity(title, description);
      todo.setId(this.listOfTodos.size() + 1);
      this.listOfTodos.add(todo);
      return true;
    }
  }

  public Optional<ToDoEntity> getTodoById(int id) {
    return this.listOfTodos.stream().filter(td -> td.getId() == id).findFirst();
  }

  public boolean editTodoById(int id, String title, String description) {
    Optional<ToDoEntity> td = this.getTodoById(id);
    if (td.get().equals(null)) {
      return false;
    } else {
      boolean res = this.validation(title, description);
      ToDoEntity presentTodo = td.get();
      if (!res) {
        return false;
      } else {
        presentTodo.setTitle(title);
        presentTodo.setDescription(description);
        return true;
      }
    }
  }

  public boolean removeTodoById(int id) {
    Optional<ToDoEntity> td = this.getTodoById(id);
    if (td.get().equals(null)) {
      return false;
    } else {
      this.listOfTodos.remove(td.get());
      return true;
    }
  }

  private boolean validation(String title, String description) {
    if (title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
      return false;
    }
    if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
      return false;
    }
    return true;
  }
}
