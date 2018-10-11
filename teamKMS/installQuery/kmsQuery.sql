/*오토커밋 꺼라 */

create table KMS.KMSMenu (
	menu_id INT(11) unsigned auto_increment primary key,
	menu_name VARCHAR(32) NOT NULL,
    menu_type VARCHAR(16) NOT NULL
);
              
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

create table KMS.KMSGroup (
	group_id INT(11) unsigned auto_increment primary key,
    group_name VARCHAR(32) NOT NULL,
    group_parent INT(11) unsigned,
	FOREIGN KEY (group_parent)
    REFERENCES KMS.KMSGroup(group_id) ON UPDATE CASCADE
);

insert into KMS.KMSGroup(group_id,group_name) values(1,'ROOT');
CREATE INDEX FK_GROUP_PARENT ON KMS.KMSGroup(group_parent);

create table KMS.KMSUser (
	user_id INT(11) unsigned auto_increment primary key,
    user_name VARCHAR(32) NOT NULL,
    user_type VARCHAR(32) NOT NULL,
    user_group INT(11) unsigned,
    FOREIGN KEY (user_group)
    REFERENCES KMS.KMSGroup(group_id) ON UPDATE CASCADE
);

insert into KMS.KMSUser(user_id,user_name,user_type,user_group) values(1,'ADMIN','ADMIN',1);
CREATE INDEX FK_USER_GROUP ON KMS.KMSUser(user_group);

create table KMS.KMSCommonPost (
	post_id INT(11) unsigned auto_increment primary key,
    post_name VARCHAR(32) NOT NULL,
    post_user VARCHAR(32) NOT NULL,
    post_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    post_category INT(11) unsigned,
    FOREIGN KEY (post_category)
    REFERENCES KMS.KMSMenu(menu_id) ON UPDATE CASCADE
);

CREATE INDEX FK_POST_CETE ON KMS.KMSCommonPost(post_category);
