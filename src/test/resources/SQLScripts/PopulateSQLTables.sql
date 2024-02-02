use university_test;

insert into buildings(id, building_name, address_id) values
(3, 'Simpson Hall', 3);

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

insert into courses(course_detail_id, term_id, room_id) values
(1, 1, 3),
(2, 1, 4),
(3, 2, 3);

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