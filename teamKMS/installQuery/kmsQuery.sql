#create database [databaseName] DEFAULT CHARACTER SET utf8 collate utf8_general_ci;
#오토커밋 꺼라
#테이블,컬럼은 전부 대문자. 영단어 사이는 언더바를 사용한다.

create table KMS.KMS_Menu
(
  menu_id   INT(11) unsigned auto_increment primary key,
  menu_name VARCHAR(32) NOT NULL,
  menu_type VARCHAR(16) NOT NULL
) default character set utf8
  collate utf8_general_ci;

insert into KMS.KMS_Menu(menu_name, menu_type)
values ('ECM', 'SOL');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('EDM', 'SOL');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('ETC', 'SOL');

insert into KMS.KMS_Menu(menu_name, menu_type)
values ('Java', 'QNA');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('C++', 'QNA');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('Python', 'QNA');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('CSharp', 'QNA');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('Other', 'QNA');

insert into KMS.KMS_Menu(menu_name, menu_type)
values ('제 1금융', 'SITE');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('제 2금융', 'SITE');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('공공기관', 'SITE');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('사기업', 'SITE');
insert into KMS.KMS_Menu(menu_name, menu_type)
values ('ETC', 'SITE');

CREATE unique index UK_MENU_SAME ON KMS.KMS_Menu (menu_name, menu_type);

create table KMS.KMS_Group
(
  group_id     INT(11) unsigned auto_increment primary key,
  group_name   VARCHAR(32) NOT NULL,
  group_parent INT(11) unsigned,
  FOREIGN KEY (group_parent)
    REFERENCES KMS.KMS_Group (group_id) ON UPDATE CASCADE
) default character set utf8
  collate utf8_general_ci;

insert into KMS.KMS_Group(group_id, group_name)
values (0, 'ROOT');
insert into KMS.KMS_Group(group_id, group_name, group_parent)
values (1, 'AdminGroup', 0);
insert into KMS.KMS_Group(group_id, group_name, group_parent)
values (2, 'UserGroup', 0);

CREATE INDEX FK_GROUP_PARENT ON KMS.KMS_Group (group_parent);
CREATE unique index UK_GROUP_SAME_LEVEL ON KMS.KMS_Group (group_parent, group_name);

create table KMS.KMS_User
(
  user_id    VARCHAR(32) primary key,
  user_name  VARCHAR(32)      NOT NULL,
  user_type  VARCHAR(32)      NOT NULL,
  user_group INT(11) unsigned NOT NULL,
  user_pass  varchar(60)      NOT NULL,
  user_extId varchar(32),
  FOREIGN KEY (user_group)
    REFERENCES KMS.KMS_Group (group_id) ON UPDATE CASCADE
) default character set utf8
  collate utf8_general_ci;

#ADMIN , ADMIN
insert into KMS.KMS_User(user_id, user_name, user_type, user_group, user_pass)
values ('ADMIN', 'ADMIN', 'ADMIN', 1, '$2a$10$RWMKRWqLCWbjhIjiGzwvqusafXr8Y76JpB5SdaJCHoZeyAhb1aohu');
#USER , USER
insert into KMS.KMS_User(user_id, user_name, user_type, user_group, user_pass)
values ('USER', 'USER', 'USER', 2, '$2a$10$RpcZZqJ3aBuKVswqVh/ixO8umoYLInnpPA6KnTXDoQlCH0I4Cq5om');
CREATE INDEX FK_USER_GROUP ON KMS.KMS_User (user_group);

create table KMS.KMS_ACL
(
  AclId         varchar(36) PRIMARY KEY,
  AclName       varchar(36)  not null unique,
  HasPermission VARCHAR(255) not null
)  default character set utf8
   collate utf8_general_ci;

create table KMS.KMS_ACE
(
  AclId varchar(36) not null,
  AceId varchar(32) not null,
  Type  varchar(5)  not null,
  CONSTRAINT FK_ACLID foreign key (AclId) REFERENCES KMS_ACL (AclId),
  CONSTRAINT PK_ACEID primary key (AclId, AceId)
)  default character set utf8
   collate utf8_general_ci;

CREATE INDEX IDX_ACLID ON KMS_ACE (AclId);
CREATE INDEX IDX_ACEID ON KMS.KMS_ACE (AceId);
CREATE unique INDEX IDX_NOTINSAMEACL ON KMS.KMS_ACE (AclId, AceId);

insert into KMS.KMS_ACL(AclId, AclName, HasPermission)
values ('1', 'ADMINACL', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18');
insert into KMS.KMS_ACL(AclId, AclName)
values ('2', 'USERACL');

insert into KMS.KMS_ACE(AclId, AceId)
values ('1', 'ADMIN');
insert into KMS.KMS_ACE(AclId, AceId)
values ('2', 'USER');

create table KMS_BOARD
(
    board_id int(255) unsigned auto_increment comment '게시글 key'
        primary key,
    subject  varchar(255) not null comment '게시글 제목',
    contents text         not null comment '게시글 내용',
    user_id  varchar(32)  not null comment '작성자',
    reg_date date         not null comment '등록일자',
    upd_date date         not null comment '수정일자',
    hits     int(255)     not null comment '조회수'
);

create table KMS_COMMENT
(
    board_id     int(255) unsigned not null comment '게시글 key',
    cmt_id       int(255) unsigned auto_increment comment '댓글 key',
    cmt_contents text              not null comment '댓글 내용',
    cmt_code     text              null,
    cmt_user_id  varchar(20)       not null comment '댓글 작성자',
    cmt_date     date              not null comment '댓글 등록일자',
    cmt_like     int default 0     null,
    constraint index_cmt_id
        unique (cmt_id),
    constraint FK_TBL_COMMENT_TBL_BOARD
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade
) comment '댓글 테이블';

create table KMS_DOC
(
    doc_id      int(255) unsigned auto_increment comment '문서 key',
    board_id    int(255) unsigned null comment '게시글 key',
    cmt_id      int(32) unsigned  null comment '댓글 key',
    doc_path    varchar(255)      not null comment '문서 경로',
    doc_name    varchar(255)      null comment '문서 이',
    doc_ext     varchar(45)       null comment '문서 확장자',
    doc_user_id varchar(32)       not null comment '문서 등록자',
    doc_size    int(32) unsigned  not null comment '파일 사이즈',
    constraint unique_doc_id
        unique (doc_id),
    constraint FK_KMS_COMMENT_ID
        foreign key (cmt_id) references KMS_COMMENT (cmt_id)
            on update cascade on delete cascade,
    constraint FK_TBL_DOC_TBL_BOARD
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade
) comment '첨부문서 테이블';

-- auto-generated definition
create table KMS_LIKE_CHECK
(
    like_check_id int(255)                   not null
        primary key,
    cmt_id        int(255) unsigned          not null,
    user_id       varchar(32)                not null,
    like_yn       int(255) unsigned zerofill not null,
    unlike_yn     int(255) unsigned zerofill not null,
    constraint fk_like_cmt_id
        foreign key (cmt_id) references KMS_COMMENT (cmt_id)
);

create index idx_like_cmt_id
    on KMS_LIKE_CHECK (cmt_id);

create index idx_like_user_id
    on KMS_LIKE_CHECK (user_id);

-- auto-generated definition
create table KMS_QNA_CODE
(
    menu_id  int(11) unsigned  not null,
    board_id int(255) unsigned not null,
    constraint FK_KMS_QNA_CODE_BOARD_ID_KMS_BOARD
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint FK_KMS_QNA_CODE_MENU_ID_KMS_MENU
        foreign key (menu_id) references KMS_Menu (menu_id)
            on update cascade
) comment 'QnA 게시판 언어별 게시물 매핑 테이블';

create table KMS_Solution_Patch
(
    board_id int(255) unsigned not null
        primary key,
    menu_id  int(11) unsigned  not null,
    importance varchar(11) not null,
    version varchar(11) not null,
    constraint FK_KMSSolution_KMS_BOARD
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint KMS_Solution_KMS_Menu_menu_id_fk
        foreign key (menu_id) references KMS_Menu (menu_id)
) comment '솔루션 패치 노트 관련 테이블';

create table KMS_Solution_SITE
(
    board_id int(255) unsigned not null
        primary key,
    menu_id  int(11) unsigned  not null,
    site_id int(11) unsigned not null,
    version varchar(11) not null,
    constraint FK_KMSSolution_SITE_boardId
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint FK_KMSSolution_SITE_menuId
        foreign key (menu_id) references KMS_Menu (menu_id),
    constraint FK_KMSSolution_SITE_siteId
        foreign key (site_id) references KMS_SITE (SITE_ID)
) comment '솔루션 사이트 버전 관리 테이블';

-- auto-generated definition
create table KMS_Solution_Patch
(
    board_id int(255) unsigned not null
        primary key,
    menu_id  int(11) unsigned  not null,
    importance varchar(11) not null,
    version varchar(11) not null,
    constraint FK_KMSSolution_PATCH_boardId
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint FK_KMSSolution_PATCH_menuId
        foreign key (menu_id) references KMS_Menu (menu_id)
);

create table KMS_Solution_Patch
(
    board_id int(255) unsigned not null
        primary key,
    menu_id  int(11) unsigned  not null,
    importance varchar(11) not null,
    version varchar(11) not null,
    constraint FK_KMSSolution_PATCH_boardId
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint FK_KMSSolution_PATCH_menuId
        foreign key (menu_id) references KMS_Menu (menu_id)
);

create table KMS_Solution_BUG
(
    board_id int(255) unsigned not null
        primary key,
    menu_id  int(11) unsigned  not null,
    manager  varchar(32) not null,
    completed varchar(1) not null,
    constraint FK_KMSSolution_BUG_boardId
        foreign key (board_id) references KMS_BOARD (board_id)
            on update cascade on delete cascade,
    constraint FK_KMSSolution_BUG_menuId
        foreign key (menu_id) references KMS_Menu (menu_id),
    constraint FK_KMSSolution_BUG_userId
        foreign key (manager) references KMS_User (user_id)
);

create table KMS_SITE
(
  MENU_ID INT(11) unsigned,
  SITE_ID INT(11) unsigned auto_increment PRIMARY KEY,
  SITE_NAME VARCHAR(32),
  FOREIGN KEY (MENU_ID) REFERENCES KMS.KMS_Menu (menu_id) ON UPDATE CASCADE
)  default character set utf8
   collate utf8_general_ci;

CREATE INDEX IDX_SITE ON KMS_SITE (MENU_ID);

create table KMS_PROJECT
(
  SITE_ID INT(11) unsigned,
  PROJECT_ID INT(11) unsigned auto_increment primary key,
  PROJECT_NAME VARCHAR(64) not null,
  START_DATE   date             not null,
  END_DATE     date             not null,
  MANAGER      varchar(32)      not null,
  FOREIGN KEY (SITE_ID) REFERENCES KMS.KMS_SITE (SITE_ID) ON UPDATE CASCADE
)  default character set utf8
   collate utf8_general_ci;

CREATE INDEX IDX_PROJECT_SITEID ON KMS_PROJECT (SITE_ID);
CREATE INDEX IDX_PROJECT_NAME ON KMS_PROJECT (PROJECT_NAME);

create table KMS_PROJECT_BOARD
(
  PROJECT_ID INT(11) unsigned,
  BOARD_ID int(255) unsigned unique,
  FOREIGN KEY (BOARD_ID) REFERENCES KMS.KMS_BOARD (board_id) ON UPDATE CASCADE,
  FOREIGN KEY (PROJECT_ID) REFERENCES KMS.KMS_PROJECT (PROJECT_ID) ON UPDATE CASCADE
)  default character set utf8
   collate utf8_general_ci;

CREATE INDEX IDX_PROJECT_BOARD_PROJECTKEY ON KMS_PROJECT_BOARD (PROJECT_ID);
CREATE INDEX IDX_PROJECT_BOARD_BOARDKEY ON KMS_PROJECT_BOARD (BOARD_ID);























