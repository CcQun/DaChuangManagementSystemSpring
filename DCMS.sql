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
values (17301091, '崔超群', '男', '软件学院', DATE('2017-09-01'), '软件工程', '崔超群:软件学院的一名学生。',
        'E10ADC3949BA59ABBE56E057F20F883E');

insert into STUDENT
values (17301097, '李程遥', '男', '软件学院', DATE('2017-09-01'), '软件工程', '李程遥:软件学院的一名学生。',
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

insert into BLINK
values (1, 17301091, '基于深度学习的列车监控系统', '使用深度学习算法进行列车的轴承故障检测和驾驶员监控',
        '软件学院', '深度学习', 0, DATE('2019-05-21 21:12:22'));

insert into BLINK
values (2, 17301097, '医疗推荐系统', '对患病人群进行实时医疗方案推荐',
        '经管学院', '医学', 0, DATE('2020-11-20 05:30:50'));

insert into BLINK
values (3, 17301091, '基于Spring Boot的大创管理系统', '使用Spring Boot实现北交大创管理系统的重构',
        '软件学院', 'JavaEE', 0, DATE('2019-08-12 08:50:15'));

insert into BLINK
values (4, 17301091, '使用逻辑回归进行web异常请求检测', '运用nlp与逻辑回归进行二分类',
        '软件学院', '机器学习', 0, DATE('2020-06-02 07:31:22'));

insert into BLINK
values (5, 17301113, '基于Spring Boot与Vue的合同管理系统', '运用Spring Boot与Vue实现合同管理的自动化',
        '软件学院', '前端开发', 0, DATE('2018-07-12 16:37:33'));

insert into BLINK
values (6, 17301097, '列车故障检测', '使用多学科技术进行列车故障检测',
        '交通运输学院', '交通运输', 0, DATE('2018-11-12 14:15:29'));

insert into PROJECT
values (1,10000001,10000001,null,'秒杀系统的设计与实现','使用最新技术进行开发和设计','软件学院','系统开发',0,DATE('2018-12-22 15:15:19'));

insert into PROJECT
values (2,10000001,10000001,null,'基于Unity的宝石迷阵游戏开发','使用Unity开发宝石迷阵游戏','软件学院','游戏开发',0,DATE('2020-05-12 19:25:31'));

insert into PROJECT
values (3,10000001,10000001,null,'基于虚幻5引擎的只狼DLC开发','使用虚幻5引擎开发只狼的新DLC','软件学院','游戏开发',0,DATE('2019-06-18 15:35:31'));