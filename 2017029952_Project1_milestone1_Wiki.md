# Project 1 Wiki

# **환경**

- OS: macOS Monterey (ver 12.6)
- M1 silicon (arm)

## 개발 환경

- IntelliJ IDEA 2022.2
- MySQL 8.0
- Tomcat 9.0.68
- SDK Amazon Corretto version 15.0.2
- Connector mysql-connector-java-8.0.20.

# Design

- 현재 user type에 따른 로그인 환경 구현 중입니다.
    - 따라서, DB 및 UI는 기획 중에 있습니다.
- UI 계획
    - 회원가입 시, 관리자와 사용자 둘 중 하나를 고를 수 있게 함
    - 로그인 시, 자동으로 관리자와 사용자를 분류해서 로그인 함
    - 관리자와 사용자는 공통 기능인 수업 검색을 이용할 수 있음
        - 권한의 차이로 구분되는 기능들은 버튼을 통해 구현할 예정
    - 개설 과목을 list 형식으로 관리하고, 검색 기능을 추가할 예정
    - 기본적인 U들은 기초 기능이 구현된 후 추가할 예정
- DB Schema는 주어진 예제를 따라서 진행할 계획
    - 웹의 기본틀을 기획한 후 db를 구현해나갈 예정
    - 수강신청을 위한 table 및 admin 관리를 위한 추가적인 table 구현

# SQL query

- to create database
    
    ```sql
    Drop database if exists univ_sys;
    Create Database if not exists univ_sys;
    use univ_sys; 
    ```
    
- create table
    
    ```sql
    CREATE TABLE table_name (
    attribute1 data_type1,
    attribute2 data_type2,
    ...
    
    PRIMARY KEY (attribute1)
    foreign keys (attribute2) references other_table (other_attribute)
    );
    
    Load data local infile 'filepath'
    alter table building
    fields terminated by ','
    ignore 1 rows;
    ```
    
- search course with course_id, , name
    
    ```sql
    select * 
    from course
    where course.name like formatmessage("%s%", $(searching_word))
    ```
    
- delete or create course
    
    ```sql
    delete from course
    where course_id = ''
    
    insert into course
    values (course_id, name, credit)
    ```
    
- inquire status of student
    
    ```sql
    select *
    from student
    where student_id = ""
    ```
    
- change status of student
    
    ```sql
    update student
    set $(attribute) = $(something)
    where condition
    ```
    
- inquire status of course
    
    ```sql
    select *
    from course
    where condition
    ```
    
- change status of course or class
    
    ```sql
    update course
    set $(attribute) = $(something)
    where condition
    
    update class
    set $(attribute) = $(something)
    where condition
    ```
    
- create class
    
    ```sql
    insert into class
    values (class attributes)
    ```
    
- delete class
    
    ```sql
    delete from class
    where condition
    ```
    
- OLAP part
    - 추후 진행할 예정

- 수강신청을 위한 table이 필요함. 수업 table의 class_id를 foreign key로 가지고 있는 table을 만들 예정
- class registration
    
    ```sql
    insert into student_class
    values (student_id, class_id)
    ```
    
- hope course enrollment
    
    ```sql
    insert into student_hope
    values (student_id, class_id)
    ```