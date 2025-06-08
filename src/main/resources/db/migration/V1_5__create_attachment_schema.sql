create table if not exists attachments
(
    id          bigint                      not null generated always as identity,
    task_id     bigint                      not null,
    resource_id bigint                      not null,
    attached_at timestamp without time zone not null,
    constraint uk_task_resource unique (task_id, resource_id),
    constraint fk_task foreign key (task_id) references tasks (id),
    constraint fk_resource foreign key (resource_id) references resources (id),
    primary key (id)
)