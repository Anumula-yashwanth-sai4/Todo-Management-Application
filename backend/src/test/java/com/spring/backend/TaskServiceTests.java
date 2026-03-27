package com.spring.backend;


import com.spring.backend.entity.Task;
import com.spring.backend.repository.TodoRepository;
import com.spring.backend.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTests {
    @MockitoBean
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @Test
    void addTaskSuccess() {

        Task inputTask = new Task("Test title", "test description");
        Task savedTask = new Task("Test title", "test description");

        when(todoRepository.save(ArgumentMatchers.<Task>any()))
                .thenReturn(savedTask);

        Task result = todoService.addTask(inputTask);

        Assertions.assertEquals("Test title", result.getTitle());
        Assertions.assertEquals("test description", result.getDescription());


    }
    @Test
    void getAllTaskSuccess(){
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task("test name 1","test description 1"));
        tasks.add(new Task("test name 2","test description 2"));

        when(todoRepository.findAll()).thenReturn(tasks);

        List<Task> result=todoService.getAllTasks();

        Assertions.assertEquals("test name 1", result.get(0).getTitle());
        Assertions.assertEquals("test description 1", result.get(0).getDescription());

        Assertions.assertEquals("test name 2", result.get(1).getTitle());
        Assertions.assertEquals("test description 2", result.get(1).getDescription());



    }

    @Test
    void filterTasksSuccess() {

        Task t1 = new Task("Task 1", "Desc 1");
        t1.setCompleted(false);

        Task t2 = new Task("Task 2", "Desc 2");
        t2.setCompleted(false);

        List<Task> pendingTasks = List.of(t1, t2);

        when(todoRepository.findByCompleted(false))
                .thenReturn(pendingTasks);

        List<Task> result = todoService.filterTasks(false);

        Assertions.assertEquals(2, result.size());
        Assertions.assertFalse(result.get(0).getCompleted());
        Assertions.assertFalse(result.get(1).getCompleted());

        verify(todoRepository).findByCompleted(false);
    }

    @Test
    void partialTaskUpdate_shouldUpdateTitleOnly() {

        Integer id = 1;

        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setCompleted(false);

        Task updatingTask = new Task("New Title", null);

        when(todoRepository.findById(id))
                .thenReturn(Optional.of(existingTask));



        when(todoRepository.save(ArgumentMatchers.<Task>any()))
                .thenAnswer(invocation -> invocation.getArgument(0));



        Optional<Task> result = todoService.partialTaskUpdate(id, updatingTask);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("New Title", result.get().getTitle());
        Assertions.assertEquals("Old Desc", result.get().getDescription());
        Assertions.assertFalse(result.get().getCompleted());

        verify(todoRepository).findById(id);
        verify(todoRepository).save(existingTask);
    }



}
