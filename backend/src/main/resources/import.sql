INSERT INTO tb_user (name,email,password, created_At) VALUES ('Bruno','bruno@gmail.com','123456', NOW());
INSERT INTO tb_user (name,email,password, created_At) VALUES ('Bianca','bianca@gmail.com','123456', NOW());
INSERT INTO tb_user (name,email,password, created_At) VALUES ('Danilo','danilo@gmail.com','123456', NOW()); 

INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, created_At) VALUES ('Fuga das Galinhas','Subtitle', 1994,'Image URL', 'Movie synopsis', NOW()); 
INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, created_At) VALUES ('The Godfather','Subtitle', 1988,'Image URL', 'Movie synopsis', NOW()); 
INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, created_At) VALUES ('Fast and Furious','Subtitle', 2001,'Image URL', 'Movie synopsis', NOW()); 

INSERT INTO tb_genre (name) VALUES ('Ação');
INSERT INTO tb_genre (name) VALUES ('Aventura');
INSERT INTO tb_genre (name) VALUES ('Comédia');