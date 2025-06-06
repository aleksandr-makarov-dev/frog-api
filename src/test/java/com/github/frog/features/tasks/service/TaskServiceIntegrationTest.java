package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.TaskTestData;
import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskDetailsResponse;
import com.github.frog.features.tasks.dto.TaskSummaryResponse;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.exception.TaskNotFoundException;
import com.github.frog.features.tasks.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class TaskServiceIntegrationTest {

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        taskRepository.deleteAll();
    }

    @Test
    @DisplayName("Given a valid task creation request, when createTask is called, then it should return the created task details")
    public void givenValidTaskCreateRequest_whenCreateTask_thenReturnCreatedTaskDetails() {
        // given
        TaskCreateRequest request = TaskTestData.mockTaskCreateRequest();

        // when
        TaskDetailsResponse response = taskService.createTask(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.priority()).isEqualTo(request.priority());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.dueDate()).isEqualTo(request.dueDate());
    }

    @Test
    @DisplayName("Given task entity list, when getAllTasks is called, then it should return list of task summaries")
    public void givenTaskEntityList_whenGetAllTasks_thenReturnTaskSummaryResponseList() {
        // given
        List<TaskEntity> taskList = TaskTestData.mockTaskEntityList();
        taskRepository.saveAll(taskList);

        // then
        List<TaskSummaryResponse> response = taskService.getAllTasks();

        // when
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(taskList.size());
    }

    @Test
    @DisplayName("Given valid task id and task update request, when updateTaskById is called, then it should return update task details")
    public void givenValidIdAndTaskUpdateRequest_whenUpdateTaskById_thenReturnTaskDetailsResponse() {
        // given
        taskRepository.saveAll(TaskTestData.mockTaskEntityList());

        Long taskId = 1L;
        TaskUpdateRequest request = TaskTestData.mockTaskUpdateRequest();

        // when
        TaskDetailsResponse response = taskService.updateTaskById(taskId, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(taskId);
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.priority()).isEqualTo(request.priority());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.dueDate()).isEqualTo(request.dueDate());
    }

    @Test
    @DisplayName("Given invalid task id and valid task update request, when updateTaskById is called, then it should throw TaskNotFoundException")
    public void givenInValidIdAndValidTaskUpdateRequest_whenUpdateTaskById_thenThrowTaskNotFoundException() {
        // given
        taskRepository.saveAll(TaskTestData.mockTaskEntityList());

        Long taskId = 999L;
        TaskUpdateRequest request = TaskTestData.mockTaskUpdateRequest();

        // when
        assertThatThrownBy(() -> taskService.updateTaskById(taskId, request))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d not found".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    @DisplayName("Given valid task id, when getTaskById is called, then it should return task details")
    public void givenValidTaskId_whenGetTaskById_thenReturnTaskDetailsResponse() {
        // given
        List<TaskEntity> savedTasks = taskRepository.saveAll(TaskTestData.mockTaskEntityList());
        Long taskId = savedTasks.get(0).getId();

        // when
        TaskDetailsResponse response = taskService.getTaskById(taskId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(taskId);
    }

    @Test
    @DisplayName("Given invalid task id, when getTaskById is called, then it should throw TaskNotFoundException")
    public void givenInvalidTaskId_whenGetTaskById_thenThrowTaskNotFoundException() {
        // given
        taskRepository.saveAll(TaskTestData.mockTaskEntityList());
        Long taskId = 999L;

        // when
        assertThatThrownBy(() -> taskService.getTaskById(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d not found".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    @DisplayName("Given valid task id, when deleteTaskById is called, then it should not throw TaskNotFoundException")
    public void givenValidTaskId_whenDeleteTaskById_thenNotThrowTaskNotFoundException() {
        // given
        List<TaskEntity> savedTasks = taskRepository.saveAll(TaskTestData.mockTaskEntityList());
        Long taskId = savedTasks.get(0).getId();

        // when
        assertThatNoException().isThrownBy(() -> taskService.deleteTaskById(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    @DisplayName("Given invalid task id, when deleteTaskById is called, then it should throw TaskNotFoundException")
    public void givenInvalidTaskId_whenDeleteTaskById_thenThrowTaskNotFoundException() {
        // given
        taskRepository.saveAll(TaskTestData.mockTaskEntityList());
        Long taskId = 999L;

        // when
        assertThatThrownBy(() -> taskService.deleteTaskById(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d not found".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }
}