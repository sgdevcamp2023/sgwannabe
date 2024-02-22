CREATE DATABASE IF NOT EXISTS chart
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE chart;

create table streaming_logs (
    id bigint not null auto_increment,
    music_id bigint not null,
    created_at datetime(6),
    updated_at datetime(6),
    primary key (id)
) engine=InnoDB;
