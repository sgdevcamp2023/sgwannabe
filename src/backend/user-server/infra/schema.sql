CREATE DATABASE IF NOT EXISTS lalala;

USE lalala;

-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- user Table Create SQL
-- 테이블 생성 SQL - user
CREATE TABLE `user` (
    id            BIGINT        NOT NULL    AUTO_INCREMENT,
    email         VARCHAR(60)   NOT NULL,
    last_access   DATETIME(6),
    nickname      VARCHAR(15)   NOT NULL,
    password      CHAR(68)      NOT NULL,
    profile       VARCHAR(255)              DEFAULT '',
    role          VARCHAR(15)   NOT NULL    DEFAULT 'USER',
    status        VARCHAR(10)   NOT NULL    DEFAULT 'ACTIVE',
    created_at    DATETIME(6),
    updated_at    DATETIME(6),
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE `user`
   ADD CONSTRAINT EMAIL_UNIQUE UNIQUE (email)

-- 테이블 Comment 설정 SQL - user
ALTER TABLE `user`` COMMENT '유저 테이블';
