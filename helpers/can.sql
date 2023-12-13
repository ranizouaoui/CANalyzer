-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2023 at 07:44 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `can`
--

-- --------------------------------------------------------

--
-- Table structure for table `can_frames`
--

CREATE TABLE `can_frames` (
  `index` int(11) NOT NULL,
  `IDENTIFIER` varchar(255) DEFAULT NULL,
  `DLC` varchar(255) DEFAULT NULL,
  `DATA` varchar(255) DEFAULT NULL,
  `Frame_Name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `can_frames`
--

INSERT INTO `can_frames` (`index`, `IDENTIFIER`, `DLC`, `DATA`, `Frame_Name`) VALUES
(1, '00000010100', '0010', '0001001000010010', 'SEND_null'),
(2, '00000010100', '0010', '0001001000010010', 'SEND_Data Frame'),
(3, '00000010100', '0010', '0001001000010010', 'SEND_Remote Frame'),
(4, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(5, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(6, '00000010100', '0000', '00010010', 'SEND_Remote Frame'),
(7, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(8, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(9, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(10, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(11, '00000010100', '0001', '00010010', 'SEND_Data Frame'),
(12, '00000010100', '0000', '00010010', 'SEND_Remote Frame'),
(13, '00000010100', '0000', '00010010', 'SEND_Remote Frame'),
(14, '00000010100', '0001', '00001111', 'SEND_Data Frame'),
(15, '00000010100', '0001', '00001111', 'SEND_Data Frame'),
(16, '00000010100', '0001', '00001111', 'SEND_Data Frame');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `user_password`, `role`) VALUES
(1, 'rani', 'rani', 'USER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `can_frames`
--
ALTER TABLE `can_frames`
  ADD PRIMARY KEY (`index`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `can_frames`
--
ALTER TABLE `can_frames`
  MODIFY `index` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
