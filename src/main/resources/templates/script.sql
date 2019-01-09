
create database accounts;

create table accounts
(
	firstname varchar(20) not null,
	lastname varchar(20) not null,
	phone integer not null
		constraint accounts_pkey
			primary key
)
;

create unique index accounts_phone_uindex
	on accounts (phone)
;

