DROP DATABASE IF EXISTS `mw_notes_combined_db`;

CREATE DATABASE IF NOT EXISTS `mw_notes_combined_db`;
USE `mw_notes_combined_db`;



-- Table structure for table `users`

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Dumping data for table `users`
-- The passwords are encrypted using BCrypt
-- Default passwords are 'pass123'

INSERT INTO `users` (username,password,first_name,last_name,email)
VALUES 
('mike','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Mike','Smith','mike@gmail.com'),
('marty','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Marty','Andrews','marty@gmail.com'),
('jenny','$2a$10$XihOJgGY/Dir3fXOo8Cfour967tds1UUC/THA3wBWy49XoxePu/Su','Jane','Doe','jenny@gmail.com');


-- Table structure for table `roles`

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Dumping data for table `roles`

INSERT INTO `roles` (name)
VALUES 
('ROLE_USER'),('ROLE_ADMIN');



-- Table structure for table `users_roles`

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `roles` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

-- Dumping data for table `users_roles`

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 2),
(2, 1),
(3, 1);



-- Table structure for table `notes`

DROP TABLE IF EXISTS `notes`;

CREATE TABLE `notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` varchar(50000) NOT NULL,
  `owner` varchar(50) NOT NULL,
  `created` timestamp DEFAULT CURRENT_TIMESTAMP ,
  `last_edited` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Dumping data for table `notes`

INSERT INTO `notes` VALUES
	(1, 'Example note', 'Exemplary content of an exemplary note','mike@gmail.com', DEFAULT, DEFAULT),
	(2, 'Example memo', 'Exemplary content of an exemplary memo - another one, but a bit longer','mike@gmail.com', DEFAULT, DEFAULT),
	(3, 'Lorem ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','marty@gmail.com', DEFAULT, DEFAULT),
	(4, 'Lorem ipsum dolor', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','jenny@gmail.com', DEFAULT, DEFAULT);