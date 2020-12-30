create table users(
id serial,
name varchar(255),
password varchar,
email varchar(100),
role varchar(40),
primary key (id));

create table accounts(
id serial,
account_number varchar(50),
account_type varchar(40),
primary key (id));

create table users_accounts(
id serial,
account_id integer,
user_id integer,
primary key(id),
foreign key(user_id) references users(id),
foreign key(account_id) references accounts(id));

insert into users(name, email) values ('swagger user', 'dev@swagger.user');

alter table travel add column account_id integer;
alter table travel add constraint fk_account_id foreign key (account_id) references accounts(id);