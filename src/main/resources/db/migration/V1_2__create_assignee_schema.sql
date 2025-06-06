create table if not exists assignees
(
    id         bigint generated always as identity,
    task_id    bigint                      not null,
    user_id    bigint                      not null,
    assigned_at timestamp without time zone not null,
    primary key (id),
    constraint uk_task_user unique (task_id, user_id),
    constraint fk_task foreign key (task_id) references tasks (id),
    constraint fk_user foreign key (user_id) references users (id)
)