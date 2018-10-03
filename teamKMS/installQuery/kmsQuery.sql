create table KMS.KMSMenu (
	menu_id INT(11) unsigned auto_increment primary key  NOT NULL,
	menu_name VARCHAR(32) NOT NULL,
    menu_type VARCHAR(16) NOT NULL
);
              
insert into KMS.KMSMenu(menu_name,menu_type) values('ECM','SOL');
insert into KMS.KMSMenu(menu_name,menu_type) values('EDM','SOL');
insert into KMS.KMSMenu(menu_name,menu_type) values('ETC','SOL');

insert into KMS.KMSMenu(menu_name,menu_type) values('Java','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('C++','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('Python','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('C#','QNA');
insert into KMS.KMSMenu(menu_name,menu_type) values('Other','QNA');

insert into KMS.KMSMenu(menu_name,menu_type) values('제 1금융','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('제 2금융','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('공공기관','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('사기업','SITE');
insert into KMS.KMSMenu(menu_name,menu_type) values('ETC','SITE');

commit;

