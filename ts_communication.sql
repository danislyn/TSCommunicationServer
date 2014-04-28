-- MySQL dump 10.13  Distrib 5.1.53, for Win32 (ia32)
--
-- Host: localhost    Database: ts_communication
-- ------------------------------------------------------
-- Server version	5.1.53-community

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
-- Table structure for table `adeptness_definition_table`
--

DROP TABLE IF EXISTS `adeptness_definition_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adeptness_definition_table` (
  `adeptness` int(11) NOT NULL,
  `adeptness_definition` varchar(10) NOT NULL,
  PRIMARY KEY (`adeptness`,`adeptness_definition`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adeptness_definition_table`
--

LOCK TABLES `adeptness_definition_table` WRITE;
/*!40000 ALTER TABLE `adeptness_definition_table` DISABLE KEYS */;
INSERT INTO `adeptness_definition_table` VALUES (1,'Java'),(2,'C/C++'),(3,'C#'),(4,'Ruby'),(5,'JavaScript'),(6,'汇编'),(7,'开源框架'),(8,'嵌入式'),(9,'Web开发 '),(10,'模式识别');
/*!40000 ALTER TABLE `adeptness_definition_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_teacher_comment_table`
--

DROP TABLE IF EXISTS `admin_teacher_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_teacher_comment_table` (
  `p_id` int(11) NOT NULL,
  `commenter_username` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`p_id`,`commenter_username`,`time`),
  KEY `admin_teacher_comment_table_ibfk_1` (`commenter_username`),
  KEY `admin_teacher_comment_table_ibfk2` (`p_id`),
  CONSTRAINT `admin_teacher_comment_table_ibfk2` FOREIGN KEY (`p_id`) REFERENCES `admin_teacher_post_table` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `admin_teacher_comment_table_ibfk_1` FOREIGN KEY (`commenter_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_teacher_comment_table`
--

LOCK TABLES `admin_teacher_comment_table` WRITE;
/*!40000 ALTER TABLE `admin_teacher_comment_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_teacher_comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_teacher_post_table`
--

DROP TABLE IF EXISTS `admin_teacher_post_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_teacher_post_table` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category` varchar(8) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `read_sum` int(11) DEFAULT '0',
  `is_top` bit(1) DEFAULT b'0',
  PRIMARY KEY (`p_id`),
  KEY `admin_teacher_post_table_ibfk_1` (`sender_username`),
  CONSTRAINT `admin_teacher_post_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_teacher_post_table`
--

LOCK TABLES `admin_teacher_post_table` WRITE;
/*!40000 ALTER TABLE `admin_teacher_post_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_teacher_post_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement_feedback_table`
--

DROP TABLE IF EXISTS `announcement_feedback_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement_feedback_table` (
  `a_id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  PRIMARY KEY (`a_id`,`username`),
  KEY `announcement_feedback_table_ibfk_1` (`username`),
  KEY `announcement_feedbakc_table_ibfk_2` (`a_id`),
  CONSTRAINT `announcement_feedback_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `announcement_feedbakc_table_ibfk_2` FOREIGN KEY (`a_id`) REFERENCES `announcement_table` (`a_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement_feedback_table`
--

LOCK TABLES `announcement_feedback_table` WRITE;
/*!40000 ALTER TABLE `announcement_feedback_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcement_feedback_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement_table`
--

DROP TABLE IF EXISTS `announcement_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement_table` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category` varchar(8) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`a_id`),
  KEY `announcement_table_ibfk_1` (`sender_username`),
  CONSTRAINT `announcement_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement_table`
--

LOCK TABLES `announcement_table` WRITE;
/*!40000 ALTER TABLE `announcement_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcement_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_teacher_comment_table`
--

DROP TABLE IF EXISTS `department_teacher_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_teacher_comment_table` (
  `p_id` int(11) NOT NULL,
  `commenter_username` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`p_id`,`commenter_username`,`time`),
  KEY `department_teacher_comment_table_ibfk_1` (`commenter_username`),
  KEY `department_teacher_comment_table_ibfk_2` (`p_id`),
  CONSTRAINT `department_teacher_comment_table_ibfk_1` FOREIGN KEY (`commenter_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `department_teacher_comment_table_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `department_teacher_post_table` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_teacher_comment_table`
--

LOCK TABLES `department_teacher_comment_table` WRITE;
/*!40000 ALTER TABLE `department_teacher_comment_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `department_teacher_comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_teacher_post_table`
--

DROP TABLE IF EXISTS `department_teacher_post_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_teacher_post_table` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category` varchar(8) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `read_sum` int(11) DEFAULT '0',
  `is_top` bit(1) DEFAULT b'0',
  PRIMARY KEY (`p_id`),
  KEY `department_teacher_post_table_ibfk_1` (`sender_username`),
  CONSTRAINT `department_teacher_post_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_teacher_post_table`
--

LOCK TABLES `department_teacher_post_table` WRITE;
/*!40000 ALTER TABLE `department_teacher_post_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `department_teacher_post_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hobby_definition_table`
--

DROP TABLE IF EXISTS `hobby_definition_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hobby_definition_table` (
  `hobby` int(11) NOT NULL,
  `hobby_definition` varchar(10) NOT NULL,
  PRIMARY KEY (`hobby`,`hobby_definition`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hobby_definition_table`
--

LOCK TABLES `hobby_definition_table` WRITE;
/*!40000 ALTER TABLE `hobby_definition_table` DISABLE KEYS */;
INSERT INTO `hobby_definition_table` VALUES (1,'篮球'),(2,'足球'),(3,'排球'),(4,'羽毛球'),(5,'乒乓球'),(6,'自行车'),(7,'文学'),(8,'电影'),(9,'音乐'),(10,'街舞'),(11,'游戏'),(12,'其他');
/*!40000 ALTER TABLE `hobby_definition_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_info_table`
--

DROP TABLE IF EXISTS `lab_info_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lab_info_table` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`l_id`),
  KEY `lab_info_table_ibfk_1` (`username`),
  CONSTRAINT `lab_info_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_info_table`
--

LOCK TABLES `lab_info_table` WRITE;
/*!40000 ALTER TABLE `lab_info_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_info_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_table`
--

DROP TABLE IF EXISTS `log_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_table` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  `description` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `log_table_ibfk_1` (`username`),
  CONSTRAINT `log_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_table`
--

LOCK TABLES `log_table` WRITE;
/*!40000 ALTER TABLE `log_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail_table`
--

DROP TABLE IF EXISTS `mail_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail_table` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(600) DEFAULT NULL,
  `is_feedback` bit(1) DEFAULT b'0',
  `feedback_sum` int(11) DEFAULT '0',
  `receiver_sum` int(11) DEFAULT '0',
  `is_delete` bit(1) DEFAULT b'0',
  PRIMARY KEY (`m_id`),
  KEY `mail_table_ibfk_1` (`sender_username`),
  CONSTRAINT `mail_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail_table`
--

LOCK TABLES `mail_table` WRITE;
/*!40000 ALTER TABLE `mail_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_teacher_comment_table`
--

DROP TABLE IF EXISTS `major_teacher_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major_teacher_comment_table` (
  `p_id` int(11) NOT NULL,
  `commenter_username` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`p_id`,`commenter_username`,`time`),
  KEY `major_teacher_comment_table_ibfk_1` (`commenter_username`),
  KEY `major_teacher_comment_table_ibfk_2` (`p_id`),
  CONSTRAINT `major_teacher_comment_table_ibfk_1` FOREIGN KEY (`commenter_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `major_teacher_comment_table_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `major_teacher_post_table` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_teacher_comment_table`
--

LOCK TABLES `major_teacher_comment_table` WRITE;
/*!40000 ALTER TABLE `major_teacher_comment_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `major_teacher_comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_teacher_post_table`
--

DROP TABLE IF EXISTS `major_teacher_post_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major_teacher_post_table` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category` varchar(8) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `read_sum` int(11) DEFAULT '0',
  `is_top` bit(1) DEFAULT b'0',
  PRIMARY KEY (`p_id`),
  KEY `major_teacher_post_table_ibfk_1` (`sender_username`),
  CONSTRAINT `major_teacher_post_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_teacher_post_table`
--

LOCK TABLES `major_teacher_post_table` WRITE;
/*!40000 ALTER TABLE `major_teacher_post_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `major_teacher_post_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `online_user_table`
--

DROP TABLE IF EXISTS `online_user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `online_user_table` (
  `username` varchar(10) NOT NULL,
  `location` int(11) DEFAULT '0',
  PRIMARY KEY (`username`),
  KEY `online_user_table_ibfk_1` (`username`),
  CONSTRAINT `online_user_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `online_user_table`
--

LOCK TABLES `online_user_table` WRITE;
/*!40000 ALTER TABLE `online_user_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `online_user_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_adeptness_table`
--

DROP TABLE IF EXISTS `personal_adeptness_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_adeptness_table` (
  `username` varchar(10) NOT NULL,
  `adeptness` int(11) NOT NULL,
  PRIMARY KEY (`username`,`adeptness`),
  KEY `personal_adeptness_table_ibfk_1` (`username`),
  KEY `personal_adeptness_table_ibfk_2` (`adeptness`),
  CONSTRAINT `personal_adeptness_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `personal_adeptness_table_ibfk_2` FOREIGN KEY (`adeptness`) REFERENCES `adeptness_definition_table` (`adeptness`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_adeptness_table`
--

LOCK TABLES `personal_adeptness_table` WRITE;
/*!40000 ALTER TABLE `personal_adeptness_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_adeptness_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_favourite_teacher_table`
--

DROP TABLE IF EXISTS `personal_favourite_teacher_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_favourite_teacher_table` (
  `username` varchar(10) NOT NULL,
  `favourite_teacher` varchar(8) NOT NULL,
  PRIMARY KEY (`username`,`favourite_teacher`),
  KEY `personal_favourite_teacher_table_ibfk_1` (`username`),
  CONSTRAINT `personal_favourite_teacher_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_favourite_teacher_table`
--

LOCK TABLES `personal_favourite_teacher_table` WRITE;
/*!40000 ALTER TABLE `personal_favourite_teacher_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_favourite_teacher_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_hobby_table`
--

DROP TABLE IF EXISTS `personal_hobby_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_hobby_table` (
  `username` varchar(10) NOT NULL,
  `hobby` int(11) NOT NULL,
  PRIMARY KEY (`username`,`hobby`),
  KEY `personal_hobby_table_ibfk_1` (`username`),
  KEY `personal_hobby_table_ibfk_2` (`hobby`),
  CONSTRAINT `personal_hobby_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `personal_hobby_table_ibfk_2` FOREIGN KEY (`hobby`) REFERENCES `hobby_definition_table` (`hobby`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_hobby_table`
--

LOCK TABLES `personal_hobby_table` WRITE;
/*!40000 ALTER TABLE `personal_hobby_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_hobby_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_image_table`
--

DROP TABLE IF EXISTS `personal_image_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_image_table` (
  `username` varchar(10) NOT NULL,
  `image_name` varchar(20) NOT NULL,
  `image_path` varchar(45) NOT NULL,
  `image_size` int(11) DEFAULT '0',
  PRIMARY KEY (`username`),
  KEY `personal_image_table_ibfk_1` (`username`),
  CONSTRAINT `personal_image_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_image_table`
--

LOCK TABLES `personal_image_table` WRITE;
/*!40000 ALTER TABLE `personal_image_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_image_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_address_group_table`
--

DROP TABLE IF EXISTS `private_address_group_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `private_address_group_table` (
  `username` varchar(10) NOT NULL,
  `group_number` int(11) NOT NULL,
  `group_name` varchar(16) DEFAULT '未命名',
  PRIMARY KEY (`username`,`group_number`),
  KEY `private_address_group_table_ibfk_1` (`username`),
  CONSTRAINT `private_address_group_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_address_group_table`
--

LOCK TABLES `private_address_group_table` WRITE;
/*!40000 ALTER TABLE `private_address_group_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `private_address_group_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_address_table`
--

DROP TABLE IF EXISTS `private_address_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `private_address_table` (
  `username` varchar(10) NOT NULL,
  `contact_username` varchar(10) NOT NULL,
  `group_number` int(11) NOT NULL,
  PRIMARY KEY (`username`,`contact_username`,`group_number`),
  KEY `private_address_table_ibfk_1` (`username`),
  KEY `private_address_table_ibfk_2` (`contact_username`),
  CONSTRAINT `private_address_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `private_address_table_ibfk_2` FOREIGN KEY (`contact_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_address_table`
--

LOCK TABLES `private_address_table` WRITE;
/*!40000 ALTER TABLE `private_address_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `private_address_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receiver_mail_table`
--

DROP TABLE IF EXISTS `receiver_mail_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `receiver_mail_table` (
  `receiver_username` varchar(10) NOT NULL,
  `m_id` int(11) NOT NULL,
  `is_read` bit(1) DEFAULT b'0',
  `is_delete` bit(1) DEFAULT b'0',
  PRIMARY KEY (`receiver_username`,`m_id`),
  KEY `receiver_mail_table_ibfk_1` (`receiver_username`),
  KEY `receiver_mail_table_ibfk_2` (`m_id`),
  CONSTRAINT `receiver_mail_table_ibfk_1` FOREIGN KEY (`receiver_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `receiver_mail_table_ibfk_2` FOREIGN KEY (`m_id`) REFERENCES `mail_table` (`m_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receiver_mail_table`
--

LOCK TABLES `receiver_mail_table` WRITE;
/*!40000 ALTER TABLE `receiver_mail_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `receiver_mail_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_table`
--

DROP TABLE IF EXISTS `schedule_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_table` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `content` varchar(100) DEFAULT NULL,
  `location` int(11) DEFAULT '0',
  PRIMARY KEY (`s_id`),
  KEY `schedule_table_ibfk_1` (`username`),
  CONSTRAINT `schedule_table_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_table`
--

LOCK TABLES `schedule_table` WRITE;
/*!40000 ALTER TABLE `schedule_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shared_file_table`
--

DROP TABLE IF EXISTS `shared_file_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shared_file_table` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `contributor_username` varchar(10) NOT NULL,
  `file_name` varchar(32) NOT NULL,
  `file_path` varchar(90) NOT NULL,
  `file_size` int(11) DEFAULT '0',
  PRIMARY KEY (`file_id`),
  KEY `shared_file_table_ibfk_1` (`contributor_username`),
  CONSTRAINT `shared_file_table_ibfk_1` FOREIGN KEY (`contributor_username`) REFERENCES `user_table` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shared_file_table`
--

LOCK TABLES `shared_file_table` WRITE;
/*!40000 ALTER TABLE `shared_file_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `shared_file_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_comment_table`
--

DROP TABLE IF EXISTS `student_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_comment_table` (
  `p_id` int(11) NOT NULL,
  `commenter_username` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`p_id`,`commenter_username`,`time`),
  KEY `student_comment_table_ibfk_1` (`commenter_username`),
  KEY `student_comment_table_ibfk_2` (`p_id`),
  CONSTRAINT `student_comment_table_ibfk_1` FOREIGN KEY (`commenter_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_comment_table_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `student_post_table` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_comment_table`
--

LOCK TABLES `student_comment_table` WRITE;
/*!40000 ALTER TABLE `student_comment_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_post_table`
--

DROP TABLE IF EXISTS `student_post_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_post_table` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category` varchar(8) DEFAULT NULL,
  `time` datetime NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `read_sum` int(11) DEFAULT '0',
  `is_top` bit(1) DEFAULT b'0',
  PRIMARY KEY (`p_id`),
  KEY `student_post_table_ibfk_1` (`sender_username`),
  CONSTRAINT `student_post_table_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `user_table` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_post_table`
--

LOCK TABLES `student_post_table` WRITE;
/*!40000 ALTER TABLE `student_post_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_post_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_table` (
  `username` varchar(10) NOT NULL,
  `password` varchar(16) NOT NULL,
  `authority` int(11) NOT NULL,
  `last_time` datetime DEFAULT NULL,
  `name` varchar(8) NOT NULL,
  `grade` int(11) NOT NULL,
  `klass` int(11) DEFAULT NULL,
  `signature` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
INSERT INTO `user_table` VALUES ('admin','123',5,NULL,'管理员',0,0,NULL);
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-12 22:33:01
