drop database if exists ecommerce;
create database ecommerce;
use ecommerce;


create table users(
	user_id serial primary key,
    firstname varchar(100),
    lastname varchar(100),
	username varchar(100),
    email varchar(255),
	password char(100),
	reg_date datetime
);