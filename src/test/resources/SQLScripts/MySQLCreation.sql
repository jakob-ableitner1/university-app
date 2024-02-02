drop database if exists university_test;
create database if not exists university_test;

use university_test;

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

create table if not exists students(
	id serial,
	email varchar(45) unique not null,
    first_name varchar(45) not null,
    last_name varchar(45) not null,
    degree_program_id bigint unsigned,
    address_id bigint unsigned,
    constraint fk_students_degree_program foreign key (degree_program_id) references degree_programs(id) on update no action on delete cascade,
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
	id serial,
    building_name varchar(45) unique not null,
    address_id bigint unsigned,
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

create table if not exists courses(
	id serial,
    course_detail_id bigint unsigned not null,
    term_id bigint unsigned,
    room_id bigint unsigned,
    constraint fk_course_course_details foreign key (course_detail_id) references course_details (id) on update no action on delete cascade,
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