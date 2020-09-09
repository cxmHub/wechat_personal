
CREATE DATABASE `wechat_public_personal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;


create table city
(
  id        bigint(255) auto_increment
    primary key,
  city_code varchar(255) null,
  city_name varchar(255) null,
  constraint city_code
  unique (city_code, city_name)
);

create table log
(
  id        bigint(255) auto_increment
    primary key,
  user_name varchar(32)  null,
  message   varchar(255) null,
  time      datetime     null
);

create table sentence
(
  id          bigint(255) auto_increment
    primary key,
  content     varchar(255) null,
  note        varchar(255) null,
  translation varchar(255) null,
  date        varchar(255) null
);

create table weather
(
  id           bigint auto_increment
    primary key,
  cityCode     varchar(255) null,
  date         varchar(255) null,
  dayWeather   varchar(255) null,
  nightWeather varchar(255) null,
  dayTemp      varchar(255) null,
  nightTemp    varchar(255) null,
  windLevel    varchar(255) null,
  constraint cityCode
  unique (cityCode, date)
);

