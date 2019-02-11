#create database [databaseName] DEFAULT CHARACTER SET utf8 collate utf8_general_ci;
#오토커밋 꺼라
#테이블,컬럼은 전부 대문자. 영단어 사이는 언더바를 사용한다.

create table KMS.KMS_Menu(
	menu_id INT(11) unsigned auto_increment primary key,
	menu_name VARCHAR(32) NOT NULL,
    menu_type VARCHAR(16) NOT NULL
) default character set utf8 collate utf8_general_ci ;

insert into KMS.KMS_Menu(menu_name,menu_type)values('ECM','SOL');
insert into KMS.KMS_Menu(menu_name,menu_type)values('EDM','SOL');
insert into KMS.KMS_Menu(menu_name,menu_type)values('ETC','SOL');

insert into KMS.KMS_Menu(menu_name,menu_type)values('Java','QNA');
insert into KMS.KMS_Menu(menu_name,menu_type)values('C++','QNA');
insert into KMS.KMS_Menu(menu_name,menu_type)values('Python','QNA');
insert into KMS.KMS_Menu(menu_name,menu_type)values('CSharp','QNA');
insert into KMS.KMS_Menu(menu_name,menu_type)values('Other','QNA');

insert into KMS.KMS_Menu(menu_name,menu_type)values('제 1금융','SITE');
insert into KMS.KMS_Menu(menu_name,menu_type)values('제 2금융','SITE');
insert into KMS.KMS_Menu(menu_name,menu_type)values('공공기관','SITE');
insert into KMS.KMS_Menu(menu_name,menu_type)values('사기업','SITE');
insert into KMS.KMS_Menu(menu_name,menu_type)values('ETC','SITE');

CREATE unique index UK_MENU_SAME ON KMS.KMS_Menu (menu_name,menu_type);

create table KMS.KMS_Group(
	group_id INT(11) unsigned auto_increment primary key,
    group_name VARCHAR(32) NOT NULL,
    group_parent INT(11) unsigned,
	FOREIGN KEY (group_parent)
    REFERENCES KMS.KMS_Group (group_id) ON UPDATE CASCADE
) default character set utf8 collate utf8_general_ci ;

insert into KMS.KMS_Group(group_id,group_name)values(0,'ROOT');
insert into KMS.KMS_Group(group_id,group_name,group_parent)values(1,'AdminGroup',0);
insert into KMS.KMS_Group(group_id,group_name,group_parent)values(2,'UserGroup',0);

CREATE INDEX FK_GROUP_PARENT ON KMS.KMS_Group (group_parent);
CREATE unique index UK_GROUP_SAME_LEVEL ON KMS.KMS_Group (group_parent,group_name);

create table KMS.KMS_User (
	user_id VARCHAR(32) primary key,
    user_name VARCHAR(32) NOT NULL,
    user_type VARCHAR(32) NOT NULL,
    user_group INT(11) unsigned NOT NULL,
	user_pass varchar(60) NOT NULL,
    user_extId varchar(32),
    FOREIGN KEY (user_group)
    REFERENCES KMS.KMS_Group (group_id) ON UPDATE CASCADE
) default character set utf8 collate utf8_general_ci ;

#ADMIN , ADMIN
insert into KMS.KMS_User(user_id,user_name,user_type,user_group,user_pass) values('ADMIN','ADMIN','ADMIN',1,'$2a$10$RWMKRWqLCWbjhIjiGzwvqusafXr8Y76JpB5SdaJCHoZeyAhb1aohu');
#USER , USER
insert into KMS.KMS_User(user_id,user_name,user_type,user_group,user_pass) values('USER','USER','USER',2,'$2a$10$RpcZZqJ3aBuKVswqVh/ixO8umoYLInnpPA6KnTXDoQlCH0I4Cq5om');
CREATE INDEX FK_USER_GROUP ON KMS.KMS_User(user_group);

create table KMS.KMS_ACL (
AclId varchar(36) PRIMARY KEY,
AclName varchar(36) not null unique,
HasPermission VARCHAR(255) not null
);

create table KMS.KMS_ACE(
AclId varchar(36) not null,
AceId varchar(32) not null,
Type varchar(5) not null,
CONSTRAINT FK_ACLID foreign key(AclId) REFERENCES KMS_ACL(AclId)
);

CREATE INDEX IDX_ACLID ON KMS_ACE(AclId);
CREATE INDEX IDX_ACEID ON KMS.KMS_ACE(AceId);
CREATE unique INDEX IDX_NOTINSAMEACL ON KMS.KMS_ACE(AclId,AceId);

insert into KMS.KMS_ACL(AclId,AclName,HasPermission) values('1','ADMINACL','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18');
insert into KMS.KMS_ACL(AclId,AclName) values('2','USERACL');

insert into KMS.KMS_ACE(AclId,AceId) values('1','ADMIN');
insert into KMS.KMS_ACE(AclId,AceId) values('2','USER');





















