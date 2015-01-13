-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.27 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4157
-- Date/time:                    2015-01-13 18:11:27
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for jass
CREATE DATABASE IF NOT EXISTS `jass` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jass`;


-- Dumping structure for table jass.games
DROP TABLE IF EXISTS `games`;
CREATE TABLE IF NOT EXISTS `games` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `creatorId` int(10) NOT NULL,
  `requiredScore` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `creatorId` (`creatorId`),
  CONSTRAINT `FK_gamesession_user` FOREIGN KEY (`creatorId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Represents a "table" where players... play.';

-- Dumping data for table jass.games: ~0 rows (approximately)
DELETE FROM `games`;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
/*!40000 ALTER TABLE `games` ENABLE KEYS */;


-- Dumping structure for table jass.teams
DROP TABLE IF EXISTS `teams`;
CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(10) NOT NULL,
  `score` int(10) NOT NULL,
  `gameId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_teams_games` (`gameId`),
  CONSTRAINT `FK_teams_games` FOREIGN KEY (`gameId`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table jass.teams: ~0 rows (approximately)
DELETE FROM `teams`;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;


-- Dumping structure for table jass.teams_users
DROP TABLE IF EXISTS `teams_users`;
CREATE TABLE IF NOT EXISTS `teams_users` (
  `userId` int(10) DEFAULT NULL,
  `teamId` int(10) DEFAULT NULL,
  KEY `FK_gamesession_user_user` (`userId`),
  KEY `FK_game_user_teams` (`teamId`),
  CONSTRAINT `FK_game_user_teams` FOREIGN KEY (`teamId`) REFERENCES `teams` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_gamesession_user_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table jass.teams_users: ~0 rows (approximately)
DELETE FROM `teams_users`;
/*!40000 ALTER TABLE `teams_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `teams_users` ENABLE KEYS */;


-- Dumping structure for table jass.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(102) NOT NULL,
  `dateofbirth` date NOT NULL DEFAULT '2010-10-20',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='This table is used for storing users';

-- Dumping data for table jass.user: ~2 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `dateofbirth`) VALUES
  (20, 'abcdef', '1000:29213a64dde5e385ef3d312b9328ec586bd20de98868ebd7:38ba42bc368d733debef4c6adac214b0b2e627687af8d3c2', '1992-01-07'),
  (21, 'regtest', '1000:094e0c0f0805e279d46a21c64a7d37b929c27e9eb45374a8:a0d1191e3b0071c440fef51472dd864914133d80220f2b98', '1992-04-07');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
