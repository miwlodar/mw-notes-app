DROP DATABASE IF EXISTS `mw_notes_app_users`;

CREATE DATABASE IF NOT EXISTS `mw_notes_app_users`;
USE `mw_notes_app_users`;

-- Table structure for table `users`

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `users`
-- NOTE: The passwords are encrypted using BCrypt
-- A generation tool is avail at: http://www.luv2code.com/generate-bcrypt-password
-- Default passwords here are: fun123

INSERT INTO `users` 
VALUES 
('michal','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1),
('andrzej','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1);

-- Table structure for table `authorities`

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `authorities`

INSERT INTO `authorities` 
VALUES 
('michal','ROLE_ADMIN'),
('andrzej','ROLE_USER');