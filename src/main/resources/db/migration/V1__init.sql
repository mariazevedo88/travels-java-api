create table travel(
id serial,
order_number varchar(20),
start_date TIMESTAMP,
end_date TIMESTAMP,
amount numeric(10,2),
type varchar(40),
primary key (id));

create table statistics(
id serial,
travel_sum numeric(10,2),
travel_avg numeric(10,2),
travel_max numeric(10,2),
travel_min numeric(10,2),
travel_count decimal,
primary key (id));