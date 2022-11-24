
CREATE DATABASE `wechat_public_personal` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `city` (
  `id` bigint(127) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(127) DEFAULT NULL,
  `city_name` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_code` (`city_code`,`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `log` (
  `id` bigint(127) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(31) DEFAULT NULL,
  `message` varchar(127) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `sentence` (
  `id` bigint(127) NOT NULL AUTO_INCREMENT,
  `content` varchar(127) DEFAULT NULL,
  `note` varchar(127) DEFAULT NULL,
  `translation` varchar(127) DEFAULT NULL,
  `date` varchar(127) DEFAULT NULL,
  `fenxiang_img` longtext,
  `media_id` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `weather` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cityCode` varchar(127) DEFAULT NULL,
  `date` varchar(127) DEFAULT NULL,
  `dayWeather` varchar(127) DEFAULT NULL,
  `nightWeather` varchar(127) DEFAULT NULL,
  `dayTemp` varchar(127) DEFAULT NULL,
  `nightTemp` varchar(127) DEFAULT NULL,
  `windLevel` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cityCode` (`cityCode`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
