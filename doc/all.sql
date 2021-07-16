drop table if exists `test`;
CREATE table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `password` varchar(50) comment '密码',
    primary key (`id`)
) engine=InnoDB default charset=utf8mb4 comment='测试';

insert into `test` (id,name,password) values(1,'测试','password');

drop table if exists `dome`;
CREATE table `dome` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment '名称',
                        primary key (`id`)
) engine=InnoDB default charset=utf8mb4 comment='测试';