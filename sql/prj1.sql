CREATE DATABASE prj1;
USE prj1;

# board create
CREATE TABLE board
(
    id         INT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(500)       NOT NULL,
    content    VARCHAR(10000)     NOT NULL,
    writer     VARCHAR(120)       NOT NULL,
    created_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id)
);
SELECT *
FROM board;


# board list select
SELECT id, title, writer, created_at
FROM board;


# 페이지 용 글 복사
INSERT INTO board (title, content, writer)
SELECT title, content, writer
FROM board;
select count(*)
from board;