create table main_user
(
    id              bigint  not null
        primary key,
    password        varchar(255),
    first_name      varchar(255),
    last_name       varchar(255),
    active          boolean not null,
    username        varchar(255),
    currentbalance  integer,
    email           varchar(255),
    activation_code varchar(255)
);

alter table main_user
    owner to postgres;

create table user_role
(
    user_id bigint not null
        constraint user_fk
            references main_user,
    roles   varchar(255)
);

alter table user_role
    owner to postgres;

create table car
(
    id          bigint  not null
        primary key,
    car_name    varchar(255),
    category    text,
    description text,
    in_rent     boolean not null,
    price       integer,
    icon_name   varchar(255)
);

alter table car
    owner to postgres;

create table application
(
    id          bigint  not null
        primary key,
    active      boolean not null,
    total_price integer,
    car_id      bigint
        constraint fkst8xgdau7igvp6og3ure9vwru
            references car,
    client_id   bigint
        constraint fkio87oqddw52ei5k2tm4dv57qt
            references main_user,
    end_time    timestamp,
    start_time  timestamp
);

alter table application
    owner to postgres;

create table review
(
    id        bigint not null
        primary key,
    text      varchar(255),
    author_id bigint
        constraint fk4s8hhauku0pqs8yiabvbed216
            references main_user,
    car_id    bigint
        constraint fkoie88l9xdqjv41ym8c1s7valq
            references car
);

alter table review
    owner to postgres;

