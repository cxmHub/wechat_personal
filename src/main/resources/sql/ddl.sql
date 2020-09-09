
CREATE DATABASE `wechat_public_personal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;


create table city
(
  id        bigint(127) auto_increment
    primary key,
  city_code varchar(127) null,
  city_name varchar(127) null,
  constraint city_code
  unique (city_code, city_name)
);

create table log
(
  id        bigint(127) auto_increment
    primary key,
  user_name varchar(31)  null,
  message   varchar(127) null,
  time      datetime     null
);

create table sentence
(
  id          bigint(127) auto_increment
    primary key,
  content     varchar(127) null,
  note        varchar(127) null,
  translation varchar(127) null,
  date        varchar(127) null
);

create table weather
(
  id           bigint auto_increment
    primary key,
  cityCode     varchar(127) null,
  date         varchar(127) null,
  dayWeather   varchar(127) null,
  nightWeather varchar(127) null,
  dayTemp      varchar(127) null,
  nightTemp    varchar(127) null,
  windLevel    varchar(127) null,
  constraint cityCode
  unique (cityCode, date)
);

