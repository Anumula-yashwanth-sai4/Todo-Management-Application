package com.spring.backend.service;

import com.spring.backend.entity.Task;
import com.spring.backend.exceptions.TaskNotFoundException;
import com.spring.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TodoService {

    TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Task addTask(Task task){
        return todoRepository.save(task);
    }

    public void deleteTask(Integer id){

        if (todoRepository.existsById(id)){
            todoRepository.deleteById(id);
        }
        else{
            throw new TaskNotFoundException("The task wasnot found in the db");
        }

    }

    public List<Task> getAllTasks(){
        return todoRepository.findAll();
    }

    public List<Task> filterTasks(Boolean flag){
        return todoRepository.findByCompleted(flag);
    }

    public Optional<Task> partialTaskUpdate(Integer id, Task updatingTask) {

        Task existingTask = todoRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("The task was not in the db"));

        if (updatingTask.getTitle() != null) {
            existingTask.setTitle(updatingTask.getTitle());
        }

        if (updatingTask.getDescription() != null) {
            existingTask.setDescription(updatingTask.getDescription());
        }

        if (updatingTask.getCompleted() != null) {
            existingTask.setCompleted(updatingTask.getCompleted());
        }

        return Optional.of(todoRepository.save(existingTask));
    }




}
