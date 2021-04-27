package com.sbmongo.springmongo.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.sbmongo.springmongo.exception.TodoCollectionException;
import com.sbmongo.springmongo.model.TodoDTO;


public interface TodoService {
    public List<TodoDTO> getAllToDos();

    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException;

    public TodoDTO getTodoById(String id) throws TodoCollectionException;

    public TodoDTO updateTodoById(String id, TodoDTO todo) throws TodoCollectionException;

    public void deleteTodo(String id);
}
