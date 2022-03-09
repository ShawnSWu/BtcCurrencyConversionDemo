drop table if exists Currency;

create table Currency
(
    id          integer     not null auto_increment,
    code        varchar(3)  not null unique,
    symbol      varchar(32) not null,
    zh_name     varchar(64) not null,
    description varchar(256),
    primary key (id)
);
