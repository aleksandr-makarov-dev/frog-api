package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.TaskTestData;
import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskDetailsResponse;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.entity.TaskPriority;
import com.github.frog.features.tasks.mapper.TaskMapper;
import com.github.frog.features.tasks.repository.TaskRepository;
import com.github.frog.features.tasks.service.TaskServiceImpl;
import com.github.frog.features.users.UserTestData;
import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.repository.UserRepository;
import com.github.frog.features.users.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("Given a valid task creation request, when createTask is called, then it should return the created task details")
    public void givenValidTaskCreateRequest_whenCreateTask_thenReturnTaskDetailsResponse() {
        // given

        UserEntity user = UserTestData.mockUserEntity();
        user.setId(1L);

        TaskCreateRequest request = TaskTestData.mockTaskCreateRequest(user.getId());

        TaskEntity task = TaskEntity.builder()
                .name(request.name())
                .description(request.description())
                .priority(request.priority())
                .createdAt(LocalDateTime.now())
                .dueDate(request.dueDate())
                .build();
        task.setId(1L);

        TaskDetailsResponse response = new TaskDetailsResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getDueDate());

        BDDMockito.given(taskMapper.toTaskEntity(Mockito.any(TaskCreateRequest.class))).willReturn(task);
        BDDMockito.given(taskRepository.save(Mockito.any(TaskEntity.class))).willReturn(task);
        BDDMockito.given(taskMapper.toDetailsResponse(Mockito.any(TaskEntity.class))).willReturn(response);
        BDDMockito.given(userService.getUserEntityByIdOrThrow(Mockito.anyLong())).willReturn(user);

        // when
        TaskDetailsResponse taskDetailsResponse = taskService.createTask(request);

        // then
        assertThat(taskDetailsResponse).isNotNull();
        assertThat(taskDetailsResponse.id()).isGreaterThan(0L);
        assertThat(taskDetailsResponse.name()).isEqualTo(request.name());
    }
}