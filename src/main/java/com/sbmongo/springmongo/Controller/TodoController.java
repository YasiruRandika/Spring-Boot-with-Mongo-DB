package com.sbmongo.springmongo.Controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.sbmongo.springmongo.exception.TodoCollectionException;
import com.sbmongo.springmongo.model.TodoDTO;
import com.sbmongo.springmongo.service.TodoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    @Autowired
    private TodoServiceImpl todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllToDos() {
        List<TodoDTO> todos =  todoService.getAllToDos();

        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }

        return new ResponseEntity<>("No todos  available", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){
        try {
            todoService.createTodo(todoDTO);
            return new ResponseEntity<TodoDTO>(todoDTO, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
       
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id) {
        try {
           TodoDTO todo = todoService.getTodoById(id); 
           return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        try {
            TodoDTO todoUpdated = todoService.updateTodoById(id, todo);
            return new ResponseEntity<TodoDTO>(todoUpdated, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Successfully Deleted the record with Id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
