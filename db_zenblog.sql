create table if not exists post_tags
(
	id int auto_increment
		primary key,
	tag_id int null,
	post_id int null,
	created_by varchar(255) null,
	created_date datetime(6) null,
	last_modified_by varchar(255) null,
	last_modified_date datetime(6) null
);

create table if not exists posts
(
	id int auto_increment
		primary key,
	title varchar(500) not null,
	content text null,
	created datetime(6) null,
	creator varchar(255) null,
	description varchar(255) null,
	is_public bit not null,
	updated datetime(6) null,
	updater varchar(255) null,
	view_count int null
);

create table if not exists roles
(
	id int auto_increment
		primary key,
	name varchar(255) not null,
	created_by varchar(255) null,
	created_date datetime(6) null,
	last_modified_by varchar(255) null,
	last_modified_date datetime(6) null
);

create table if not exists tags
(
	id int auto_increment
		primary key,
	name varchar(255) not null,
	created_by varchar(255) null,
	created_date datetime(6) null,
	last_modified_by varchar(255) null,
	last_modified_date datetime(6) null
);

create table if not exists user_roles
(
	id int auto_increment
		primary key,
	role_id int not null,
	user_id int not null,
	created_by varchar(255) null,
	created_date datetime(6) null,
	last_modified_by varchar(255) null,
	last_modified_date datetime(6) null
);

create table if not exists users
(
	id int auto_increment
		primary key,
	login_id varchar(255) not null,
	password varchar(255) not null,
	email varchar(255) null,
	last_name varchar(255) null,
	first_name varchar(255) null,
	birth_date datetime(6) null,
	address varchar(255) null,
	phone_number varchar(255) null,
	created_by varchar(255) null,
	created_date datetime(6) null,
	last_modified_by varchar(255) null,
	last_modified_date datetime(6) null
);

INSERT INTO db_zenblog.users (id, login_id, password, email, last_name, first_name, birth_date, address, phone_number, created_by, created_date, last_modified_by, last_modified_date) VALUES (1, 'admin', '$2a$10$/kiOmNgtVknPr/uRJAeaYe7nGP6/tGygdzuvANBNdChrEy6I7nFca', null, 'long', 'dang', null, null, null, 'system', '2023-01-11 13:59:52.044000', 'system', '2023-01-11 13:59:52.044000');

INSERT INTO db_zenblog.roles (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (1, 'ADMIN', 'system', '2023-01-11 13:59:51.918000', 'system', '2023-01-11 13:59:51.918000');
INSERT INTO db_zenblog.roles (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (2, 'MEMBER', 'system', '2023-01-11 13:59:51.933000', 'system', '2023-01-11 13:59:51.933000');
INSERT INTO db_zenblog.roles (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (3, 'GUEST', 'system', '2023-01-11 13:59:51.935000', 'system', '2023-01-11 13:59:51.935000');

INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (1, 'IT Book', 'system', '2023-01-11 16:42:13.463000', 'system', '2023-01-11 16:42:13.463000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (2, 'BA Book', 'system', '2023-01-15 07:30:48', '1', '2023-01-15 10:18:56.434000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (6, 'Book System', 'system', '2023-01-15 07:40:22.453000', '1', '2023-01-15 10:13:06.466000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (7, 'TAG 2', 'system', '2023-01-15 07:40:22.481000', 'system', '2023-01-15 07:40:22.481000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (8, 'TAG 3', 'system', '2023-01-15 07:40:22.488000', 'system', '2023-01-15 07:40:22.488000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (9, 'TAG 4', 'system', '2023-01-15 07:40:22.495000', 'system', '2023-01-15 07:40:22.495000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (10, 'TAG 5', 'system', '2023-01-15 07:40:22.501000', 'system', '2023-01-15 07:40:22.501000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (11, 'TAG 6', 'system', '2023-01-15 07:40:22.508000', 'system', '2023-01-15 07:40:22.508000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (12, 'TAG 7', 'system', '2023-01-15 07:40:22.514000', 'system', '2023-01-15 07:40:22.514000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (13, 'TAG 8', 'system', '2023-01-15 07:40:22.521000', 'system', '2023-01-15 07:40:22.521000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (14, 'TAG 9', 'system', '2023-01-15 07:40:22.528000', 'system', '2023-01-15 07:40:22.528000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (17, 'TAG 12', 'system', '2023-01-15 07:40:22.550000', 'system', '2023-01-15 07:40:22.550000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (18, 'TAG 13', 'system', '2023-01-15 07:40:22.558000', 'system', '2023-01-15 07:40:22.558000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (19, 'TAG 14', 'system', '2023-01-15 07:40:22.565000', 'system', '2023-01-15 07:40:22.565000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (20, 'TAG 15', 'system', '2023-01-15 07:40:22.572000', 'system', '2023-01-15 07:40:22.572000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (21, 'TAG 16', 'system', '2023-01-15 07:40:22.580000', 'system', '2023-01-15 07:40:22.580000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (22, 'TAG 17', 'system', '2023-01-15 07:40:22.587000', 'system', '2023-01-15 07:40:22.587000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (23, 'TAG 18', 'system', '2023-01-15 07:40:22.594000', 'system', '2023-01-15 07:40:22.594000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (24, 'TAG 19', 'system', '2023-01-15 07:40:22.601000', 'system', '2023-01-15 07:40:22.601000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (25, 'TAG 20', 'system', '2023-01-15 07:40:22.607000', 'system', '2023-01-15 07:40:22.607000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (26, 'TAG 21', 'system', '2023-01-15 07:40:22.615000', 'system', '2023-01-15 07:40:22.615000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (27, 'TAG 22', 'system', '2023-01-15 07:40:22.623000', 'system', '2023-01-15 07:40:22.623000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (28, 'TAG 23', 'system', '2023-01-15 07:40:22.630000', 'system', '2023-01-15 07:40:22.630000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (29, 'TAG 24', 'system', '2023-01-15 07:40:22.638000', 'system', '2023-01-15 07:40:22.638000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (30, 'TAG 25', 'system', '2023-01-15 07:40:22.647000', 'system', '2023-01-15 07:40:22.647000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (31, 'TAG 26', 'system', '2023-01-15 07:40:22.654000', 'system', '2023-01-15 07:40:22.654000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (32, 'TAG 27', 'system', '2023-01-15 07:40:22.661000', 'system', '2023-01-15 07:40:22.661000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (33, 'TAG 28', 'system', '2023-01-15 07:40:22.667000', 'system', '2023-01-15 07:40:22.667000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (34, 'TAG 29', 'system', '2023-01-15 07:40:22.674000', 'system', '2023-01-15 07:40:22.674000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (35, 'TAG 30', 'system', '2023-01-15 07:40:22.681000', 'system', '2023-01-15 07:40:22.681000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (36, 'TAG 31', 'system', '2023-01-15 07:40:22.688000', 'system', '2023-01-15 07:40:22.688000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (37, 'TAG 32', 'system', '2023-01-15 07:40:22.695000', 'system', '2023-01-15 07:40:22.695000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (38, 'TAG 33', 'system', '2023-01-15 07:40:22.701000', 'system', '2023-01-15 07:40:22.701000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (39, 'TAG 34', 'system', '2023-01-15 07:40:22.708000', 'system', '2023-01-15 07:40:22.708000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (40, 'TAG 35', 'system', '2023-01-15 07:40:22.714000', 'system', '2023-01-15 07:40:22.714000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (41, 'TAG 36', 'system', '2023-01-15 07:40:22.720000', 'system', '2023-01-15 07:40:22.720000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (42, 'TAG 37', 'system', '2023-01-15 07:40:22.726000', 'system', '2023-01-15 07:40:22.726000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (43, 'TAG 38', 'system', '2023-01-15 07:40:22.733000', 'system', '2023-01-15 07:40:22.733000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (44, 'TAG 39', 'system', '2023-01-15 07:40:22.739000', 'system', '2023-01-15 07:40:22.739000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (45, 'TAG 40', 'system', '2023-01-15 07:40:22.745000', 'system', '2023-01-15 07:40:22.745000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (46, 'TAG 41', 'system', '2023-01-15 07:40:22.751000', 'system', '2023-01-15 07:40:22.751000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (47, 'TAG 42', 'system', '2023-01-15 07:40:22.758000', 'system', '2023-01-15 07:40:22.758000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (48, 'TAG 43', 'system', '2023-01-15 07:40:22.763000', 'system', '2023-01-15 07:40:22.763000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (49, 'TAG 44', 'system', '2023-01-15 07:40:22.770000', 'system', '2023-01-15 07:40:22.770000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (50, 'TAG 45', 'system', '2023-01-15 07:40:22.776000', 'system', '2023-01-15 07:40:22.776000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (51, 'TAG 46', 'system', '2023-01-15 07:40:22.783000', 'system', '2023-01-15 07:40:22.783000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (52, 'TAG 47', 'system', '2023-01-15 07:40:22.790000', 'system', '2023-01-15 07:40:22.790000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (53, 'TAG 48', 'system', '2023-01-15 07:40:22.797000', 'system', '2023-01-15 07:40:22.797000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (54, 'TAG 49', 'system', '2023-01-15 07:40:22.805000', 'system', '2023-01-15 07:40:22.805000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (55, 'TAG 50', 'system', '2023-01-15 07:40:22.812000', 'system', '2023-01-15 07:40:22.812000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (56, 'TAG 51', 'system', '2023-01-15 07:40:22.819000', 'system', '2023-01-15 07:40:22.819000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (57, 'TAG 52', 'system', '2023-01-15 07:40:22.826000', 'system', '2023-01-15 07:40:22.826000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (58, 'TAG 53', 'system', '2023-01-15 07:40:22.833000', 'system', '2023-01-15 07:40:22.833000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (59, 'TAG 54', 'system', '2023-01-15 07:40:22.840000', 'system', '2023-01-15 07:40:22.840000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (60, 'TAG 55', 'system', '2023-01-15 07:40:22.847000', 'system', '2023-01-15 07:40:22.847000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (61, 'TAG 56', 'system', '2023-01-15 07:40:22.854000', 'system', '2023-01-15 07:40:22.854000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (62, 'TAG 57', 'system', '2023-01-15 07:40:22.860000', 'system', '2023-01-15 07:40:22.860000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (63, 'TAG 58', 'system', '2023-01-15 07:40:22.869000', 'system', '2023-01-15 07:40:22.869000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (64, 'TAG 59', 'system', '2023-01-15 07:40:22.877000', 'system', '2023-01-15 07:40:22.877000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (65, 'TAG 60', 'system', '2023-01-15 07:40:22.884000', 'system', '2023-01-15 07:40:22.884000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (66, 'TAG 61', 'system', '2023-01-15 07:40:22.889000', 'system', '2023-01-15 07:40:22.889000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (67, 'TAG 62', 'system', '2023-01-15 07:40:22.897000', 'system', '2023-01-15 07:40:22.897000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (68, 'TAG 63', 'system', '2023-01-15 07:40:22.904000', 'system', '2023-01-15 07:40:22.904000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (69, 'TAG 64', 'system', '2023-01-15 07:40:22.911000', 'system', '2023-01-15 07:40:22.911000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (70, 'TAG 65', 'system', '2023-01-15 07:40:22.917000', 'system', '2023-01-15 07:40:22.917000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (71, 'TAG 66', 'system', '2023-01-15 07:40:22.923000', 'system', '2023-01-15 07:40:22.923000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (72, 'TAG 67', 'system', '2023-01-15 07:40:22.929000', 'system', '2023-01-15 07:40:22.929000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (73, 'TAG 68', 'system', '2023-01-15 07:40:22.934000', 'system', '2023-01-15 07:40:22.934000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (74, 'TAG 69', 'system', '2023-01-15 07:40:22.939000', 'system', '2023-01-15 07:40:22.939000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (75, 'TAG 70', 'system', '2023-01-15 07:40:22.944000', 'system', '2023-01-15 07:40:22.944000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (76, 'TAG 71', 'system', '2023-01-15 07:40:22.950000', 'system', '2023-01-15 07:40:22.950000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (77, 'TAG 72', 'system', '2023-01-15 07:40:22.955000', 'system', '2023-01-15 07:40:22.955000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (78, 'TAG 73', 'system', '2023-01-15 07:40:22.961000', 'system', '2023-01-15 07:40:22.961000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (79, 'TAG 74', 'system', '2023-01-15 07:40:22.966000', 'system', '2023-01-15 07:40:22.966000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (80, 'TAG 75', 'system', '2023-01-15 07:40:22.972000', 'system', '2023-01-15 07:40:22.972000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (81, 'TAG 76', 'system', '2023-01-15 07:40:22.978000', 'system', '2023-01-15 07:40:22.978000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (82, 'TAG 77', 'system', '2023-01-15 07:40:22.985000', 'system', '2023-01-15 07:40:22.985000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (83, 'TAG 78', 'system', '2023-01-15 07:40:22.991000', 'system', '2023-01-15 07:40:22.991000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (84, 'TAG 79', 'system', '2023-01-15 07:40:22.997000', 'system', '2023-01-15 07:40:22.997000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (85, 'TAG 80', 'system', '2023-01-15 07:40:23.004000', 'system', '2023-01-15 07:40:23.004000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (86, 'TAG 81', 'system', '2023-01-15 07:40:23.011000', 'system', '2023-01-15 07:40:23.011000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (87, 'TAG 82', 'system', '2023-01-15 07:40:23.018000', 'system', '2023-01-15 07:40:23.018000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (88, 'TAG 83', 'system', '2023-01-15 07:40:23.024000', 'system', '2023-01-15 07:40:23.024000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (89, 'TAG 84', 'system', '2023-01-15 07:40:23.032000', 'system', '2023-01-15 07:40:23.032000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (90, 'TAG 85', 'system', '2023-01-15 07:40:23.038000', 'system', '2023-01-15 07:40:23.038000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (91, 'TAG 86', 'system', '2023-01-15 07:40:23.045000', 'system', '2023-01-15 07:40:23.045000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (92, 'TAG 87', 'system', '2023-01-15 07:40:23.052000', 'system', '2023-01-15 07:40:23.052000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (93, 'TAG 88', 'system', '2023-01-15 07:40:23.058000', 'system', '2023-01-15 07:40:23.058000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (94, 'TAG 89', 'system', '2023-01-15 07:40:23.065000', 'system', '2023-01-15 07:40:23.065000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (95, 'TAG 90', 'system', '2023-01-15 07:40:23.071000', 'system', '2023-01-15 07:40:23.071000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (96, 'TAG 91', 'system', '2023-01-15 07:40:23.077000', 'system', '2023-01-15 07:40:23.077000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (97, 'TAG 92', 'system', '2023-01-15 07:40:23.083000', 'system', '2023-01-15 07:40:23.083000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (98, 'TAG 93', 'system', '2023-01-15 07:40:23.090000', 'system', '2023-01-15 07:40:23.090000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (99, 'TAG 94', 'system', '2023-01-15 07:40:23.095000', 'system', '2023-01-15 07:40:23.095000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (100, 'TAG 95', 'system', '2023-01-15 07:40:23.101000', 'system', '2023-01-15 07:40:23.101000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (101, 'TAG 96', 'system', '2023-01-15 07:40:23.108000', 'system', '2023-01-15 07:40:23.108000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (102, 'TAG 97', 'system', '2023-01-15 07:40:23.114000', 'system', '2023-01-15 07:40:23.114000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (103, 'TAG 98', 'system', '2023-01-15 07:40:23.120000', 'system', '2023-01-15 07:40:23.120000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (104, 'TAG 99', 'system', '2023-01-15 07:40:23.132000', 'system', '2023-01-15 07:40:23.132000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (115, 'TAG 501', 'system', '2023-01-15 07:40:23.197000', '1', '2023-01-16 22:08:53.114000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (116, 'TAG 511', 'system', '2023-01-15 07:40:23.203000', '1', '2023-01-16 22:09:15.252000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (117, 'TAG 112', 'system', '2023-01-15 07:40:23.209000', 'system', '2023-01-15 07:40:23.209000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (118, 'TAG 113', 'system', '2023-01-15 07:40:23.216000', 'system', '2023-01-15 07:40:23.216000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (119, 'TAG 114', 'system', '2023-01-15 07:40:23.222000', 'system', '2023-01-15 07:40:23.222000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (120, 'TAG 115', 'system', '2023-01-15 07:40:23.231000', 'system', '2023-01-15 07:40:23.231000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (121, 'TAG 116', 'system', '2023-01-15 07:40:23.237000', 'system', '2023-01-15 07:40:23.237000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (122, 'TAG 117', 'system', '2023-01-15 07:40:23.247000', 'system', '2023-01-15 07:40:23.247000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (123, 'TAG 118', 'system', '2023-01-15 07:40:23.254000', 'system', '2023-01-15 07:40:23.254000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (124, 'TAG 119', 'system', '2023-01-15 07:40:23.262000', 'system', '2023-01-15 07:40:23.262000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (125, 'TAG 120', 'system', '2023-01-15 07:40:23.275000', 'system', '2023-01-15 07:40:23.275000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (126, 'TAG 121', 'system', '2023-01-15 07:40:23.283000', 'system', '2023-01-15 07:40:23.283000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (127, 'TAG 122', 'system', '2023-01-15 07:40:23.297000', 'system', '2023-01-15 07:40:23.297000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (128, 'TAG 123', 'system', '2023-01-15 07:40:23.307000', 'system', '2023-01-15 07:40:23.307000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (129, 'TAG 124', 'system', '2023-01-15 07:40:23.315000', 'system', '2023-01-15 07:40:23.315000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (130, 'TAG 125', 'system', '2023-01-15 07:40:23.324000', 'system', '2023-01-15 07:40:23.324000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (131, 'TAG 126', 'system', '2023-01-15 07:40:23.330000', 'system', '2023-01-15 07:40:23.330000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (132, 'TAG 127', 'system', '2023-01-15 07:40:23.336000', 'system', '2023-01-15 07:40:23.336000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (133, 'TAG 128', 'system', '2023-01-15 07:40:23.343000', 'system', '2023-01-15 07:40:23.343000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (134, 'TAG 129', 'system', '2023-01-15 07:40:23.350000', 'system', '2023-01-15 07:40:23.350000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (135, 'TAG 130', 'system', '2023-01-15 07:40:23.357000', 'system', '2023-01-15 07:40:23.357000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (136, 'TAG 131', 'system', '2023-01-15 07:40:23.370000', 'system', '2023-01-15 07:40:23.370000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (137, 'TAG 132', 'system', '2023-01-15 07:40:23.377000', 'system', '2023-01-15 07:40:23.377000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (138, 'TAG 133', 'system', '2023-01-15 07:40:23.384000', 'system', '2023-01-15 07:40:23.384000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (139, 'TAG 134', 'system', '2023-01-15 07:40:23.391000', 'system', '2023-01-15 07:40:23.391000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (140, 'TAG 135', 'system', '2023-01-15 07:40:23.399000', 'system', '2023-01-15 07:40:23.399000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (141, 'TAG 136', 'system', '2023-01-15 07:40:23.405000', 'system', '2023-01-15 07:40:23.405000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (142, 'TAG 137', 'system', '2023-01-15 07:40:23.413000', 'system', '2023-01-15 07:40:23.413000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (143, 'TAG 138', 'system', '2023-01-15 07:40:23.419000', 'system', '2023-01-15 07:40:23.419000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (144, 'TAG 139', 'system', '2023-01-15 07:40:23.426000', 'system', '2023-01-15 07:40:23.426000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (145, 'TAG 140', 'system', '2023-01-15 07:40:23.433000', 'system', '2023-01-15 07:40:23.433000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (146, 'TAG 141', 'system', '2023-01-15 07:40:23.440000', 'system', '2023-01-15 07:40:23.440000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (147, 'TAG 142', 'system', '2023-01-15 07:40:23.448000', 'system', '2023-01-15 07:40:23.448000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (148, 'TAG 143', 'system', '2023-01-15 07:40:23.453000', 'system', '2023-01-15 07:40:23.453000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (149, 'TAG 144', 'system', '2023-01-15 07:40:23.461000', 'system', '2023-01-15 07:40:23.461000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (150, 'TAG 145', 'system', '2023-01-15 07:40:23.467000', 'system', '2023-01-15 07:40:23.467000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (151, 'TAG 146', 'system', '2023-01-15 07:40:23.473000', 'system', '2023-01-15 07:40:23.473000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (152, 'TAG 147', 'system', '2023-01-15 07:40:23.480000', 'system', '2023-01-15 07:40:23.480000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (153, 'TAG 148', 'system', '2023-01-15 07:40:23.487000', 'system', '2023-01-15 07:40:23.487000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (154, 'TAG 149', 'system', '2023-01-15 07:40:23.494000', 'system', '2023-01-15 07:40:23.494000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (155, 'TAG 150', 'system', '2023-01-15 07:40:23.501000', 'system', '2023-01-15 07:40:23.501000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (156, 'TAG 151', 'system', '2023-01-15 07:40:23.509000', 'system', '2023-01-15 07:40:23.509000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (157, 'TAG 152', 'system', '2023-01-15 07:40:23.515000', 'system', '2023-01-15 07:40:23.515000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (158, 'X1', '1', '2023-01-15 12:57:52.513000', '1', '2023-01-15 12:57:52.513000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (159, 'X2', '1', '2023-01-15 13:00:35.530000', '1', '2023-01-15 13:00:35.530000');
INSERT INTO db_zenblog.tags (id, name, created_by, created_date, last_modified_by, last_modified_date) VALUES (161, 'X5', '1', '2023-01-15 13:03:49.382000', '1', '2023-01-15 13:03:49.382000');

INSERT INTO db_zenblog.user_roles (id, role_id, user_id, created_by, created_date, last_modified_by, last_modified_date) VALUES (1, 1, 1, null, '2023-08-03 08:34:17', '1', '2023-08-03 08:34:25');