package com.sbmongo.springmongo.repository;

import java.util.Optional;

import com.sbmongo.springmongo.model.TodoDTO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TodoRepository extends MongoRepository<TodoDTO, String>{
    @Query("{'todo' : ?0}")
    Optional<TodoDTO> findByTodo(String todo);
}
