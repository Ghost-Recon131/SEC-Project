drop database if exists ecommerce;
create database ecommerce;
use ecommerce;


create table users(
	user_id serial primary key,
	username varchar(100),
	email varchar(255),
    firstname varchar(100),
    lastname varchar(100),
	password varchar(100),
	secret_question varchar(200),
	secret_question_answer varchar(200),
	reg_date datetime
);
