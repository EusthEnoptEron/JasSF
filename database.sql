-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.27 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4157
-- Date/time:                    2014-11-20 15:40:17
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for jass
CREATE DATABASE IF NOT EXISTS `jass` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jass`;


-- Dumping structure for table jass.gamesession
CREATE TABLE IF NOT EXISTS `gamesession` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `state` enum('preparing','playing','closed','aborted') NOT NULL DEFAULT 'preparing',
  `creatorId` int(10) NOT NULL,
  `team1Points` int(10) NOT NULL DEFAULT '0',
  `team2Points` int(10) NOT NULL DEFAULT '0',
  `winner` tinyint(4) DEFAULT NULL COMMENT 'Needed because the "Bedanken"-System',
  PRIMARY KEY (`id`),
  KEY `creatorId` (`creatorId`),
  CONSTRAINT `FK_gamesession_user` FOREIGN KEY (`creatorId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Represents a "table" where players... play.';

-- Dumping data for table jass.gamesession: ~0 rows (approximately)
DELETE FROM `gamesession`;
/*!40000 ALTER TABLE `gamesession` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamesession` ENABLE KEYS */;


-- Dumping structure for table jass.gamesession_user
CREATE TABLE IF NOT EXISTS `gamesession_user` (
  `sessionId` int(10) NOT NULL,
  `userId` int(10) NOT NULL,
  `team` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sessionId`,`userId`),
  KEY `FK_gamesession_user_user` (`userId`),
  CONSTRAINT `FK_gamesession_user_gamesession` FOREIGN KEY (`sessionId`) REFERENCES `gamesession` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_gamesession_user_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table jass.gamesession_user: ~0 rows (approximately)
DELETE FROM `gamesession_user`;
/*!40000 ALTER TABLE `gamesession_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamesession_user` ENABLE KEYS */;


-- Dumping structure for table jass.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `dateofbirth` date NOT NULL DEFAULT '2010-10-20',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='This table is used for storing users';

-- Dumping data for table jass.user: ~3 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `dateofbirth`) VALUES
  (1, 'bie1', 'xxx2', '1954-08-12'),
  (2, 'bie2', 'test', '1980-01-02'),
  (14, 'Simon', 'lol', '1990-11-09');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
