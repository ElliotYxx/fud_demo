create table download
(
    id          bigint auto_increment comment '下载记录'
        primary key,
    create_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '下载时间',
    user_id     bigint                              not null comment '下载文件的用户id',
    file_id     bigint                              not null comment '下载的文件id'
)
    comment '用户下载表';

create table file
(
    id             bigint auto_increment comment '上传文件id'
        primary key,
    name           varchar(255)                           not null comment '上传文件名称',
    local_url      varchar(255)                           not null comment '上传文件路径',
    size           mediumtext                             not null comment '上传文件大小',
    description    varchar(255) default ''                null comment '上传文件描述',
    download_times bigint       default 0                 not null comment '上传文件下载次数',
    user_id        bigint(11)                             not null comment '文件上传者id',
    create_time    timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '文件上传日期',
    md5            varchar(255) default ''                not null,
    suffix         varchar(50)  default ' '               null
)
    charset = utf8;

create table role
(
    id        bigint auto_increment comment '用户角色id'
        primary key,
    role_name varchar(30) not null comment '用户角色名称'
)
    comment '角色表';

create table user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    username    varchar(20)       not null comment '用户名',
    password    varchar(100)      not null comment '用户密码',
    create_time date              not null comment '用户创建时间',
    state       tinyint default 0 not null comment '0：正常　１:锁死',
    role_id     bigint            null
);

