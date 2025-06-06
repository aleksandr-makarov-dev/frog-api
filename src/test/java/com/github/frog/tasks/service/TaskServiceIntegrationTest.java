package com.github.frog.tasks.service;

import com.github.frog.tasks.dto.TaskCreateRequest;
import com.github.frog.tasks.dto.TaskDetailsResponse;
import com.github.frog.tasks.dto.TaskSummaryResponse;
import com.github.frog.tasks.dto.TaskUpdateRequest;
import com.github.frog.tasks.entity.TaskEntity;
import com.github.frog.tasks.entity.TaskPriority;
import com.github.frog.tasks.exception.TaskNotFoundException;
import com.github.frog.tasks.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
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
        TaskCreateRequest request = mockTaskCreateRequest();

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
        List<TaskEntity> taskList = mockTaskEntityList();
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
        taskRepository.saveAll(mockTaskEntityList());

        Long taskId = 1L;
        TaskUpdateRequest request = mockTaskUpdateRequest();

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
        taskRepository.saveAll(mockTaskEntityList());

        Long taskId = 999L;
        TaskUpdateRequest request = mockTaskUpdateRequest();

        // when
        assertThatThrownBy(() -> taskService.updateTaskById(taskId, request))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d does not exist".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    @DisplayName("Given valid task id, when getTaskById is called, then it should return task details")
    public void givenValidTaskId_whenGetTaskById_thenReturnTaskDetailsResponse() {
        // given
        List<TaskEntity> savedTasks = taskRepository.saveAll(mockTaskEntityList());
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
        taskRepository.saveAll(mockTaskEntityList());
        Long taskId = 999L;

        // when
        assertThatThrownBy(() -> taskService.getTaskById(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d does not exist".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    @DisplayName("Given valid task id, when deleteTaskById is called, then it should not throw TaskNotFoundException")
    public void givenValidTaskId_whenDeleteTaskById_thenNotThrowTaskNotFoundException() {
        // given
        List<TaskEntity> savedTasks = taskRepository.saveAll(mockTaskEntityList());
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
        taskRepository.saveAll(mockTaskEntityList());
        Long taskId = 999L;

        // when
        assertThatThrownBy(() -> taskService.deleteTaskById(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d does not exist".formatted(taskId));

        // then
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    private List<TaskEntity> mockTaskEntityList() {
        return List.of(
                new TaskEntity("task 1", "task 1 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 7, 12, 45), TaskPriority.LOW),
                new TaskEntity("task 2", "task 2 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 8, 13, 45), TaskPriority.LOW),
                new TaskEntity("task 3", "task 3 description", LocalDateTime.now(), null, TaskPriority.LOW),
                new TaskEntity("task 4", "task 4 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 10, 16, 30), TaskPriority.LOW)
        );
    }

    private TaskCreateRequest mockTaskCreateRequest() {
        return new TaskCreateRequest(
                "test task name",
                "test task description",
                TaskPriority.MEDIUM,
                LocalDateTime.of(2025, 6, 7, 12, 45));
    }

    private TaskUpdateRequest mockTaskUpdateRequest() {
        return new TaskUpdateRequest(
                "task updated name",
                "task updated description",
                TaskPriority.HIGH,
                LocalDateTime.of(2026, 8, 12, 14, 15)
        );
    }
}