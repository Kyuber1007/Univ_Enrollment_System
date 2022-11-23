DROP database if exists DB2017029952;
Create database if not exists DB2017029952;
use DB2017029952;

create table course (
course_id varchar(20),
name varchar(20),
credit varchar(5),
primary key(course_id));

create table course_grade(
course_id varchar(20),
average_grade double, 
diff double, 
foreign key(course_id) references course (course_id)
);

create table major(
major_id varchar(20),
name varchar(20),
primary key(major_id)
);

create table building(
building_id varchar(20),
name varchar(20),
admin varchar(20),
rooms varchar(20),
primary key(building_id)
);

create table room(
room_id varchar(20),
building_id varchar(20),
occupancy varchar(10),
primary key(room_id),
foreign key(building_id) references building (building_id)
);

create table lecturer(
lecturer_id varchar(20),
name varchar(30),
major_id varchar(20),
primary key(lecturer_id),
foreign key(major_id) references major (major_id)
);

create table student(
student_id varchar(20),
password varchar(20),
name varchar(10),
sex varchar(8),
major_id varchar(20),
lecturer_id varchar(20),
year varchar(8),
primary key(student_id),
foreign key(major_id) references major (major_id),
foreign key(lecturer_id) references lecturer (lecturer_id)
);
alter table student add status varchar(5) default "재학";


create table credits(
credits_id varchar(10),
student_id varchar(20),
course_id varchar(10),
year varchar(8),
grade varchar(5),
primary key(credits_id),
foreign key(student_id) references student (student_id),
foreign key(course_id) references course (course_id)
);

create table class(
class_id varchar(20),
class_no varchar(8),
course_id varchar(20),
name varchar(20),
major_id varchar(20),
year varchar(8),
credit varchar(3),
lecturer_id varchar(20),
person_max varchar(3),
opened varchar(20),
room_id varchar(20),
primary key(class_id),
foreign key(course_id) references course (course_id),
foreign key(lecturer_id) references lecturer (lecturer_id)
);

create table time(
time_id varchar(10),
class_id varchar(20),
period varchar(10),
begin varchar(30),
end varchar(30),
primary key(time_id),
foreign key(class_id) references class (class_id)
);

create table admin(
	admin_id varchar(20),
    password varchar(20),
    admin_code varchar(10),
    primary key(admin_id)
);

insert admin value ("test001", "test1234!", '1234');
insert admin value ("test", "test", '1234');

create table enrollment(
    student_id varchar(20),
    class_no varchar(20),
    credit varchar(3)
);

insert into enrollment values ('2018003125','10003','3'),
('2021030128','10003','3'),
('2021030303','10003','3');

create table wanted_enrollment(
    student_id varchar(20),
    class_no varchar(20),
    credit varchar(3)
);