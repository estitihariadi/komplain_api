-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 08, 2018 at 08:23 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `komplain_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `posts_rating`
--

CREATE TABLE `posts_rating` (
  `id` int(11) NOT NULL,
  `nim` int(11) NOT NULL,
  `id_komplain` int(11) NOT NULL,
  `rating` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posts_rating`
--

INSERT INTO `posts_rating` (`id`, `nim`, `id_komplain`, `rating`) VALUES
(1, 1631710037, 13, 5),
(2, 1631710037, 14, 1),
(3, 1631710037, 12, 5),
(4, 1631710037, 15, 5);

-- --------------------------------------------------------

--
-- Table structure for table `tb_admin`
--

CREATE TABLE `tb_admin` (
  `id_admin` int(11) NOT NULL,
  `nama_admin` varchar(50) NOT NULL,
  `username_admin` varchar(50) NOT NULL,
  `password_admin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_admin`
--

INSERT INTO `tb_admin` (`id_admin`, `nama_admin`, `username_admin`, `password_admin`) VALUES
(1, 'admin', 'iqbalcakep', '5c2fb951458b57e8e049d48a55cdddad'),
(3, 'Fahrul', 'lol', '9cdfb439c7876e703e307864c9167a15');

-- --------------------------------------------------------

--
-- Table structure for table `tb_kategori`
--

CREATE TABLE `tb_kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_kategori`
--

INSERT INTO `tb_kategori` (`id_kategori`, `nama_kategori`) VALUES
(1, 'Infrastruktur '),
(2, 'Layanan');

-- --------------------------------------------------------

--
-- Table structure for table `tb_komplain`
--

CREATE TABLE `tb_komplain` (
  `id_komplain` int(11) NOT NULL,
  `nim` int(12) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `judul` varchar(50) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `foto` text NOT NULL,
  `foto_after` text NOT NULL,
  `keluhan` text NOT NULL,
  `lokasi` varchar(100) NOT NULL,
  `tgl_komplain` date NOT NULL,
  `status` enum('Belum','Diproses','Ditolak','Selesai') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_komplain`
--

INSERT INTO `tb_komplain` (`id_komplain`, `nim`, `id_pegawai`, `judul`, `id_kategori`, `foto`, `foto_after`, `keluhan`, `lokasi`, `tgl_komplain`, `status`) VALUES
(12, 1631710037, 3, 'AIR MATI', 1, 'uploads/6.png', 'uploads/IMG_20181203_035112.jpg', 'Mohon segera di perbaiki air mati di gedung saya,sehingga kegiatan sholat terganggu karena tidak bisa ambil wudhu', 'Gedung Sipil lt 6', '2018-11-30', 'Belum'),
(13, 1631710038, 1, 'LCD RUSAK', 1, 'uploads/default11.png', 'uploads/1535159082290.jpg', 'di gedung sayang tidak bisa menggunakan lcd karena lcdnya rusak', 'LPj04 gd af lt2', '2018-11-30', 'Selesai'),
(14, 1631710037, 3, 'Kunci Kelas Hilang', 2, 'uploads/tempe7.jpg', 'uploads/IMG_20181205_102144.jpg', 'Kunci kelas hilang karena penyimpanan tidak ditata dengan baik', 'Gedung Sipil lt 12', '2018-12-01', 'Selesai'),
(15, 1631710038, 1, 'AIR MATI', 1, 'http://127.0.0.1/komplain/client/uploads/Screenshot_from_2018-10-31_13-01-49.png', 'http://127.0.0.1/komplain/client/uploads/Screenshot_from_2018-10-31_13-01-49.png', 'Mohon maaf air mati pak', 'Gedung Sipil lt 6', '2018-12-06', 'Selesai');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts_rating`
--
ALTER TABLE `posts_rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `tb_kategori`
--
ALTER TABLE `tb_kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `tb_komplain`
--
ALTER TABLE `tb_komplain`
  ADD PRIMARY KEY (`id_komplain`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `posts_rating`
--
ALTER TABLE `posts_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tb_admin`
--
ALTER TABLE `tb_admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_kategori`
--
ALTER TABLE `tb_kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_komplain`
--
ALTER TABLE `tb_komplain`
  MODIFY `id_komplain` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
