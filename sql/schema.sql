CREATE USER 'changzipup'@'localhost' IDENTIFIED BY '1234';
create database changzipup;
GRANT ALL PRIVILEGES ON changzipup.* TO 'changzipup'@'localhost';

create table building_type
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table construction_type
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table membership_type
(
    id    bigint auto_increment
        primary key,
    name  enum ('BASIC', 'NO', 'PREMIUM') null,
    price int                             not null
);

create table refresh_token
(
    id               bigint auto_increment
        primary key,
    expire_date_time datetime(6)  null,
    token            varchar(255) null
);

create table account
(
    account_type           varchar(31)                       not null,
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6)                       null,
    last_updated_date_time datetime(6)                       null,
    account_role           enum ('ADMIN', 'COMPANY', 'USER') null,
    email                  varchar(255)                      null,
    is_verified            bit                               not null,
    password               varchar(255)                      null,
    phone_number           varchar(255)                      null,
    refresh_token_id       bigint                            null,
    constraint UK19n9h1o0l5qbp9p10n9hnark6
        unique (refresh_token_id),
    constraint FKa3lo7vtnv43s1hmf9wwqcpd83
        foreign key (refresh_token_id) references refresh_token (id)
);

create table company
(
    address          varchar(255) null,
    company_desc     varchar(255) null,
    company_logo_url varchar(255) null,
    company_name     varchar(255) null,
    company_number   varchar(255) null,
    owner            varchar(255) null,
    publish_date     date         null,
    rating           double       not null,
    id               bigint       not null
        primary key,
    constraint FKoi516httgi8ss7c8pdcd5j4o1
        foreign key (id) references account (id)
);

create table company_construction_type
(
    id         bigint auto_increment
        primary key,
    company_id bigint null,
    type_id    bigint null,
    constraint FKiwe3jxa57vupwcornmtny1uh7
        foreign key (company_id) references company (id),
    constraint FKq8iaub55kfev70swcfrhxrq33
        foreign key (type_id) references construction_type (id)
);

create table member
(
    member_type enum ('KAKAO', 'LOCAL', 'NAVER') null,
    name        varchar(255)                     null,
    nick_name   varchar(255)                     null,
    id          bigint                           not null
        primary key,
    constraint FK4knbwm1yq5ysijeuwuw5lf2ah
        foreign key (id) references account (id)
);

create table estimate_request
(
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6)                                             null,
    last_updated_date_time datetime(6)                                             null,
    address                varchar(255)                                            not null,
    budget                 varchar(255)                                            not null,
    detailed_address       varchar(255)                                            null,
    floor                  int                                                     null,
    identification         varchar(255)                                            null,
    measure_date           date                                                    null,
    reg_date               datetime(6)                                             not null,
    schedule               varchar(255)                                            not null,
    status                 enum ('CANCELLATION', 'COMPLETE', 'ONGOING', 'WAITING') null,
    building_type_id       bigint                                                  null,
    member_id              bigint                                                  not null,
    constraint UKa5k2emnkjyp8ur0v6lxxffg86
        unique (identification),
    constraint FK6kqkn6pyt65tg7v8esxt1179
        foreign key (member_id) references member (id),
    constraint FKcoft5fk9k6rnch9bm8hk1ujr9
        foreign key (building_type_id) references building_type (id)
);

create table estimate
(
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6)                                                   null,
    last_updated_date_time datetime(6)                                                   null,
    estimate_status        enum ('ACCEPTED', 'COMPLETE', 'RECEIVED', 'REJECTED', 'SENT') not null,
    company_id             bigint                                                        null,
    estimate_request_id    bigint                                                        not null,
    constraint FKbjqacbuqxa3xw306m4h5lhm4e
        foreign key (estimate_request_id) references estimate_request (id),
    constraint FKhmn7ysb3kkdan1nxq8c5ghye7
        foreign key (company_id) references company (id)
);

create table estimate_construction_type
(
    id                  bigint auto_increment
        primary key,
    type_id             bigint not null,
    estimate_request_id bigint not null,
    constraint FK2u0kjw7qmfcya98fsxgbsw0mw
        foreign key (estimate_request_id) references estimate_request (id),
    constraint FKaeuloe0g8q5jggysah6ntjxl2
        foreign key (type_id) references construction_type (id)
);

create table estimate_price
(
    id          bigint auto_increment
        primary key,
    price       int    not null,
    ec_type_id  bigint null,
    estimate_id bigint null,
    constraint FK9o2pvdouexhi5gu6pq662yr44
        foreign key (estimate_id) references estimate (id),
    constraint FKfupwcspe0maqrmpuslofr9y
        foreign key (ec_type_id) references estimate_construction_type (id)
);

create table notice
(
    id          bigint auto_increment
        primary key,
    content     tinytext     null,
    create_date datetime(6)  null,
    email       varchar(255) null,
    title       varchar(255) null,
    update_date datetime(6)  null,
    member_id   bigint       null,
    constraint FKnriaekshh15qoqnlhvqkj931e
        foreign key (member_id) references member (id)
);

create table payment
(
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6)                           null,
    last_updated_date_time datetime(6)                           null,
    complete_date          datetime(6)                           null,
    imp_uid                varchar(255)                          null,
    merchant_uid           varchar(255)                          null,
    request_date           datetime(6)                           null,
    status                 enum ('CANCEL', 'COMPLETE', 'CREATE') null,
    company_id             bigint                                null,
    membership_type_id     bigint                                null,
    constraint FKahr1jwmhpvh514p2j0vdg60cw
        foreign key (company_id) references company (id),
    constraint FKfpfiy62f3nnyw3iu6w7ydjnrp
        foreign key (membership_type_id) references membership_type (id)
);

create table membership
(
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6) null,
    last_updated_date_time datetime(6) null,
    end_date_time          datetime(6) null,
    start_date_time        datetime(6) null,
    company_id             bigint      null,
    membership_type_id     bigint      null,
    payment_id             bigint      null,
    constraint UKh9e3q5xfey8nrrix6jmlu2o1h
        unique (payment_id),
    constraint FK2r28jxw9ym6iohjreq5t8priv
        foreign key (company_id) references company (id),
    constraint FK9lvd0u9pc4uv35ysq5rkr2s6f
        foreign key (membership_type_id) references membership_type (id),
    constraint FKmm2p5qo7b0rnexh8yk179y9qa
        foreign key (payment_id) references payment (id)
);

create table portfolio
(
    id                     bigint auto_increment
        primary key,
    created_date_time      datetime(6)  null,
    last_updated_date_time datetime(6)  null,
    content                text         null,
    created_at             datetime(6)  null,
    end_date               date         null,
    floor                  int          not null,
    project_budget         int          not null,
    project_location       varchar(255) null,
    start_date             date         null,
    title                  varchar(255) null,
    updated_at             datetime(6)  null,
    building_type_id       bigint       null,
    company_id             bigint       null,
    constraint FK85931pklvjkgtuf2e1rfsx1o6
        foreign key (company_id) references company (id),
    constraint FKhh0d7e1b73peyuv8d4uksg5ck
        foreign key (building_type_id) references building_type (id)
);

create table portfolio_construction_type
(
    id                   bigint auto_increment
        primary key,
    construction_type_id bigint null,
    portfolio_id         bigint null,
    constraint FK38p2af9tvtxy7xy2ka0xnl4hi
        foreign key (construction_type_id) references construction_type (id),
    constraint FKe5y0ned7006octv2gnhs9qnw1
        foreign key (portfolio_id) references portfolio (id)
);

create table portfolio_image
(
    id           bigint auto_increment
        primary key,
    image_url    varchar(255) null,
    portfolio_id bigint       null,
    constraint FKt44582f3i30sx25cjqth61a6p
        foreign key (portfolio_id) references portfolio (id)
);

create table review
(
    id               bigint auto_increment
        primary key,
    content          text         null,
    floor            int          not null,
    rating           int          not null,
    reg_date         datetime(6)  null,
    title            varchar(255) null,
    total_price      bigint       null,
    work_end_date    date         null,
    work_start_date  date         null,
    building_type_id bigint       null,
    company_id       bigint       null,
    member_id        bigint       null,
    constraint FK1r7rw4wp2mtlsn758pdi24qdx
        foreign key (company_id) references company (id),
    constraint FKar51b66tbs77nwndn3g78eh8w
        foreign key (building_type_id) references building_type (id),
    constraint FKk0ccx5i4ci2wd70vegug074w1
        foreign key (member_id) references member (id)
);

create table review_construction_type
(
    id                   bigint auto_increment
        primary key,
    construction_type_id bigint null,
    review_id            bigint null,
    constraint FKdy328bhvmprttqv8y2skivpto
        foreign key (review_id) references review (id),
    constraint FKr8rl9650txjoru01d2b6dwmea
        foreign key (construction_type_id) references construction_type (id)
);

create table review_images
(
    id        bigint auto_increment
        primary key,
    image_url varchar(255) null,
    review_id bigint       null,
    constraint FKn6ocagcwsaswdoh2ntvrkk5en
        foreign key (review_id) references review (id)
);

create table verification
(
    id                bigint auto_increment
        primary key,
    verification_code varchar(255) null,
    account_id        bigint       null,
    constraint FK2i3pcd59lu5f31hko92ndhgos
        foreign key (account_id) references account (id)
);

alter table portfolio
    modify column content TEXT;

alter table review
    modify column content TEXT;