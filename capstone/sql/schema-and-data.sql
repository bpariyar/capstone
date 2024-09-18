drop database if exists capstone;
create database capstone;
use capstone;

CREATE TABLE flashcard_set(
	flashcard_set_id int auto_increment primary key,
    title varchar(255) unique
);

CREATE TABLE flashcard(
	flashcard_id int auto_increment primary key,
    flashcard_set_id int,
    front_data varchar(255),
    back_data varchar(255),
	constraint fk_flashcard_id_flashcard_set_id
		foreign key (flashcard_set_id)
		references flashcard_set(flashcard_set_id)
);

-- data
insert into flashcard_set(flashcard_set_id, title) values 
(1, "World History Midterm"),
(2, "Spanish - Colors");

insert into flashcard(flashcard_set_id, front_data, back_data) values
(1, "What year did Christopher Columbus reach the Americas?", "1492"),
(1, "Who was the first European explorer to reach India by sea?", "Vasco Da Gama"),
(1, "What ancient civilization was responsible for the creation of the Rosetta Stone?", "Ancient Egyptians"),
(1, "Who founded the Mongol Empire?", "Genghis Khan"),
(1, "Who was the longest-reigning monarch in British history?", "Queen Victoria"),
(2, "Yellow", "Amarillo"),
(2, "Red", "Rojo"),
(2, "Green", "Verde"),
(2, "Blue", "Azul"),
(2, "Orange", "Naranja");


