create table access_log
(
	id int auto_increment
		primary key,
	type varchar(128) not null,
	payload json not null
);

create index audit_log_type_index
	on access_log (type);

create table address
(
	id int auto_increment
		primary key,
	address varchar(512) not null
);

create table category
(
	id int auto_increment
		primary key,
	name varchar(128) not null,
	description varchar(512) not null,
	enabled tinyint(1) default 1 not null,
	constraint category_name_uindex
		unique (name)
);

create table image
(
	id int auto_increment
		primary key,
	url varchar(2048) not null,
	description varchar(512) null
);

create table invoice
(
	id int auto_increment
		primary key,
	type varchar(128) not null,
	amount decimal default 0 not null
);

create index invoice_type_index
	on invoice (type);

create table invoice_guest
(
	invoice_id int not null
		primary key,
	email_address varchar(320) not null,
	first_name varchar(80) not null,
	last_name varchar(80) null,
	constraint invoice_guest_invoice_id_fk
		foreign key (invoice_id) references invoice (id)
);

create table payment_method
(
	id int auto_increment
		primary key,
	type varchar(128) not null
);

create table `order`
(
	id int auto_increment
		primary key,
	invoice_id int not null,
	address_id int not null,
	payment_method_id int not null,
	constraint order_address_id_fk
		foreign key (address_id) references address (id),
	constraint order_invoice_id_fk
		foreign key (invoice_id) references invoice (id),
	constraint order_payment_method_id_fk
		foreign key (payment_method_id) references payment_method (id)
);

create table order_status
(
	id int auto_increment
		primary key,
	order_id int not null,
	status varchar(128) not null,
	details varchar(512) not null,
	timestamp timestamp default CURRENT_TIMESTAMP not null,
	constraint order_status_order_id_fk
		foreign key (order_id) references `order` (id)
);

create index order_status_status_index
	on order_status (status);

create index payment_method_type_index
	on payment_method (type);

create table payment_method_credit_card
(
	payment_method_id int auto_increment
		primary key,
	card_number varchar(19) not null,
	card_holder_name varchar(180) not null,
	card_verification_value varchar(3) not null,
	expiration_date date not null,
	constraint payment_method_credit_card_payment_method_id_fk
		foreign key (payment_method_id) references payment_method (id)
);

create table payment_method_paypal
(
	payment_method_id int auto_increment
		primary key,
	token varchar(256) not null,
	constraint payment_method_paypal_token_uindex
		unique (token),
	constraint payment_method_paypal_payment_method_id_fk
		foreign key (payment_method_id) references payment_method (id)
);

create table product
(
	id int auto_increment
		primary key,
	name varchar(128) not null,
	description varchar(512) not null,
	quantity int default 0 not null,
	price decimal default 0 not null
);

create table order_product
(
	id int auto_increment
		primary key,
	order_id int not null,
	product_id int not null,
	quantity int default 0 not null,
	constraint order_id
		unique (order_id, product_id),
	constraint order_product_order_id_fk
		foreign key (order_id) references `order` (id),
	constraint order_product_product_id_fk
		foreign key (product_id) references product (id)
);

create index product_name_index
	on product (name);

create table product_category
(
	id int auto_increment
		primary key,
	product_id int not null,
	category_id int not null,
	constraint product_category_category_id_fk
		foreign key (category_id) references category (id),
	constraint product_category_product_id_fk
		foreign key (product_id) references product (id)
);

create table product_image
(
	id int auto_increment
		primary key,
	product_id int not null,
	image_id int not null,
	constraint product_image_image_id_fk
		foreign key (image_id) references image (id),
	constraint product_image_product_id_fk
		foreign key (product_id) references product (id)
);

create table role
(
	id int auto_increment
		primary key,
	name varchar(128) not null,
	constraint role_name_uindex
		unique (name)
);

create table user
(
	id int auto_increment
		primary key,
	role_id int not null,
	email_address varchar(320) not null,
	first_name varchar(80) not null,
	last_name varchar(80) null,
	password_hash varchar(128) not null,
	enabled tinyint(1) default 1 not null,
	verified_at timestamp null,
	created_at timestamp default CURRENT_TIMESTAMP not null,
	constraint user_email_address_uindex
		unique (email_address),
	constraint user_role_id_fk
		foreign key (role_id) references role (id)
);

create table invoice_user
(
	invoice_id int not null
		primary key,
	user_id int not null,
	constraint invoice_user_invoice_id_fk
		foreign key (invoice_id) references invoice (id),
	constraint invoice_user_user_id_fk
		foreign key (user_id) references user (id)
);

create table user_address
(
	id int auto_increment
		primary key,
	user_id int not null,
	address_id int not null,
	type varchar(128) not null,
	constraint user_address_address_id_uindex
		unique (address_id),
	constraint user_address_type_uindex
		unique (user_id, type),
	constraint user_address_address_id_fk
		foreign key (address_id) references address (id),
	constraint user_address_user_id_fk
		foreign key (user_id) references user (id)
);

create table user_payment_method
(
	id int auto_increment
		primary key,
	user_id int not null,
	payment_method_id int not null,
	constraint user_payment_method_payment_method_id_uindex
		unique (payment_method_id),
	constraint user_payment_method_payment_method_id_fk
		foreign key (payment_method_id) references payment_method (id),
	constraint user_payment_method_user_id_fk
		foreign key (user_id) references user (id)
);

create table wishlist
(
	id int auto_increment
		primary key,
	user_id int not null,
	name varchar(128) not null,
	constraint user_id
		unique (user_id, name),
	constraint wishlist_user_id_fk
		foreign key (user_id) references user (id)
);

create index wishlist_name_index
	on wishlist (name);

create table wishlist_product
(
	id int auto_increment
		primary key,
	wishlist_id int not null,
	product_id int not null,
	constraint wishlist_product_product_id_fk
		foreign key (product_id) references product (id),
	constraint wishlist_product_wishlist_id_fk
		foreign key (wishlist_id) references wishlist (id)
);

