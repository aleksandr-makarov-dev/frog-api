package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.TaskTestData;
import com.github.frog.features.tasks.dto.AssigneeAddRequest;
import com.github.frog.features.tasks.dto.AssigneeResponse;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.exception.TaskNotFoundException;
import com.github.frog.features.tasks.repository.AssigneeRepository;
import com.github.frog.features.tasks.repository.TaskRepository;
import com.github.frog.features.users.UserTestData;
import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.exception.UserNotFoundException;
import com.github.frog.features.users.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class AssigneeServiceIntegrationTest {

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
    private UserRepository userRepository;

    @Autowired
    private AssigneeRepository assigneeRepository;

    @Autowired
    private AssigneeService assigneeService;

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
        assigneeRepository.deleteAll();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Given valid task id and user id, when addAssignee is called, then return AssigneeResponse")
    public void givenValidTaskIdAndUserId_whenAddAssignee_thenReturnAssigneeResponse() {
        // given
        TaskEntity task = TaskTestData.mockTaskEntity();
        UserEntity user = UserTestData.mockUserEntity();
        taskRepository.save(task);
        userRepository.save(user);

        // when
        AssigneeResponse response = assigneeService.addAssignee(task.getId(), user.getId());

        // then
        assertThat(response).isNotNull();
        assertThat(response.taskId()).isEqualTo(task.getId());
        assertThat(response.userId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Given valid task id and invalid user id, when addAssignee is called, then throw UserNotFoundException")
    public void givenValidTaskIdAndInvalidUserId_whenAddAssignee_thenThrowUserNotFoundException() {
        // given
        TaskEntity task = TaskTestData.mockTaskEntity();
        UserEntity user = UserTestData.mockUserEntity();
        taskRepository.save(task);
        userRepository.save(user);

        Long invalidUserId = 999L;

        // when
        assertThatThrownBy(() -> assigneeService.addAssignee(task.getId(), invalidUserId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID=%d not found".formatted(invalidUserId));

        // then
        assertThat(assigneeRepository.findByTaskIdAndUserId(task.getId(), invalidUserId)).isEmpty();
    }

    @Test
    @DisplayName("Given invalid task id and valid user id, when addAssignee is called, then throw TaskNotFoundException")
    public void givenInvalidTaskIdAndValidUserId_whenAddAssignee_thenThrowTaskNotFoundException() {
        // given
        TaskEntity task = TaskTestData.mockTaskEntity();
        UserEntity user = UserTestData.mockUserEntity();
        taskRepository.save(task);
        userRepository.save(user);

        Long invalidTaskId = 999L;

        // when
        assertThatThrownBy(() -> assigneeService.addAssignee(invalidTaskId, user.getId()))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task with ID=%d not found".formatted(invalidTaskId));

        // then
        assertThat(assigneeRepository.findByTaskIdAndUserId(invalidTaskId, user.getId())).isEmpty();
    }

    @Test
    @DisplayName("Given valid task id and user id, when remove assignee, then not throw AssigneeNotFoundException")
    public void givenValidTaskIdAndUserId_whenRemoveAssignee_thenNotThrowAssigneeNotFoundException() {
        // given
        TaskEntity task = TaskTestData.mockTaskEntity();
        UserEntity user = UserTestData.mockUserEntity();
        taskRepository.save(task);
        userRepository.save(user);

        // when
        assertThatNoException().isThrownBy(() -> assigneeService.removeAssignee(task.getId(), user.getId()));

        // then
        assertThat(assigneeRepository.findByTaskIdAndUserId(task.getId(), user.getId())).isEmpty();
    }


}