INSERT INTO tb_user (name,email,password) VALUES ('Bruno','bruno@gmail.com','123456');
INSERT INTO tb_user (name,email,password) VALUES ('Bianca','bianca@gmail.com','123456');
INSERT INTO tb_user (name,email,password) VALUES ('Danilo','danilo@gmail.com','123456'); 

INSERT INTO tb_role(authority) VALUES ('MEMBER');
INSERT INTO tb_role(authority) VALUES ('VISITOR');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1,1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2,1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3,2);

INSERT INTO tb_genre (name) VALUES ('Ação');
INSERT INTO tb_genre (name) VALUES ('Aventura');
INSERT INTO tb_genre (name) VALUES ('Comédia');

INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, genre_id) VALUES ('Fuga das Galinhas','Subtitle', 1994,'Image URL', 'Movie synopsis', 1); 
INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, genre_id) VALUES ('The Godfather','Subtitle', 1988,'Image URL', 'Movie synopsis', 2); 
INSERT INTO tb_movie (title, subtitle, year, img_url, synopsis, genre_id) VALUES ('Fast and Furious','Subtitle', 2001,'Image URL', 'Movie synopsis', 3); 

INSERT INTO tb_review (text, user_id, movie_id) VALUES ('Muito bom!', 1, 1);
INSERT INTO tb_review (text, user_id, movie_id) VALUES ('Não gostei, achei fraco.', 2, 3);