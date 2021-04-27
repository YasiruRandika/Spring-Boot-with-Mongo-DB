package com.sbmongo.springmongo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.sbmongo.springmongo.exception.TodoCollectionException;
import com.sbmongo.springmongo.model.TodoDTO;
import com.sbmongo.springmongo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<TodoDTO> getAllToDos() {
        List<TodoDTO> todos = todoRepository.findAll();
        return todos;
    }

    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> tOptional = todoRepository.findByTodo(todoDTO.getTodo());

        if (tOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        } else {
            todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoDTO);
        }
    }

    public TodoDTO getTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            return todoOptional.get();
        } else {
            throw new TodoCollectionException(TodoCollectionException.notFoundExceString(id));
        }
    }

    public TodoDTO updateTodoById(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        Optional<TodoDTO> todoWithName = todoRepository.findByTodo(todo.getTodo());

        if (todoOptional.isPresent()) {
            if (todoWithName.isPresent() && !todoWithName.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            TodoDTO todoToUpdate = todoOptional.get();

            todoToUpdate.setCompleted(todo.isCompleted() ? todo.isCompleted() : todoToUpdate.isCompleted());
            todoToUpdate.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToUpdate.getTodo());
            todoToUpdate.setDescription(
                    todo.getDescription() != null ? todo.getDescription() : todoToUpdate.getDescription());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoToUpdate);

            return todoToUpdate;
        } else {
            throw new TodoCollectionException(TodoCollectionException.notFoundExceString(id));
        }
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
