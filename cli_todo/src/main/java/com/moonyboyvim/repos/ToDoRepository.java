package com.moonyboyvim.repos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.moonyboyvim.entity.ToDoEntity;

public class ToDoRepository {
  private class Tuple<T, E> {
    private T first;
    private E second;

    public Tuple(T first, E second) {
      this.first = first;
      this.second = second;
    }

    public T getFirst() {
      return first;
    }

    public E getSecond() {
      return second;
    }

  }

  /* Title constants */
  private final int MIN_TITLE_LENGTH = 5;
  private final int MAX_TITLE_LENGTH = 40;
  /* Description constants */
  private final int MIN_DESCRIPTION_LENGTH = 10;
  private final int MAX_DESCRIPTION_LENGTH = 100;
  /* Local storage of todos */
  private List<ToDoEntity> listOfTodos = new ArrayList<>();

  public void getListOfTodo() {
    if (this.listOfTodos.equals(null) || this.listOfTodos.size() == 0)
      System.out.println("Your list is empty now. Create your todo!");
    this.listOfTodos
        .forEach(td -> System.out.println(td.getId() + ". " + td.getTitle() + " -> " + td.getStatusInString()));
  }

  public void createTodo(String title, String description) {
    Tuple<List<ToDoRepositoryError>, Boolean> tuple = this.validation(title, description);
    if (!tuple.getSecond()) {
      List<ToDoRepositoryError> list = tuple.getFirst();
      for (ToDoRepositoryError tre : list)
        System.out.println(tre.getTitle() + ": " + tre.getMessage());
    } else {
      ToDoEntity todo = new ToDoEntity(title, description);
      todo.setId(this.listOfTodos.size() + 1);
      this.listOfTodos.add(todo);
      System.out.println("Todo added to list succesfully");
    }
  }

  public Optional<ToDoEntity> getTodoById(int id) {
    return this.listOfTodos.stream().filter(td -> td.getId() == id).findFirst();
  }

  public void editTodoById(int id, String title, String description) {
    Optional<ToDoEntity> td = this.getTodoById(id);
    if (td.get().equals(null)) {
      System.out.println("Todo is not present in the list");
    } else {
      Tuple<List<ToDoRepositoryError>, Boolean> tuple = this.validation(title, description);
      ToDoEntity presentTodo = td.get();
      if (!tuple.getSecond()) {
        List<ToDoRepositoryError> list = tuple.getFirst();
        for (ToDoRepositoryError tre : list)
          System.out.println(tre.getTitle() + ": " + tre.getMessage());
      } else {
        presentTodo.setTitle(title);
        presentTodo.setDescription(description);
        System.out.println("Todo edited/updated succesfully");
      }
    }
  }

  public void removeTodoById(int id) {
    Optional<ToDoEntity> td = this.getTodoById(id);
    if (td.get().equals(null)) {
      System.out.println("Todo is not present in the list");
    } else {
      this.listOfTodos.remove(td.get());
      System.out.println("Todo was removed succesfully from the list");
    }
  }

  public void setStatusTodoById(int id, String st) {
    Map<String, Integer> status = new HashMap<>(Map.of("u", 0, "p", 1, "d", 2));
    if (!status.containsKey(st)) {
      System.out.println("Invalid input. Please try again...");
    } else {
      Optional<ToDoEntity> presentTodo = this.getTodoById(id);
      if (presentTodo.get().equals(null)) {
        System.out.println("This todo is not present in the list");
      } else {
        ToDoEntity todo = presentTodo.get();
        Set<Map.Entry<String, Integer>> set = status.entrySet();

        for (Map.Entry<String, Integer> el : set)
          if (st.equals(el.getKey()))
            todo.setStatus(el.getValue());

        System.out.println("Todo status updated successfully");
      }
    }
  }

  public List<ToDoEntity> sortListByStatus(String st) {
    Map<String, Integer> status = new HashMap<>(Map.of("u", 0, "p", 1, "d", 2));
    if (!status.containsKey(st)) {
      System.out.println("Invalid input. Please try again...");
    }
    List<ToDoEntity> listToDisplay = new ArrayList<>();
    Set<Map.Entry<String, Integer>> set = status.entrySet();

    for (Map.Entry<String, Integer> el : set)
      if (st.equals(el.getKey()))
        listToDisplay.add(this.listOfTodos.stream().filter(td -> td.getStatus() == el.getValue()).findFirst().get());

    return listToDisplay;
  }

  private Tuple<List<ToDoRepositoryError>, Boolean> validation(String title, String description) {
    String errTitle = "";
    String errMessage = "";
    boolean res = true;
    List<ToDoRepositoryError> listOfErrors = new ArrayList<>();
    if (title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
      errTitle = "Invalid size of title";
      errMessage = "Title cannot be less than " + MIN_DESCRIPTION_LENGTH + " and greater than " +
          MAX_DESCRIPTION_LENGTH;
      res = false;
      ToDoRepositoryError err = new ToDoRepositoryError(errTitle, errMessage);
      listOfErrors.add(err);
    }
    if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
      errTitle = "Invalid size of description";
      errMessage = "Description cannot be less than " + MIN_DESCRIPTION_LENGTH + " and greater than " +
          MAX_DESCRIPTION_LENGTH;
      res = false;
      ToDoRepositoryError err = new ToDoRepositoryError(errTitle, errMessage);
      listOfErrors.add(err);
    }
    return new Tuple<List<ToDoRepositoryError>, Boolean>(listOfErrors, res);
  }
}
