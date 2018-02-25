-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 25, 2018 at 02:40 PM
-- Server version: 5.6.11
-- PHP Version: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_ipm`
--
CREATE DATABASE IF NOT EXISTS `db_ipm` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `db_ipm`;

-- --------------------------------------------------------

--
-- Table structure for table `datas`
--

CREATE TABLE IF NOT EXISTS `datas` (
  `tahun` varchar(4) NOT NULL,
  `t5` double NOT NULL,
  `t4` double NOT NULL,
  `t3` double NOT NULL,
  `t2` double NOT NULL,
  `t1` double NOT NULL,
  `target` double NOT NULL,
  `kategori` enum('Data Latih','Data Uji') NOT NULL,
  PRIMARY KEY (`tahun`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `datas`
--

INSERT INTO `datas` (`tahun`, `t5`, `t4`, `t3`, `t2`, `t1`, `target`, `kategori`) VALUES
('2001', 71.8, 70.25, 70.25, 68.7, 69.75, 69.75, 'Data Latih'),
('2002', 70.25, 70.25, 68.7, 69.75, 69.75, 70.8, 'Data Latih'),
('2003', 70.25, 68.7, 69.75, 69.75, 70.8, 71.85, 'Data Latih'),
('2004', 68.7, 69.75, 69.75, 70.8, 71.85, 72.9, 'Data Latih'),
('2005', 69.75, 69.75, 70.8, 71.85, 72.9, 73.5, 'Data Latih'),
('2006', 69.75, 70.8, 71.85, 72.9, 73.5, 73.7, 'Data Latih'),
('2007', 70.8, 71.85, 72.9, 73.5, 73.7, 74.15, 'Data Latih'),
('2008', 71.85, 72.9, 73.5, 73.7, 74.15, 74.88, 'Data Latih'),
('2009', 72.9, 73.5, 73.7, 74.15, 74.88, 75.23, 'Data Latih'),
('2010', 73.5, 73.7, 74.15, 74.88, 75.23, 75.37, 'Data Latih'),
('2011', 73.7, 74.15, 74.88, 75.23, 75.37, 75.93, 'Data Latih'),
('2012', 74.15, 74.88, 75.23, 75.37, 75.93, 76.15, 'Data Latih'),
('2013', 74.88, 75.23, 75.37, 75.93, 76.15, 76.44, 'Data Latih'),
('2014', 75.23, 75.37, 75.93, 76.15, 76.44, 76.81, 'Data Uji'),
('2015', 75.37, 75.93, 76.15, 76.44, 76.81, 77.59, 'Data Uji'),
('2016', 75.93, 76.15, 76.44, 76.81, 77.59, 78.38, 'Data Uji');

-- --------------------------------------------------------

--
-- Table structure for table `learnings`
--

CREATE TABLE IF NOT EXISTS `learnings` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `arsitektur` varchar(5) NOT NULL,
  `lr` double NOT NULL,
  `epoch` int(6) NOT NULL,
  `mse` double NOT NULL,
  `akurasi_latih` double NOT NULL,
  `akurasi_uji` double NOT NULL,
  `aktivasi` enum('Biner','Bipolar') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `learnings`
--

INSERT INTO `learnings` (`id`, `arsitektur`, `lr`, `epoch`, `mse`, `akurasi_latih`, `akurasi_uji`, `aktivasi`) VALUES
(1, '5-3-1', 0.1, 10000, 0.00140892, 99.6, 98.56, 'Biner'),
(2, '5-3-1', 0.1, 10000, 0.00080686, 99.85, 98.79, 'Bipolar'),
(3, '5-3-1', 0.01, 10000, 0.00365197, 99.4, 98.21, 'Biner'),
(4, '5-3-1', 0.01, 10000, 0.00636391, 99.57, 98.57, 'Bipolar'),
(5, '5-6-1', 0.1, 10000, 0.00121521, 99.64, 98.69, 'Biner'),
(6, '5-6-1', 0.1, 10000, 0.00305164, 99.72, 99.13, 'Bipolar'),
(7, '5-6-1', 0.01, 10000, 0.00441266, 99.39, 98.4, 'Biner'),
(8, '5-6-1', 0.01, 10000, 0.00481516, 99.61, 98.47, 'Bipolar'),
(9, '5-9-1', 0.1, 10000, 0.00116075, 99.64, 98.73, 'Biner'),
(10, '5-9-1', 0.1, 10000, 0.00385234, 99.68, 98.91, 'Bipolar'),
(11, '5-9-1', 0.01, 10000, 0.00375027, 99.4, 98.24, 'Biner'),
(12, '5-9-1', 0.01, 10000, 0.00397927, 99.66, 98.87, 'Bipolar');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
