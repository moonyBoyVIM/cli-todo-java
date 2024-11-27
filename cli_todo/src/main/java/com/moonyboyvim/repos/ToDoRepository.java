package com.moonyboyvim.repos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.moonyboyvim.entity.ToDoEntity;

public class ToDoRepository {
  /**
   * Class which represents a
   * tuple data structure.
   * Main purpose of this
   * structure is storage a
   * pairs of value and return
   * one of this method using
   * getters
   */
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

  /**
   * This method display
   * all todos from the initialized list.
   */
  public void getListOfTodo() {
    if (this.listOfTodos.equals(null) || this.listOfTodos.size() == 0)
      System.out.println("Your list is empty now. Create your todo!");
    this.listOfTodos
        .forEach(td -> System.out.println(td.getId() + ". " + td.getTitle() + " -> " + td.getStatusInString()));
  }

  /**
   * This method accepts two params
   * title, description. After validation
   * params this method create new instance
   * of ToDoEntity and saved this to the list.
   * If result of validation is false
   * method display all mistakes using foreach.
   * 
   * @param title
   * @param description
   */
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

  /**
   * This method accept an id.
   * Return Optional<ToDoEntity>.
   * 
   * @param id
   * @return
   */
  public Optional<ToDoEntity> getTodoById(int id) {
    return this.listOfTodos.stream().filter(td -> td.getId() == id).findFirst();
  }

  /**
   * This method accept an id, title, description.
   * First of all method check if result object of
   * getTodoById(id) is have an instance of ToDoEntity.
   * After validation title and description method set
   * new title and new description in present todo and
   * print message which report about successfully
   * edited or updated todo.
   * If result of validation is false
   * method display all mistakes using foreach.
   * 
   * @param id
   * @param title
   * @param description
   */
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

  /**
   * This method accepts an id value.
   * Method check if result object of
   * getTodoById(id) is have an instance of ToDoEntity.
   * If Optional have an instance method remove this
   * object from the list.
   * 
   * @param id
   */
  public void removeTodoById(int id) {
    Optional<ToDoEntity> td = this.getTodoById(id);
    if (td.get().equals(null)) {
      System.out.println("Todo is not present in the list");
    } else {
      this.listOfTodos.remove(td.get());
      System.out.println("Todo was removed succesfully from the list");
    }
  }

  /**
   * This method accepts and id and String status params.
   * Method have an instance of HashMap which operates with
   * Integer and String instances.
   * Map has a ready set of keys and values.
   * After check is map has an value in keys, we received an
   * Optional value from getTodoById(id) and if Optional has
   * instance of ToDoEntity method unpacking this Optional.
   * Using foreach loop for comparising st value to key value
   * from map, and if values are equals to each other,
   * method set status respectively to the value of status
   * which enter user.
   * 
   * @param id
   * @param st
   */
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

  /**
   * This method accepts an String status.
   * Method have an instance of HashMap which operates with
   * Integer and String instances.
   * Method creates an instance of ArrayList which storage an
   * elements with respectively status.
   * To obtain this elements method using foreach method and
   * comparises st value to key value of map.
   * If values are equals initialized list is updating and
   * adding a new elements with respectivelu status.
   * 
   * @param st
   * @return
   */
  public List<ToDoEntity> sortListByStatus(String st) {
    Map<String, Integer> status = new HashMap<>(Map.of("u", 0, "p", 1, "d", 2));
    if (!status.containsKey(st)) {
      System.out.println("Invalid input. Please try again...");
    }
    List<ToDoEntity> listToDisplay = new ArrayList<>();
    Set<Map.Entry<String, Integer>> set = status.entrySet();

    int res = set.stream().filter(el -> el.getKey().equals(st)).findFirst().get().getValue();
    this.listOfTodos.stream().forEach(td -> {
      if (td.getStatus() == res)
        listToDisplay.add(td);
    });
    return listToDisplay;
  }

  /**
   * This method accepts an title and description values.
   * Method check if length of title and description are less
   * or greater than values in constants.
   * If one of params does not match to conditions, method
   * create an instance of ToDoRepositoryError and put this
   * in list of errors and set boolean result of validation
   * to false.
   * Method return an instance of tuple which contains
   * list of errors and boolean result of validation.
   * 
   * @param title
   * @param description
   * @return
   */
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
