alter table tasks
    add column created_by bigint not null,
    add constraint fk_user foreign key (created_by) references users (id)