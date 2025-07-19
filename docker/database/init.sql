create table clients (
	id INT primary key generated always as identity,
	first_name VARCHAR(50),
	last_name VARCHAR(50)
);

create table orders (
	id INT primary key generated always as identity,
	client_id INT,
	cost INT,
	constraint fk_clients
		foreign key (client_id)
		references clients(id)
);

create table dishes (
	id INT primary key generated always as identity,
	name VARCHAR(50),
	description VARCHAR(50),
	cost INT
);

create table dishes_in_orders (
	id INT primary key generated always as identity,
	order_id INT,
	dish_id INT,
	count INT,
	constraint fk_orders
		foreign key (order_id)
		references orders(id),
	constraint fk_dishes
		foreign key (dish_id)
		references dishes(id)
);