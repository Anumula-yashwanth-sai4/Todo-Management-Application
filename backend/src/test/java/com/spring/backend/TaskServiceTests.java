package com.spring.backend;


import com.spring.backend.entity.Task;
import com.spring.backend.exceptions.TaskNotFoundException;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

        assertEquals("Test title", result.getTitle());
        assertEquals("test description", result.getDescription());


    }
    @Test
    void getAllTaskSuccess(){
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task("test name 1","test description 1"));
        tasks.add(new Task("test name 2","test description 2"));

        when(todoRepository.findAll()).thenReturn(tasks);

        List<Task> result=todoService.getAllTasks();

        assertEquals("test name 1", result.get(0).getTitle());
        assertEquals("test description 1", result.get(0).getDescription());

        assertEquals("test name 2", result.get(1).getTitle());
        assertEquals("test description 2", result.get(1).getDescription());



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

        assertEquals(2, result.size());
        assertFalse(result.get(0).getCompleted());
        assertFalse(result.get(1).getCompleted());

        verify(todoRepository).findByCompleted(false);
    }



    @Test
    void deleteTaskSuccess() {

        Integer id = 1;


        when(todoRepository.existsById(id)).thenReturn(true);


        todoService.deleteTask(id);


        verify(todoRepository).existsById(id);
        verify(todoRepository).deleteById(id);
    }
    @Test
    void deleteTaskThrowsException() {

        Integer id = 99;

        when(todoRepository.existsById(id))
                .thenReturn(false);

        TaskNotFoundException exception =
                Assertions.assertThrows(
                        TaskNotFoundException.class,
                        () -> todoService.deleteTask(id)
                );

        assertEquals(
                "The task wasnot found in the db",
                exception.getMessage()
        );

        verify(todoRepository).existsById(id);
        verify(todoRepository, never()).deleteById(anyInt() );
    }
    // ✅ 1️⃣ Update ALL fields (title, description, completed)
    @Test
    void partialTaskUpdate_updatesAllFields() {

        Integer id = 1;

        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setCompleted(false);

        Task updatingTask = new Task("New Title", "New Desc");
        updatingTask.setCompleted(true);

        when(todoRepository.findById(id))
                .thenReturn(Optional.of(existingTask));

        when(todoRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Task> result = todoService.partialTaskUpdate(id, updatingTask);

        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Desc", result.get().getDescription());
        assertTrue(result.get().getCompleted());

        verify(todoRepository).findById(id);
        verify(todoRepository).save(existingTask);
    }

    // ✅ 2️⃣ Update ONLY completed (title & description null)
    @Test
    void partialTaskUpdate_updatesOnlyCompleted() {

        Integer id = 2;

        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setCompleted(false);

        Task updatingTask = new Task(null, null);
        updatingTask.setCompleted(true);

        when(todoRepository.findById(id))
                .thenReturn(Optional.of(existingTask));

        when(todoRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Task> result = todoService.partialTaskUpdate(id, updatingTask);

        assertTrue(result.isPresent());
        assertEquals("Old Title", result.get().getTitle());
        assertEquals("Old Desc", result.get().getDescription());
        assertTrue(result.get().getCompleted());

        verify(todoRepository).save(existingTask);
    }

    // ✅ 3️⃣ Completed is NULL → must NOT change
    @Test
    void partialTaskUpdate_doesNotUpdateCompletedWhenNull() {

        Integer id = 3;

        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setCompleted(false);

        Task updatingTask = new Task("New Title", "New Desc");
        // completed intentionally null

        when(todoRepository.findById(id))
                .thenReturn(Optional.of(existingTask));

        when(todoRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Task> result = todoService.partialTaskUpdate(id, updatingTask);

        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Desc", result.get().getDescription());
        assertFalse(result.get().getCompleted());

        verify(todoRepository).save(existingTask);
    }

    // ✅ 4️⃣ Task NOT found → exception path
    @Test
    void partialTaskUpdate_throwsExceptionWhenTaskNotFound() {

        Integer id = 99;

        Task updatingTask = new Task("New Title", "New Desc");

        when(todoRepository.findById(id))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception =
                assertThrows(
                        TaskNotFoundException.class,
                        () -> todoService.partialTaskUpdate(id, updatingTask)
                );

        assertEquals("The task was not in the db", exception.getMessage());

        verify(todoRepository).findById(id);
        verify(todoRepository, never()).save(any(Task.class));
    }


}