CREATE DATABASE IF NOT EXISTS music
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE music;

-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- artists Table Create SQL
-- 테이블 생성 SQL - artists
CREATE TABLE artists
(
    `id`          BIGINT        NOT NULL    AUTO_INCREMENT COMMENT '고유 번호',
    `name`        VARCHAR(50)   NOT NULL    DEFAULT '' COMMENT '이름',
    `gender`      VARCHAR(6)    NOT NULL    DEFAULT 'MALE' COMMENT '성별. MALE / FEMALE / MIX',
    `type`        VARCHAR(5)    NOT NULL    DEFAULT 'SOLO' COMMENT '아티스트 구분. SOLO / GROUP',
    `agency`      VARCHAR(50)   NOT NULL    COMMENT '소속사',
    `created_at`  DATETIME      NOT NULL    DEFAULT NOW() COMMENT '생성 시각',
    `updated_at`  DATETIME      NOT NULL    DEFAULT NOW() COMMENT '수정 시각',
     PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - artists
ALTER TABLE artists COMMENT '아티스트 테이블';


-- albums Table Create SQL
-- 테이블 생성 SQL - albums
CREATE TABLE albums
(
    `id`           BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '고유 번호', 
    `artist_id`    BIGINT          NOT NULL    COMMENT '아티스트', 
    `type`         VARCHAR(7)      NOT NULL    DEFAULT 'SINGLE' COMMENT '앨범 구분. SINGLE / REGULAR',
    `title`        VARCHAR(50)     NOT NULL    DEFAULT '' COMMENT '제목',
    `released_at`  DATETIME        NOT NULL    COMMENT '발매일',
    `cover_url`    VARCHAR(255)    NOT NULL    DEFAULT '' COMMENT '앨범 커버 주소',
    `like_count`   INT UNSIGNED    NOT NULL    DEFAULT 0 COMMENT '좋아요',
    `created_at`   DATETIME        NOT NULL    DEFAULT NOW() COMMENT '생성 시각', 
    `updated_at`   DATETIME        NOT NULL    DEFAULT NOW() COMMENT '수정 시각', 
     PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - albums
ALTER TABLE albums COMMENT '앨범 테이블';

-- Foreign Key 설정 SQL - albums(artist_id) -> artists(id)
ALTER TABLE albums
    ADD CONSTRAINT FK_albums_artist_id_artists_id FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - albums(artist_id)
-- ALTER TABLE albums
-- DROP FOREIGN KEY FK_albums_artist_id_artists_id;


-- musics Table Create SQL
-- 테이블 생성 SQL - musics
CREATE TABLE musics
(
    `id`           BIGINT               NOT NULL    AUTO_INCREMENT COMMENT '고유 번호', 
    `album_id`     BIGINT               NOT NULL    COMMENT '앨범', 
    `artist_id`    BIGINT               NOT NULL    COMMENT '아티스트', 
    `title`        VARCHAR(100)         NOT NULL    DEFAULT '' COMMENT '제목',
    `play_time`    SMALLINT UNSIGNED    NOT NULL    DEFAULT 0 COMMENT '재생 시간',
    `lyrics`       TEXT                 NOT NULL    COMMENT '가사',
    `file_url`     VARCHAR(255)         NOT NULL    DEFAULT '' COMMENT '파일 주소',
    `format_type`  VARCHAR(5)           NOT NULL    DEFAULT 'MP3' COMMENT '파일 형식. MP3 / FLAC',
    `like_count`   INT UNSIGNED         NOT NULL    DEFAULT 0 COMMENT '좋아요',
    `created_at`   DATETIME             NOT NULL    DEFAULT NOW() COMMENT '생성 시각',
    `updated_at`   DATETIME             NOT NULL    DEFAULT NOW() COMMENT '수정 시각',
     PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - musics
ALTER TABLE musics COMMENT '음원 테이블';

-- Foreign Key 설정 SQL - musics(artist_id) -> artists(id)
ALTER TABLE musics
    ADD CONSTRAINT FK_musics_artist_id_artists_id FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - musics(artist_id)
-- ALTER TABLE musics
-- DROP FOREIGN KEY FK_musics_artist_id_artists_id;

-- Foreign Key 설정 SQL - musics(album_id) -> albums(id)
ALTER TABLE musics
    ADD CONSTRAINT FK_musics_album_id_albums_id FOREIGN KEY (album_id)
        REFERENCES albums (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - musics(album_id)
-- ALTER TABLE musics
-- DROP FOREIGN KEY FK_musics_album_id_albums_id;
