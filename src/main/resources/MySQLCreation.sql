drop database if exists university;
create database if not exists university;

use university;

create table if not exists departments(
	id serial,
    department_name varchar(45) unique not null,
    primary key (id)
);

create table if not exists degree_programs(
	id serial,
    degree_program_name varchar(45) unique not null,
    total_credits smallint,
    department_id bigint unsigned,
    constraint fk_department_degree_programs foreign key (department_id) references departments (id) on update no action on delete cascade,
    primary key (id)
);

create table if not exists addresses(
	id serial,
    street text not null,
    city varchar(45) not null,
    state varchar(45) not null,
    country varchar(45) not null,
    zip_code int unsigned not null,
    primary key (id)
);

create table if not exists students(
	id serial,
	email varchar(45) unique not null,
    first_name varchar(45) not null,
    last_name varchar(45) not null,
    degree_program_id bigint unsigned,
    address_id bigint unsigned,
    constraint fk_students_degree_program foreign key (degree_program_id) references degree_programs(id) on update no action on delete cascade,
    constraint fk_students_address foreign key (address_id) references addresses (id) on update no action on delete no action,
    primary key (id)
);

create table if not exists test_results(
	id serial,
    score tinyint unsigned not null,
    subject varchar(25) not null,
    student_id bigint unsigned not null,
    constraint fk_student_test_results foreign key (student_id) references students (id) on update no action on delete cascade,
    check (0 <= score <= 100),
    primary key (id)
);

create table if not exists buildings(
	id bigint unsigned unique not null,
    building_name varchar(45) unique not null,
    constraint fk_building_address foreign key (id) references addresses (id) on update no action on delete cascade,
    primary key (id)
);

create table if not exists rooms(
	id serial,
    room_number varchar(45) not null,
    building_id bigint unsigned not null,
    constraint fk_building_rooms foreign key (building_id) references buildings (id) on update no action on delete cascade,
    primary key (id)
);

create table if not exists professors(
	id serial,
    first_name varchar(45) not null,
    last_name varchar(45) not null,
    department_id bigint unsigned,
    department_head tinyint(1) unsigned not null,
    office_id bigint unsigned unique not null,
    constraint fk_department_professors foreign key (department_id) references departments (id) on update no action on delete no action,
    constraint fk_professors_office foreign key (office_id) references rooms (id) on update no action on delete no action,
    primary key (id)
);

create table if not exists course_details(
	id serial,
    course_name varchar(45) unique not null,
    credits tinyint unsigned not null,
    department_id bigint unsigned,
	constraint fk_course_details_department foreign key (department_id) references departments (id) on update no action on delete cascade,
    primary key (id)
);

create table if not exists terms(
	id serial,
    term_name varchar(45) unique not null,
    start_date date not null,
    end_date date not null,
    primary key (id)
);

create table if not exists courses(
	id serial,
    course_detail_id bigint unsigned not null,
    term_id bigint unsigned,
    room_id bigint unsigned,
    constraint fk_course_course_details foreign key (course_detail_id) references course_details (id) on update no action on delete cascade,
    constraint fk_course_term_id foreign key (term_id) references terms (id) on update no action on delete cascade,
    constraint fk_course_room_id foreign key (room_id) references rooms (id) on update no action on delete no action,
    primary key (id)
);

create table if not exists course_professors(
	course_id bigint unsigned not null,
    professor_id bigint unsigned not null,
    constraint fk_course_professors_courses foreign key (course_id) references courses (id) on update no action on delete cascade,
    constraint fk_course_professors_professors foreign key (professor_id) references professors (id) on update no action on delete cascade
);

create table if not exists student_courses(
	student_id bigint unsigned not null,
    course_id bigint unsigned not null,
    constraint fk_student_course_students foreign key (student_id) references students (id) on update no action on delete cascade,
    constraint fk_student_course_courses foreign key (course_id) references courses (id) on update no action on delete cascade
);

create table if not exists times(
	id serial,
    day_of_the_week enum('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') not null,
	start_time time not null,
    end_time time not null,
    primary key (id)
);

create table if not exists course_times(
	time_id bigint unsigned not null,
    course_id bigint unsigned not null,
    constraint fk_course_times_time foreign key (time_id) references times (id) on update no action on delete no action,
    constraint fk_course_times_course foreign key (course_id) references courses (id) on update no action on delete cascade
);

insert into addresses(street, city, state, country, zip_code) values
('20 55th Ave', 'St. Paul', 'MN', 'USA', 443),
('20 75th Ave', 'Minneapolis', 'MN', 'USA', 444),
('20 95th Ave', 'Edina', 'MN', 'USA', 445);

insert into buildings(id, building_name) values
(3, 'Simpson Hall');

insert into rooms(room_number, building_id) values
('101A', 3),
('300', 3),
('400', 3),
('500', 3);

insert into departments(department_name) values
('Computer Science and Engineering'),
('Business');

insert into degree_programs(degree_program_name, total_credits, department_id) values
('Bachelor of Science in Computer Science', 120, 1),
('Bachelor of Science in Chemical Engineering', 130, 1),
('Bachelor of Science in Mechanical Engineering', 125, 1),
('Bachelor of Science in Marketing', 100, 2);

insert into students(email, first_name, last_name, degree_program_id, address_id) values
('ja@gmail.com','Jakob', 'Ableitner', 2, 1),
('bs@gmail.com','Bob', 'Smith', 4, 2),
('tt@hotmail.com','Timothy', 'Train', 1, 2);

insert into test_results(student_id, subject, score) values
(1, 'Math', 92),
(1, 'English', 75),
(2, 'Math', 86),
(3, 'Math', 70);

insert into professors(first_name, last_name, department_id, department_head, office_id) values
('Tim', 'Seyer', 1, 1, 1),
('Michael', 'Taylor', 2, 0, 2);

insert into course_details(course_name, credits, department_id) values
('Intro to Java', 3, 1),
('How to Build an SQL Query', 2, 1),
('Business for Beginers', 3, 2);

insert into terms(term_name, start_date, end_date) values ('Spring 2024', '2024-01-06', '2024-05-21');

insert into courses(course_detail_id, term_id, room_id) values
(1, 1, 3),
(2, 1, 4),
(3, 1, 3);

insert into course_professors(course_id, professor_id) values
(1, 1),
(2, 1),
(2, 2),
(3, 2);

insert into student_courses(student_id, course_id) values
(1, 1),
(1, 2),
(2, 1),
(2, 3);

insert into times(day_of_the_week, start_time, end_time) values
('Tuesday', '12:00', '12:45'),
('Thursday', '12:00', '12:45'),
('Wednesday', '10:15', '11:00');

insert into course_times(course_id, time_id) values
(1, 1),
(1, 2),
(2, 3),
(3, 3);