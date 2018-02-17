CREATE DATABASE  IF NOT EXISTS `epm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `epm`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: epm
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id_customer` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id_customer`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (52,'user','','user@gmail.com','15af7b14054daaaad11baf2f4e859021e218cfccfded40362059706ec927df68',0);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_to_package`
--

DROP TABLE IF EXISTS `customer_to_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_to_package` (
  `id_customer` int(11) DEFAULT NULL,
  `id_package` int(11) DEFAULT NULL,
  KEY `del_p_link_idx` (`id_package`),
  KEY `del_p_link_c_idx` (`id_customer`),
  CONSTRAINT `pck_de_dep` FOREIGN KEY (`id_package`) REFERENCES `package` (`id_package`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `usr_de` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id_customer`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_to_package`
--

LOCK TABLES `customer_to_package` WRITE;
/*!40000 ALTER TABLE `customer_to_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_to_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `id_language` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id_language`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'English'),(2,'Русский');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `id_package` int(11) NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `type` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_package`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (13,50.00,1,'/pictures/internet2.jpg'),(14,75.00,1,'/pictures/internet.png'),(16,100.00,1,'/pictures/1.jpg'),(19,60.00,2,'/pictures/tv.jpg'),(20,80.00,2,'/pictures/tv_2.jpg'),(21,40.00,3,'/pictures/mobile.png'),(22,100.00,3,'/pictures/vodafone.png');
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_description`
--

DROP TABLE IF EXISTS `package_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_description` (
  `id_type_description` int(11) NOT NULL AUTO_INCREMENT,
  `id_package` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_language` int(11) NOT NULL,
  PRIMARY KEY (`id_type_description`),
  KEY `package_description_package_id_package_fk` (`id_package`),
  CONSTRAINT `package_description_package_id_package_fk` FOREIGN KEY (`id_package`) REFERENCES `package` (`id_package`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_description`
--

LOCK TABLES `package_description` WRITE;
/*!40000 ALTER TABLE `package_description` DISABLE KEYS */;
INSERT INTO `package_description` VALUES (11,13,'Internet basic','10mb/s',1),(14,14,'Internet extra','20mb/s',1),(15,16,'Internet extra plus','50mb/s',1),(16,13,'Интернет базовый','10мб/с',2),(17,14,'Интернет экстра','20мб/с',2),(18,16,'Интернет экстра плюс','50мб/с',2),(24,19,'TV basic','10 channels',1),(25,19,'ТВ базовый','10 каналов',2),(26,20,'TV extra','20 channels',1),(27,20,'ТВ экстра','20 каналов',2),(28,21,'Life smart','500 mb internet, unlimited calls',1),(29,21,'Life смарт','500 мб интернета, безлимит в сети',2),(30,22,'Vodafone 4g','Unlimited 4g internet, unlimited calls',1),(31,22,'Водафон 4g','Безлимитный 4g интернет, безлимит на звонки',2);
/*!40000 ALTER TABLE `package_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'admin_1@gmail.com','password',1),(10,'admin_2@gmail.com','password',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_order`
--

DROP TABLE IF EXISTS `work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_order` (
  `id_work_order` int(11) NOT NULL AUTO_INCREMENT,
  `id_customer` int(11) DEFAULT NULL,
  `id_package` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_work_order`),
  KEY `work_order_customer_id_customer_fk` (`id_customer`),
  KEY `work_order_package_id_package_fk` (`id_package`),
  CONSTRAINT `work_order_customer_id_customer_fk` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id_customer`) ON DELETE CASCADE,
  CONSTRAINT `work_order_package_id_package_fk` FOREIGN KEY (`id_package`) REFERENCES `package` (`id_package`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_order`
--

LOCK TABLES `work_order` WRITE;
/*!40000 ALTER TABLE `work_order` DISABLE KEYS */;
INSERT INTO `work_order` VALUES (52,52,16,'2018-02-14 21:00:47','2018-03-15 00:00:00',1),(53,52,19,'2018-02-15 09:54:22','2018-03-15 00:00:00',1),(54,52,21,'2018-02-15 10:13:19','2018-03-15 00:00:00',1);
/*!40000 ALTER TABLE `work_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'epm'
--

--
-- Dumping routines for database 'epm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-17 11:57:16
