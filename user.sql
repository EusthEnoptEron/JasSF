-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 18. Dez 2014 um 14:42
-- Server Version: 5.6.11
-- PHP-Version: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `jass`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(102) NOT NULL,
  `dateofbirth` date NOT NULL DEFAULT '2010-10-20',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This table is used for storing users' AUTO_INCREMENT=22 ;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `dateofbirth`) VALUES
(1, 'bie1', 'xxx2', '1954-08-12'),
(2, 'bie2', 'test', '1980-01-02'),
(14, 'Simon', 'lol', '1990-11-09'),
(15, 'hai', 'abc', '1992-04-07'),
(16, 'abc', 'hai', '1992-04-07'),
(20, 'abcdef', '1000:29213a64dde5e385ef3d312b9328ec586bd20de98868ebd7:38ba42bc368d733debef4c6adac214b0b2e627687af8d3c2', '1992-01-07'),
(21, 'regtest', '1000:094e0c0f0805e279d46a21c64a7d37b929c27e9eb45374a8:a0d1191e3b0071c440fef51472dd864914133d80220f2b98', '1992-04-07');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
