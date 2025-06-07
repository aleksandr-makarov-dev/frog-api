create table if not exists resources
(
    id            bigint       not null generated always as identity,
    original_name varchar(125) not null,
    key           varchar(500) not null,
    extension     varchar(16)  not null,
    size          bigint       not null,
    mime_type     varchar(25)  not null,
    primary key (id)
)