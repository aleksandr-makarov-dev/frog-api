create table if not exists tasks
(
    id          bigint generated always as identity,
    name        varchar(150)                not null,
    description varchar(250),
    created_at  timestamp without time zone not null,
    due_date    timestamp without time zone,
    priority    varchar(25)                 not null,
    created_by  bigint                      not null,
    primary key (id),
    constraint fk_user foreign key (created_by) references users (id)
)