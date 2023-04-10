-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 02 avr. 2023 à 16:19
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `mediatheque`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonne`
--

DROP TABLE IF EXISTS `abonne`;
CREATE TABLE IF NOT EXISTS `abonne` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `abonne`
--

INSERT INTO `abonne` (`id`, `nom`, `dateNaissance`) VALUES
(1, 'louis', '2003-07-19'),
(2, 'Gabriel', '2003-02-20'),
(3, 'Jean-Michelle', '1990-05-20'),
(4, 'Jean-François', '2000-06-29'),
(5, 'Bernard', '1945-05-20'),
(6, 'Karim', '2020-09-12'),
(7, 'Kylian', '2010-03-18'),
(8, 'Hugo', '2013-12-02'),
(9, 'Yvann', '2011-01-15'),
(10, 'Leo', '2015-03-25');

-- --------------------------------------------------------

--
-- Structure de la table `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE IF NOT EXISTS `document` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(50) NOT NULL,
  `adulte` tinyint(1) DEFAULT NULL,
  `Emprunteur` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Emprunteur` (`Emprunteur`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `document`
--

INSERT INTO `document` (`id`, `titre`, `adulte`, `Emprunteur`) VALUES
(1, 'Fast and Furious', 0, NULL),
(2, 'Star Wars', 0, NULL),
(3, 'Harry Potter', 0, NULL),
(4, 'Rocky', 0, NULL),
(5, 'Avatar', 0, NULL),
(6, 'Le loup de Wall Street', 1, NULL),
(7, 'Fight Club', 1, NULL),
(8, 'Creed', 1, NULL),
(9, 'Scarface', 1, NULL),
(10, 'Pulp Fiction', 1, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
