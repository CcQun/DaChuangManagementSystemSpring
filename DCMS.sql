DROP SCHEMA DCMS;

CREATE SCHEMA DCMS DEFAULT CHARACTER SET utf8mb4;

USE DCMS;

/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/5/18 8:03:45                            */
/*==============================================================*/


drop table if exists APPLY_BLINK;

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
    Blink_Number   int not null,
    Student_Number int not null,
    Blink_Approval int,
    primary key (Blink_Number, Student_Number)
);

/*==============================================================*/
/* Table: APPLY_PROJECT                                         */
/*==============================================================*/
create table APPLY_PROJECT
(
    Project_Number   int not null,
    Student_Number   int not null,
    Project_Approval int,
    primary key (Project_Number, Student_Number)
);

/*==============================================================*/
/* Table: BLINK                                                 */
/*==============================================================*/
create table BLINK
(
    Blink_Number   int         not null,
    Student_Number int         not null,
    Blink_Title    char(50)    not null,
    Blink_Content  text        not null,
    Blink_College  varchar(20) not null,
    Blink_Field    varchar(20) not null,
    Blink_State    int,
    Create_Time    datetime    not null,
    primary key (Blink_Number)
);

/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
create table PROJECT
(
    Project_Number        int         not null,
    Create_Teacher_Number int,
    Direct_Teacher_Number int,
    Create_Student_Number int,
    Project_Name          char(50)    not null,
    Project_Description   text,
    Project_College       char(20)    not null,
    Project_Field         varchar(20) not null,
    Project_State         int,
    Create_Time           datetime    not null,
    primary key (Project_Number)
);

/*==============================================================*/
/* Table: STUDENT                                               */
/*==============================================================*/
create table STUDENT
(
    Student_Number       int       not null,
    Student_Name         char(20)  not null,
    Student_Gender       char(1)   not null,
    Student_College      char(30)  not null,
    Enrollment_Year      date      not null,
    Major                char(20)  not null,
    Student_Introduction text,
    Student_Password     char(200) not null,
    primary key (Student_Number)
);

/*==============================================================*/
/* Table: TEACHER                                               */
/*==============================================================*/
create table TEACHER
(
    Teacher_Number       int       not null,
    Teacher_Name         char(20)  not null,
    Teacher_Gender       char(1)   not null,
    Teacher_College      char(30)  not null,
    Teacher_Introduction text,
    Teacher_Password     char(200) not null,
    primary key (Teacher_Number)
);

/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/
create table TEAM
(
    Project_Number    int      not null,
    Student_Number    int      not null,
    Group_Description text,
    Join_Time         datetime not null,
    primary key (Project_Number, Student_Number)
);

alter table APPLY_BLINK
    add constraint FK_APPLY_BLINK foreign key (Blink_Number)
        references BLINK (Blink_Number) on delete restrict on update restrict;

alter table APPLY_BLINK
    add constraint FK_APPLY_BLINK2 foreign key (Student_Number)
        references STUDENT (Student_Number) on delete restrict on update restrict;

alter table APPLY_PROJECT
    add constraint FK_APPLY_PROJECT foreign key (Project_Number)
        references PROJECT (Project_Number) on delete restrict on update restrict;

alter table APPLY_PROJECT
    add constraint FK_APPLY_PROJECT2 foreign key (Student_Number)
        references STUDENT (Student_Number) on delete restrict on update restrict;

alter table BLINK
    add constraint FK_issue foreign key (Student_Number)
        references STUDENT (Student_Number) on delete restrict on update restrict;

alter table PROJECT
    add constraint FK_direct foreign key (Direct_Teacher_Number)
        references TEACHER (Teacher_Number) on delete restrict on update restrict;

alter table PROJECT
    add constraint FK_screate foreign key (Create_Student_Number)
        references STUDENT (Student_Number) on delete restrict on update restrict;

alter table PROJECT
    add constraint FK_tcreate foreign key (Create_Teacher_Number)
        references TEACHER (Teacher_Number) on delete restrict on update restrict;

alter table TEAM
    add constraint FK_TEAM foreign key (Project_Number)
        references PROJECT (Project_Number) on delete restrict on update restrict;

alter table TEAM
    add constraint FK_TEAM2 foreign key (Student_Number)
        references STUDENT (Student_Number) on delete restrict on update restrict;



/*==============================================================*/
/* Init Database Data                                           */
/*==============================================================*/

insert into STUDENT
values (17301091, '崔超群', '男', '软件学院', DATE('2017-09-01'), '软件工程', '崔超群:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301093, '李程遥', '男', '软件学院', DATE('2017-09-01'), '软件工程', '李程遥:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301115, '张雨梦', '女', '软件学院', DATE('2017-09-01'), '软件工程', '张雨梦:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301126, '李浩冉', '男', '软件学院', DATE('2017-09-01'), '软件工程', '李浩冉:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301113, '张影', '女', '软件学院', DATE('2017-09-01'), '软件工程', '张影:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into TEACHER
values (10000001, '李宇', '男', '软件学院', '李宇:软件学院的一位老师。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into TEACHER
values (10000002, '曾立刚', '男', '软件学院', '曾立刚:软件学院的一位老师。',
        'E10ADC3949BA59ABBE56E057F20F883E');