#create database [databaseName] DEFAULT CHARACTER SET utf8 collate utf8_general_ci;
#오토커밋 꺼라

create table KMS.KMSMenu (
	menu_id INT(11) unsigned auto_increment primary key,
	menu_name VARCHAR(32) NOT NULL,
    menu_type VARCHAR(16) NOT NULL
) default character set utf8 collate utf8_general_ci ;
              
insert into KMS.KMSMenu(menu_name,menu_type) values('ECM','SOL');
insert into KMS.KMSMenu(menu_name,menu_type) values('EDM','SOL');
insert into KMS.KMSMenu(menu_name,menu_type) values('ETC','SOL');

insert into KMS.KMSMenu(menu_name,menu_type) values('Java','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('C++','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('Python','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('CSharp','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('Other','QNA');

insert into KMS.KMSMenu(menu_name,menu_type) values('제 1금융','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('제 2금융','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('공공기관','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('사기업','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('ETC','SITE');

CREATE unique index UK_MENU_SAME ON KMS.KMSMenu(menu_name,menu_type);

create table KMS.KMSGroup (
	group_id INT(11) unsigned auto_increment primary key,
    group_name VARCHAR(32) NOT NULL,
    group_parent INT(11) unsigned,
	FOREIGN KEY (group_parent)
    REFERENCES KMS.KMSGroup(group_id) ON UPDATE CASCADE
) default character set utf8 collate utf8_general_ci ;

insert into KMS.KMSGroup(group_id,group_name) values(0,'ROOT');
insert into KMS.KMSGroup(group_id,group_name,group_parent) values(1,'DefaultGroup',0);
CREATE INDEX FK_GROUP_PARENT ON KMS.KMSGroup(group_parent);
CREATE unique index UK_GROUP_SAMELAVEL ON KMS.KMSGroup(group_parent,group_name);

create table KMS.KMSUser (
	user_id VARCHAR(32) primary key,
    user_name VARCHAR(32) NOT NULL,
    user_type VARCHAR(32) NOT NULL,
    user_group INT(11) unsigned NOT NULL,
	user_pass varchar(60) NOT NULL,
    user_extId varchar(32),
    FOREIGN KEY (user_group)
    REFERENCES KMS.KMSGroup(group_id) ON UPDATE CASCADE
) default character set utf8 collate utf8_general_ci ;

#ADMIN , ADMIN
insert into KMS.KMSUser(user_id,user_name,user_type,user_group,user_pass) values('ADMIN','ADMIN','ADMIN',1,'$2a$10$RWMKRWqLCWbjhIjiGzwvqusafXr8Y76JpB5SdaJCHoZeyAhb1aohu');
#USER , USER
insert into KMS.KMSUser(user_id,user_name,user_type,user_group,user_pass) values('USER','USER','USER',1,'$2a$10$RpcZZqJ3aBuKVswqVh/ixO8umoYLInnpPA6KnTXDoQlCH0I4Cq5om');
CREATE INDEX FK_USER_GROUP ON KMS.KMSUser(user_group);

create table KMS.KMSCommonPost (
	post_id INT(11) unsigned auto_increment primary key,
    post_name VARCHAR(32) NOT NULL,
    post_user VARCHAR(32) NOT NULL,
    post_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    post_category INT(11) unsigned,
    FOREIGN KEY (post_category)
    REFERENCES KMS.KMSMenu(menu_id) ON UPDATE CASCADE
) default character set utf8 collate utf8_general_ci ;

CREATE INDEX FK_POST_CETE ON KMS.KMSCommonPost(post_category);
