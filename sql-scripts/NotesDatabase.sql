DROP DATABASE IF EXISTS `mw_notes_app_notes`;

CREATE DATABASE  IF NOT EXISTS `mw_notes_app_notes`;
USE `mw_notes_app_notes`;

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

-- Data for table `notes`

INSERT INTO `notes` VALUES
	(1, 'Example title', 'Exemplary content of an exemplary note','michal', DEFAULT, DEFAULT),
	(2, 'Example title - other', 'Exemplary content of an exemplary note - another one, but a bit longer','andrzej', DEFAULT, DEFAULT),
	(3, 'Lorem lorem looorem', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','andrzej', DEFAULT, DEFAULT);