-- MySQL dump 10.13  Distrib 5.1.47, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: GROOP
-- ------------------------------------------------------
-- Server version	5.1.47-log

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
-- Table structure for table `Campaign`
--

DROP TABLE IF EXISTS `Campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Campaign` (
  `CID` char(36) NOT NULL,
  `Title` text NOT NULL,
  `Visual` varchar(1024) DEFAULT NULL,
  `US_Release` date DEFAULT NULL,
  `Release_Timeframe` varchar(256) DEFAULT NULL,
  `Release_Year` int(11) NOT NULL,
  `Movie_Type` varchar(256) DEFAULT NULL,
  `Target_Audience` varchar(256) DEFAULT NULL,
  `Secondary_Audience` varchar(256) DEFAULT NULL,
  `Security_Title` text,
  `Notes` text,
  `Status` varchar(256) NOT NULL,
  PRIMARY KEY (`CID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Campaign`
--

LOCK TABLES `Campaign` WRITE;
/*!40000 ALTER TABLE `Campaign` DISABLE KEYS */;
/*!40000 ALTER TABLE `Campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Checklist`
--

DROP TABLE IF EXISTS `Checklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Checklist` (
  `CLID` char(36) NOT NULL,
  `MID` char(36) NOT NULL,
  `Alert` tinyint(4) DEFAULT NULL,
  `Complete` tinyint(4) DEFAULT NULL,
  `Task` char(36) DEFAULT NULL,
  `Notes` text,
  `Owner` char(36) DEFAULT NULL,
  PRIMARY KEY (`CLID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Checklist`
--

LOCK TABLES `Checklist` WRITE;
/*!40000 ALTER TABLE `Checklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `Checklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Departments`
--

DROP TABLE IF EXISTS `Departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Departments` (
  `DID` char(36) NOT NULL,
  `Name` varchar(256) NOT NULL,
  `PrimaryEmail` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Departments`
--

LOCK TABLES `Departments` WRITE;
/*!40000 ALTER TABLE `Departments` DISABLE KEYS */;
INSERT INTO `Departments` VALUES ('046d7b76-e877-11e0-b8d2-25e1fb45693d','Planning',NULL),('0c576478-e877-11e0-b8d2-25e1fb45693d','Publicity',NULL),('128a1cc8-e877-11e0-b8d2-25e1fb45693d','Promotions',NULL),('16b0158c-e877-11e0-b8d2-25e1fb45693d','Print',NULL),('1b6d029c-e877-11e0-b8d2-25e1fb45693d','AV',NULL),('24914554-e877-11e0-b8d2-25e1fb45693d','Online Creative',NULL),('2e8bf3e2-e877-11e0-b8d2-25e1fb45693d','Synergy/Creative',NULL);
/*!40000 ALTER TABLE `Departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Event`
--

DROP TABLE IF EXISTS `Event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Event` (
  `EID` char(36) NOT NULL,
  `Name` varchar(256) NOT NULL,
  PRIMARY KEY (`EID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Event`
--

LOCK TABLES `Event` WRITE;
/*!40000 ALTER TABLE `Event` DISABLE KEYS */;
/*!40000 ALTER TABLE `Event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Milestone`
--

DROP TABLE IF EXISTS `Milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Milestone` (
  `MID` char(36) NOT NULL,
  `Name` varchar(256) NOT NULL,
  `StartDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `Priority` char(36) DEFAULT NULL,
  `CID` char(36) NOT NULL,
  `Status` char(36) NOT NULL,
  `Duration` int(11) NOT NULL,
  `In_Theater_Date` date NOT NULL,
  `Trailer_Target_Date` date NOT NULL,
  `Notes` text,
  `Link` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`MID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Milestone`
--

LOCK TABLES `Milestone` WRITE;
/*!40000 ALTER TABLE `Milestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `Milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Watches`
--

DROP TABLE IF EXISTS `Watches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Watches` (
  `WID` char(36) NOT NULL,
  `ItemID` char(36) NOT NULL,
  `ItemType` char(36) NOT NULL,
  `CID` char(36) NOT NULL,
  PRIMARY KEY (`WID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Watches`
--

LOCK TABLES `Watches` WRITE;
/*!40000 ALTER TABLE `Watches` DISABLE KEYS */;
/*!40000 ALTER TABLE `Watches` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-09-30 19:00:57
