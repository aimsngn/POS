-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shop_three
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_first` varchar(50) DEFAULT NULL,
  `customer_last` varchar(50) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'amelie','ngan','2095391350'),(2,'adrian','perez','2095557777'),(3,'kirby','girl','2097778888'),(4,'hallie','bieber','2095643121'),(5,'kim','yan','0291112222'),(6,'judy','smith','2097778888'),(7,'bi','bi','2095391350'),(8,'jody','brunch','1112223333'),(9,'jollibee','rockz','4445556666'),(10,'jung','kook','2095391359'),(11,'kiki','spirit','2134334423'),(12,'Eddie','Huang','2109998878'),(13,'jessica','huang','7879092343'),(14,'Louis','Tan','8922425422'),(15,'juju','kiki','8979866475'),(16,'walter','smith','2095931350'),(17,'lil','man','4244244235'),(18,'Milo','Bunny','9098988989'),(19,'edddie','huang','4334432345'),(20,'maya','ochija','2221119999'),(21,'anna','smith','7778889999'),(22,'Kyla','Kim','2327768889'),(23,'Karla','Manala','9098987877'),(24,'Smiski','Miniso','6667788988'),(25,'Karla','Ochija','7771111223'),(26,'kyoko','hori','4353782342');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `hourly_pay` decimal(5,2) DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `passwords` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  CONSTRAINT `chk_hourly_pay` CHECK ((`hourly_pay` >= 15.50))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Jim','Halpert',16.50,'2023-01-01','$2a$10$JtHqM19.e/sgQ1cl1BTsYOm5JZc7mbP/pFNJcWjnui/3jQ6NGOQB2'),(2,'Dwight','Schrute',16.50,'2023-01-01','$2a$10$/wAfJxFwWF/QSSjoaS/hguo2AF.w6HSOUMprxpfQKt3bb7Mth2NJm'),(3,'Pam','Halpert',16.50,'2023-02-01','$2a$10$eUi2Wh.GiswMfxfV1gAH5OweUkrDRKZuCxdtK87TUIo.JciqiH1eO'),(4,'Angela','Schrute',16.00,'2023-02-01','$2a$10$LWbagliQCg9aha5Zkj2WWODYsE./xy19S5ja9X9bE0cIqye4mZBbC'),(5,'Ryan','Howard',15.50,'2023-03-01','$2a$10$fXf3jTvoB0.Pd/vheGthk.UEmkDd5bJnVYfvDVURAPPz9/lKX56Ae');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(25) DEFAULT NULL,
  `price` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Matcha',6.49),(2,'Strawberry Latte',6.49),(3,'Taro Latte',6.49),(4,'Americano',4.49),(5,'Espresso',4.49),(6,'Black Coffee',3.50),(7,'Cappuccino',4.59),(8,'Almond Milk',2.50),(9,'Soy Milk',2.50);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trans_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` varchar(50) DEFAULT NULL,
  `subtotal` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transactions_customers` (`customer_id`),
  KEY `fk_transaction_employees` (`employee_id`),
  CONSTRAINT `fk_transaction_employees` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`emp_id`),
  CONSTRAINT `fk_transactions_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'2023-07-14 07:57:39',NULL,NULL,'[1, 4]',10.00),(2,'2023-07-14 07:59:59',NULL,NULL,'[1, 4]',10.00),(3,'2023-07-14 08:02:54',NULL,1,'[1, 4]',10.98),(4,'2023-07-14 08:06:32',NULL,NULL,'[4]',4.49),(5,'2023-07-14 08:39:24',NULL,NULL,'[4]',4.49),(6,'2023-07-14 08:43:12',NULL,NULL,'[1, 4]',10.98),(7,'2023-07-14 22:34:47',NULL,7,'[6]',3.50),(8,'2023-07-17 06:12:45',NULL,1,'[9, 8]',5.00),(9,'2023-07-17 09:05:49',NULL,NULL,'[9]',2.50),(10,'2023-07-17 09:07:47',NULL,NULL,'[9, 8]',5.00),(11,'2023-07-17 09:20:42',NULL,1,'[4]',4.49),(12,'2023-07-18 00:02:48',NULL,8,'[4]',4.49),(13,'2023-07-18 01:44:40',NULL,NULL,'[4]',4.49),(14,'2023-07-19 07:36:27',1,NULL,'[1]',6.49),(15,'2023-07-19 07:41:36',4,1,'[1, 8]',8.99),(16,'2023-07-22 07:20:13',1,NULL,'[8]',2.50),(17,'2023-07-24 07:28:16',1,NULL,'[]',0.00),(18,'2023-07-24 07:42:33',3,NULL,'[4]',4.49),(19,'2023-07-24 07:48:45',2,NULL,'[1]',6.49),(20,'2023-07-24 07:50:06',1,NULL,'[]',0.00),(21,'2023-07-24 07:52:55',1,NULL,'[8]',2.50),(22,'2023-07-24 08:00:20',1,NULL,'[1]',6.49),(23,'2023-07-24 08:47:36',2,NULL,'[1]',6.49),(24,'2023-07-24 08:48:12',2,NULL,'[8]',2.50),(25,'2023-07-24 08:53:08',2,NULL,'[1]',6.49),(26,'2023-07-24 08:53:38',2,1,'[1, 2]',12.98),(27,'2023-07-24 08:56:13',1,NULL,'[1, 8]',8.99),(28,'2023-07-24 08:57:42',1,9,'[1, 8, 3]',15.48),(29,'2023-07-24 09:12:45',4,10,'[8]',2.50),(30,'2023-07-24 09:18:11',1,11,'[5]',4.49),(31,'2023-07-24 09:22:34',2,NULL,'[1]',6.49),(32,'2023-07-24 09:22:59',3,NULL,'[1, 1]',12.98),(33,'2023-07-24 09:29:45',4,1,'[3, 7]',11.08),(34,'2023-07-24 09:29:59',4,NULL,'[3, 7, 8]',13.58),(35,'2023-07-24 09:33:11',2,NULL,'[1]',6.49),(36,'2023-07-24 09:38:40',4,NULL,'[1]',6.49),(37,'2023-07-24 09:38:49',4,NULL,'[1, 9]',8.99),(38,'2023-07-24 20:47:23',2,NULL,'[1, 8]',8.99),(39,'2023-07-24 20:47:50',2,1,'[4, 6]',7.99),(40,'2023-07-24 20:49:45',2,NULL,'[1]',6.49),(41,'2023-07-24 21:12:44',1,12,'[1]',6.49),(42,'2023-07-25 03:22:16',1,NULL,'[4]',4.49),(43,'2023-07-25 03:22:36',1,1,'[7, 9]',7.09),(44,'2023-07-25 03:22:59',3,NULL,'[8]',2.50),(45,'2023-07-25 03:33:40',4,NULL,'[9]',2.50),(46,'2023-07-25 03:34:04',3,NULL,'[3]',6.49),(47,'2023-07-25 03:34:50',4,NULL,'[9]',2.50),(48,'2023-07-25 03:36:44',1,NULL,'[9]',2.50),(49,'2023-07-25 03:36:58',2,NULL,'[1]',6.49),(50,'2023-07-25 03:37:10',2,NULL,'[5]',4.49),(51,'2023-07-25 03:37:26',4,NULL,'[9]',2.50),(52,'2023-07-25 08:22:38',1,NULL,'[8]',2.50),(53,'2023-07-25 08:22:53',2,NULL,'[4]',4.49),(54,'2023-07-25 08:23:10',3,NULL,'[9]',2.50),(55,'2023-07-25 08:40:01',1,NULL,'[9]',2.50),(56,'2023-07-25 08:50:12',4,NULL,'[8, 9]',5.00),(57,'2023-07-25 09:06:58',1,NULL,'[8, 9]',5.00),(58,'2023-07-25 09:51:08',1,13,'[7]',4.59),(59,'2023-07-25 10:15:04',3,NULL,'[6]',3.50),(60,'2023-07-25 10:15:56',4,14,'[8, 9]',5.00),(61,'2023-07-25 10:16:08',4,NULL,'[9]',2.50),(62,'2023-07-25 11:05:15',2,NULL,'[1]',6.49),(63,'2023-07-25 11:05:48',2,15,'[9]',2.50),(64,'2023-07-26 00:38:18',1,NULL,'[9, 1, 1]',15.48),(65,'2023-07-26 00:40:43',2,NULL,'[]',0.00),(66,'2023-07-26 04:26:29',3,NULL,'[6, 6, 6]',10.50),(67,'2023-07-26 04:26:40',3,NULL,'[6, 6, 6]',10.50),(68,'2023-07-26 04:30:27',1,NULL,'[1, 1]',12.98),(69,'2023-07-26 04:31:27',2,NULL,'[1, 8]',8.99),(70,'2023-07-26 04:32:10',2,NULL,'[4, 9]',6.99),(71,'2023-07-26 04:35:14',1,NULL,'[5]',4.49),(72,'2023-07-26 04:35:30',1,NULL,'[5]',4.49),(73,'2023-07-26 04:44:22',1,NULL,'[1, 1]',12.98),(74,'2023-07-26 04:45:15',2,NULL,'[8, 9]',5.00),(75,'2023-07-26 04:50:13',1,NULL,'[1, 1]',12.98),(76,'2023-07-26 04:50:52',1,1,'[4]',4.49),(77,'2023-07-26 05:05:28',1,NULL,'[6, 6, 9]',9.50),(78,'2023-07-26 05:06:27',1,16,'[1, 8]',8.99),(79,'2023-07-26 05:15:11',1,NULL,'[4]',4.49),(80,'2023-07-26 05:16:37',1,NULL,'[1]',6.49),(81,'2023-07-26 07:57:00',1,1,'[5, 9]',6.99),(82,'2023-07-26 08:32:01',4,NULL,'[1, 1, 2, 6, 3, 9, 8]',34.46),(83,'2023-07-26 08:56:40',4,NULL,'[6, 9]',6.00),(84,'2023-07-27 07:29:48',2,NULL,'[1, 1, 8, 8, 6]',21.48),(85,'2023-07-27 08:16:51',2,NULL,'[1, 1, 9, 8, 6]',21.48),(86,'2023-07-27 08:18:19',2,NULL,'[9]',2.50),(87,'2023-07-27 08:18:47',2,NULL,'[9, 4]',6.99),(88,'2023-07-27 08:34:31',1,NULL,'[1, 8, 6]',12.49),(89,'2023-07-27 08:48:49',3,1,'[1]',6.49),(90,'2023-07-27 08:49:17',3,NULL,'[6, 6, 8]',9.50),(91,'2023-07-27 08:49:51',3,17,'[5]',4.49),(92,'2023-07-27 08:54:47',4,NULL,'[1]',6.49),(93,'2023-07-27 08:55:09',4,1,'[5]',4.49),(94,'2023-07-27 08:55:49',4,NULL,'[4]',4.49),(95,'2023-07-27 08:57:09',2,NULL,'[3]',6.49),(96,'2023-07-27 08:59:52',2,NULL,'[1, 5]',10.98),(97,'2023-07-27 09:01:03',4,NULL,'[6]',3.50),(98,'2023-07-29 04:12:50',2,1,'[6]',3.50),(99,'2023-07-29 04:13:38',2,18,'[5]',4.49),(100,'2023-07-29 04:14:49',2,NULL,'[1]',6.49),(101,'2023-07-29 04:15:41',2,NULL,'[1]',6.49),(102,'2023-07-29 04:16:17',2,19,'[8]',2.50),(103,'2023-07-29 04:46:12',1,NULL,'[4, 4]',8.98),(104,'2023-07-29 04:57:49',1,NULL,'[5, 4]',8.98),(105,'2023-07-29 05:19:12',1,NULL,'[5, 1, 6, 2]',20.97),(106,'2023-07-29 05:27:17',2,NULL,'[1, 3, 6, 2, 2]',29.46),(107,'2023-07-29 05:31:30',4,NULL,'[1, 6, 2, 8]',18.98),(108,'2023-07-29 07:18:45',3,NULL,'[4, 8, 1, 2]',19.97),(109,'2023-07-29 08:04:47',3,NULL,'[1, 8, 8, 3]',17.98),(110,'2023-07-29 08:16:54',4,NULL,'[7, 7]',9.18),(111,'2023-07-29 08:17:17',4,1,'[9, 1]',8.99),(112,'2023-07-29 08:17:56',2,NULL,'[1, 4]',10.98),(113,'2023-08-01 07:03:33',2,NULL,'[8, 1, 6]',12.49),(114,'2023-08-01 07:03:44',2,NULL,'[5]',4.49),(115,'2023-08-01 07:07:58',1,NULL,'[1, 5]',10.98),(116,'2023-08-01 07:08:25',1,20,'[6]',3.50),(117,'2023-08-01 07:14:45',3,NULL,'[4]',4.49),(118,'2023-08-01 07:15:08',3,21,'[7]',4.59),(119,'2023-08-01 07:15:21',2,NULL,'[1]',6.49),(120,'2023-08-02 00:59:32',2,22,'[8, 5]',6.99),(121,'2023-08-02 01:00:10',3,23,'[7, 7]',9.18),(122,'2023-08-02 01:00:45',1,24,'[6]',3.50),(123,'2023-08-02 01:01:36',4,25,'[7, 8]',7.09),(124,'2023-08-07 00:11:01',2,NULL,'[1]',6.49),(125,'2023-08-08 00:29:41',2,NULL,'[8, 1]',8.99),(126,'2023-08-08 00:31:44',3,1,'[6]',3.50),(127,'2023-08-08 00:32:02',3,NULL,'[2]',6.49),(128,'2023-08-08 00:33:44',1,26,'[5, 3, 7]',15.57),(129,'2023-08-08 00:51:54',2,1,'[1, 1]',12.98),(130,'2023-08-08 01:09:29',4,2,'[2, 2]',12.98),(131,'2023-08-08 01:25:38',4,2,'[6]',3.50),(132,'2023-08-08 07:23:37',4,NULL,'[7]',4.59),(133,'2023-08-08 21:23:29',2,1,'[1]',6.49);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'shop_three'
--

--
-- Dumping routines for database 'shop_three'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-08 14:32:42
