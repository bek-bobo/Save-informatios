create table "user"
(
    id               uuid primary key,
    name              varchar   not null,
    surname          varchar   not null,
    gmail            varchar   not null unique,
    phone_number            varchar   not null unique,
    password         varchar   not null,
    confirm_password varchar   not null,
    created          timestamp not null,
    updated          timestamp not null
)