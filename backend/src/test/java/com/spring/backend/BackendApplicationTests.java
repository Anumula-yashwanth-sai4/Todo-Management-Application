package com.spring.backend;

import com.spring.backend.entity.Task;
import com.spring.backend.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	TodoService todoService;

	@Test
	void testFindAllTodosSuccess() throws Exception {
		List<Task> taskList = new ArrayList<>();
		Task t1 = new Task("Task1", "Desc1");
		t1.setCompleted(false);
		Task t2 = new Task("Task2", "Desc2");
		t2.setCompleted(true);
		taskList.add(t1);
		taskList.add(t2);

		when(todoService.getAllTasks()).thenReturn(taskList);

		mvc.perform(get("/todo/showTasks"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2));

	}

	@Test
	void testGetFilteredTasksSuccess() throws Exception {
		Task t1 = new Task("Task1", "Desc1");
		t1.setCompleted(true);

		Task t2 = new Task("Task2", "Desc2");
		t2.setCompleted(true);

		when(todoService.filterTasks(true)).thenReturn(List.of(t1, t2));

		mvc.perform(get("/todo/filtered/true"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2));

	}

	@Test
	void testAddTaskSuccess() throws Exception {

		Task saved = new Task("New Task", "New Desc");
		saved.setCompleted(false);

		when(todoService.addTask(any(Task.class))).thenReturn(saved);

		mvc.perform(post("/todo/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								    {
								      "title": "New Task",
								      "description": "New Desc",
								      "completed": false
								    }
								"""))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdateStatusCompletedSuccess() throws Exception {
		Task updated = new Task("Task1", "Desc1");
		updated.setCompleted(true);

		when(todoService.partialTaskUpdate(eq(5), any(Task.class))).thenReturn(Optional.of(updated));

		String requestJson = "{\"completed\":true}";

		mvc.perform(patch("/todo/statuscompleted/5")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

		@Test
		void testEditTaskSuccess() throws Exception {
			Task updated = new Task("Edited Title", "Edited Desc");
			updated.setCompleted(false);

			when(todoService.partialTaskUpdate(eq(7), any(Task.class))).thenReturn(Optional.of(updated));

			String requestJson = "{\"title\":\"Edited Title\",\"description\":\"Edited Desc\",\"completed\":false}";

			mvc.perform(patch("/todo/editTask/7")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(requestJson))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}



}







