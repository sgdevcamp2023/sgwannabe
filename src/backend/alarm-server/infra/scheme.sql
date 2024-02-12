CREATE DATABASE IF NOT EXISTS alarm
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE alarm;

-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- notifications Table Create SQL
-- 테이블 생성 SQL - notifications
CREATE TABLE notifications
(
    `id`                BIGINT        NOT NULL    AUTO_INCREMENT COMMENT '고유 번호',
    `message`           VARCHAR(100)  NOT NULL    DEFAULT '' COMMENT '알람 메시지',
    `sender_id`         BIGINT        NOT NULL    COMMENT '보내는 사람 ID',
    `sender_nickname`   VARCHAR(20)   NOT NULL    DEFAULT '' COMMENT '보내는 사람 닉네임',
    `sender_profile`    TEXT          NOT NULL    COMMENT '보내는 사람 프로필 정보',
    `receiver_id`       BIGINT        NOT NULL    COMMENT '보내는 사람 ID',
    `receiver_nickname` VARCHAR(20)   NOT NULL    DEFAULT '' COMMENT '보내는 사람 닉네임',
    `receiver_profile`  TEXT          NOT NULL    COMMENT '보내는 사람 프로필 정보',
    `is_viewed`         TINYINT       NOT NULL    DEFAULT '0' COMMENT '읽음 여부',
    `viewed_at`         DATETIME                  COMMENT '읽은 시각',
    `type`              VARCHAR(10)   NOT NULL    DEFAULT 'NORMAL' COMMENT '알람 종류',
    `created_at`        DATETIME      NOT NULL    DEFAULT NOW() COMMENT '생성 시각',
    `updated_at`        DATETIME      NOT NULL    DEFAULT NOW() COMMENT '수정 시각',
     PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - notifications
ALTER TABLE notifications COMMENT '알람 테이블';
