create table if not exists products(
	id integer AUTO_INCREMENT unique NOT NULL primary key,
    product varchar(20),
	customer_price float,
    provider_price float,
    stock_amount integer
);

create table if not exists purchases(
	id int AUTO_INCREMENT unique NOT NULL primary key,
	id_employee integer,
	purchase_date  date

);

create table if not exists lpurchases(
	idpurchase int AUTO_INCREMENT unique NOT NULL, 
	idlin int NOT NULL,
	idproduct integer NOT NULL,
	quantity float NOT NULL,
	primary key (idpurchase, idlin)
);

create table if not exists employee(
	id int AUTO_INCREMENT unique NOT NULL primary key,
    last_session date,
    contract_date date
);

alter table lpurchases add foreign key (idpurchase) references purchases (id);
alter table lpurchases add foreign key (idproduct) references products (id);
alter table purchases add foreign key (id_employee) references employee (id);
