DROP TABLE Post;

CREATE TABLE Post(
zeNo SERIAL,
zeTime timestamp,
userEmail text,
title text,
zeText text,
PRIMARY KEY (zeNo)); 

SELECT * FROM Post;