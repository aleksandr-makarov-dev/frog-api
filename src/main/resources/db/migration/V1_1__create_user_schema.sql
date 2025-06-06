create table if not exists users
(
    id                  bigint generated always as identity,
    email               varchar(255)                not null unique,
    password_hash       varchar(255)                not null,
    email_confirmed     boolean                     not null,
    created_at          timestamp without time zone not null,
    lockout_end         timestamp without time zone,
    access_failed_count int                         not null,
    primary key (id)
)