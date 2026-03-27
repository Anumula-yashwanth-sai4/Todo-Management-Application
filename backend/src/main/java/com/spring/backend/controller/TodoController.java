package com.spring.backend.controller;


import com.spring.backend.entity.Task;
import com.spring.backend.service.TodoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/todo")
@CrossOrigin()
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping(path = "/showTasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> findAllTodos() {
        return ResponseEntity.ok(todoService.getAllTasks());
    }

    @GetMapping(path = "/filtered/{completed}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getFilteredTasks(@PathVariable Boolean completed) {
        return ResponseEntity.ok(todoService.filterTasks(completed));
    }


    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> storeTodo(@RequestBody Task task) {
        return ResponseEntity.ok(todoService.addTask(task));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        todoService.deleteTask(id);
        return ResponseEntity.ok("Task with "+ id+" id is deleted");
    }

    @PatchMapping(path = "/statuscompleted/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Task>> updateTodoPartially(@PathVariable Integer id, @RequestBody Task task) {
        return ResponseEntity.ok(todoService.partialTaskUpdate(id, task));
    }

    @PatchMapping(path = "/editTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Task>> editTask(@PathVariable Integer id, @RequestBody Task task) {
        return ResponseEntity.ok(todoService.partialTaskUpdate(id, task));
    }
}