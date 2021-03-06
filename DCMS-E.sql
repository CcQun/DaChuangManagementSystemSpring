DROP SCHEMA DCMS;

CREATE SCHEMA DCMS DEFAULT CHARACTER SET utf8mb4;

USE DCMS;

/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/6/14 10:04:15                           */
/*==============================================================*/


drop table if exists APPLY_BLINK;

drop table if exists APPLY_DIRECT;

drop table if exists APPLY_PROJECT;

drop table if exists BLINK;

drop table if exists PROJECT;

drop table if exists STUDENT;

drop table if exists TEACHER;

drop table if exists TEAM;

/*==============================================================*/
/* Table: APPLY_BLINK                                           */
/*==============================================================*/
create table APPLY_BLINK
(
    Blink_Number         int not null,
    Student_Number       int not null,
    Blink_Approval       int,
    primary key (Blink_Number, Student_Number)
);

/*==============================================================*/
/* Table: APPLY_DIRECT                                          */
/*==============================================================*/
create table APPLY_DIRECT
(
    Project_Number       int not null,
    Teacher_Number       int not null,
    Direct_Approval      int,
    primary key (Project_Number, Teacher_Number)
);

/*==============================================================*/
/* Table: APPLY_PROJECT                                         */
/*==============================================================*/
create table APPLY_PROJECT
(
    Project_Number       int not null,
    Student_Number       int not null,
    Project_Approval     int,
    primary key (Project_Number, Student_Number)
);

/*==============================================================*/
/* Table: BLINK                                                 */
/*==============================================================*/
create table BLINK
(
    Blink_Number         int not null,
    Student_Number       int not null,
    Blink_Title          char(100) not null,
    Blink_Content        text not null,
    Blink_College        varchar(50) not null,
    Blink_Field          varchar(50) not null,
    Blink_State          int,
    Create_Time          datetime not null,
    primary key (Blink_Number)
);

/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
create table PROJECT
(
    Project_Number       int not null,
    Create_Teacher_Number int,
    Direct_Teacher_Number int,
    Create_Student_Number int,
    Project_Name         char(100) not null,
    Project_Description  text,
    Project_College      char(50) not null,
    Project_Field        varchar(50) not null,
    Project_State        int,
    Create_Time          datetime not null,
    primary key (Project_Number)
);

/*==============================================================*/
/* Table: STUDENT                                               */
/*==============================================================*/
create table STUDENT
(
    Student_Number       int not null,
    Student_Name         char(50) not null,
    Student_Gender       char(1) not null,
    Student_College      char(50) not null,
    Enrollment_Year      date not null,
    Major                char(50) not null,
    Student_Introduction text,
    Student_Password     char(200) not null,
    primary key (Student_Number)
);

/*==============================================================*/
/* Table: TEACHER                                               */
/*==============================================================*/
create table TEACHER
(
    Teacher_Number       int not null,
    Teacher_Name         char(50) not null,
    Teacher_Gender       char(1) not null,
    Teacher_College      char(50) not null,
    Teacher_Introduction text,
    Teacher_Password     char(200) not null,
    primary key (Teacher_Number)
);

/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/
create table TEAM
(
    Project_Number       int not null,
    Student_Number       int not null,
    Group_Description    text,
    Join_Time            datetime not null,
    primary key (Project_Number, Student_Number)
);

alter table APPLY_BLINK add constraint FK_APPLY_BLINK foreign key (Blink_Number)
    references BLINK (Blink_Number) on delete restrict on update restrict;

alter table APPLY_BLINK add constraint FK_APPLY_BLINK2 foreign key (Student_Number)
    references STUDENT (Student_Number) on delete restrict on update restrict;

alter table APPLY_DIRECT add constraint FK_APPLY_DIRECT foreign key (Project_Number)
    references PROJECT (Project_Number) on delete restrict on update restrict;

alter table APPLY_DIRECT add constraint FK_APPLY_DIRECT2 foreign key (Teacher_Number)
    references TEACHER (Teacher_Number) on delete restrict on update restrict;

alter table APPLY_PROJECT add constraint FK_APPLY_PROJECT foreign key (Project_Number)
    references PROJECT (Project_Number) on delete restrict on update restrict;

alter table APPLY_PROJECT add constraint FK_APPLY_PROJECT2 foreign key (Student_Number)
    references STUDENT (Student_Number) on delete restrict on update restrict;

alter table BLINK add constraint FK_issue foreign key (Student_Number)
    references STUDENT (Student_Number) on delete restrict on update restrict;

alter table PROJECT add constraint FK_direct foreign key (Direct_Teacher_Number)
    references TEACHER (Teacher_Number) on delete restrict on update restrict;

alter table PROJECT add constraint FK_screate foreign key (Create_Student_Number)
    references STUDENT (Student_Number) on delete restrict on update restrict;

alter table PROJECT add constraint FK_tcreate foreign key (Create_Teacher_Number)
    references TEACHER (Teacher_Number) on delete restrict on update restrict;

alter table TEAM add constraint FK_TEAM foreign key (Project_Number)
    references PROJECT (Project_Number) on delete restrict on update restrict;

alter table TEAM add constraint FK_TEAM2 foreign key (Student_Number)
    references STUDENT (Student_Number) on delete restrict on update restrict;



/*==============================================================*/
/* Init Database Data                                           */
/*==============================================================*/

insert into STUDENT
values (17301091, 'CuiChaoqun', 'M', 'school of software', DATE('2017-09-01'), 'software engineering',
    'CuiChaoqun:A student from school of software.','E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301097, 'LiChengyao', 'M', 'school of software', DATE('2017-09-01'), 'software engineering',
 'LiChengyao:A student from school of software.','E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301115, 'ZhangYumeng', 'F', 'school of software', DATE('2017-09-01'), 'software engineering',
 'ZhangYumeng:A student from school of software.','E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301126, 'LiHaoran', 'M', 'school of software', DATE('2017-09-01'), 'software engineering',
'LiHaoran:A student from school of software.','E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301113, 'ZhangYing', 'F', 'school of software', DATE('2017-09-01'), 'software engineering',
'ZhangYing:A student from school of software.','E10ADC3949BA59ABBE56E057F20F883E');

insert into TEACHER
values (10000001, 'LiYu', 'M', 'school of software', 'LiYu:A teacher from school of software.',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into TEACHER
values (10000002, 'ZengLigang', 'M', 'school of software', 'ZengLigang:A teacher from school of software.',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into BLINK
values (1, 17301091, 'Train monitoring system based on deep learning', 'The deep learning algorithm is used to detect the bearing fault and monitor the driver.',
        'school of software', 'Deep learning', 0, DATE('2019-05-21 21:12:22'));

insert into BLINK
values (2, 17301097, 'Medical referral system', 'Real-time medical scheme recommendation for patients.',
        'School of management', 'medical', 0, DATE('2020-11-20 05:30:50'));

insert into BLINK
values (3, 17301091, 'Based on the Spring Boot DaChuang management system', 'Using Spring Boot to implement the reconfiguration of the management system.',
        'school of software', 'JavaEE', 0, DATE('2019-08-12 08:50:15'));

insert into BLINK
values (4, 17301091, 'Web exception request detection using logistic regression', 'NLP and logistic regression were used for dichotomy.',
        'school of software', 'Machine learning', 0, DATE('2020-06-02 07:31:22'));

insert into BLINK
values (5, 17301113, 'Contract management system based on Spring Boot and Vue', 'Using Spring Boot and Vue to automate contract management.',
        'school of software', 'The front-end development', 0, DATE('2018-07-12 16:37:33'));

insert into BLINK
values (6, 17301097, 'Train fault detection', 'Train fault detection using multidisciplinary techniques',
        'School of transportation', 'The transportation', 0, DATE('2018-11-12 14:15:29'));

insert into PROJECT
values (1,10000001,10000001,null,'Design and implementation of seckilling system','Develop and design using the latest technology','Software college','System development',0,DATE('2018-12-22 15:15:19'));

insert into PROJECT
values (2,10000001,10000001,null,'Unity based bejeweled game development','Develop bejeweled game using Unity','Software college','Game development',0,DATE('2020-05-12 19:25:31'));

insert into PROJECT
values (3,10000001,10000001,null,'Development of Wolf DLC based on unreal 5 engine','Use unreal 5 engine to develop Wolf''s new DLC','Software college','Game development',0,DATE('2019-06-18 15:35:31'));