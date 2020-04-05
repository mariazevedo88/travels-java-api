create table transaction(
id serial,
nsu varchar(20),
authorization_number varchar(20),
transaction_date TIMESTAMP,
amount numeric(10,2),
type varchar(40),
primary key (id));

create table statistics(
id serial,
transactions_sum numeric(10,2),
transactions_avg numeric(10,2),
transactions_max numeric(10,2),
transactions_min numeric(10,2),
transactions_count decimal,
primary key (id));