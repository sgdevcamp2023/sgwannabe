CREATE DATABASE IF NOT EXISTS music
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE music;

-- musics Table Create SQL
-- 테이블 생성 SQL - music_likes
CREATE TABLE music_likes
(
    `id`           BIGINT               NOT NULL    AUTO_INCREMENT COMMENT '고유 번호',
    `music_id`     BIGINT               NOT NULL    COMMENT '음원',
    `user_id`      BIGINT               NOT NULL    COMMENT '유저',
    `created_at`   DATETIME             NOT NULL    DEFAULT NOW() COMMENT '생성 시각',
    `updated_at`   DATETIME             NOT NULL    DEFAULT NOW() COMMENT '수정 시각',
     PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - music_likes
ALTER TABLE music_likes COMMENT '음원 좋아요 테이블';

-- Foreign Key 설정 SQL - music_likes(music_id) -> musics(id)
ALTER TABLE music_likes
    ADD CONSTRAINT FK_music_likes_music_id_music_id FOREIGN KEY (music_id)
        REFERENCES musics (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - music_likes(music_id)
-- ALTER TABLE music_likes
-- DROP FOREIGN KEY FK_music_likes_music_id_music_id;
