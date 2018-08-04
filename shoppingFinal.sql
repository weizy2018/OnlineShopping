CREATE DATABASE  IF NOT EXISTS `shopping` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shopping`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: shopping
-- ------------------------------------------------------
-- Server version	5.6.14

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
  `id` varchar(15) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `balance` decimal(14,2) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('123','123','张三','男',72702.00,'123456789@qq.com','1234567890','长春'),('1234','1234','王五','男',1000.00,'1234@qq.com','1234','吉大'),('12345','12345','李四','男',100.00,'12345@qq.com','12345','吉林'),('666','666','666','男',0.00,'666','666','666'),('888','888','888','男',0.00,'888','888','888'),('999','999','999',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(15) NOT NULL,
  `product_id` int(11) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `state` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `customer_id` (`customer_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'123',6,'666666','changchun','2018-06-08 09:37:24',10,'已验收'),(2,'123',9,'1234567890','长春','2018-06-11 14:01:42',2,'已验收'),(3,'123',8,'1234567890','长春','2018-06-12 10:47:54',1,'已验收'),(4,'123',7,'1234567890','长春','2018-06-11 14:01:09',2,'已退订'),(5,'123',10,'1234567890','长春','2018-06-11 14:02:10',2,'已验收'),(7,'123',6,'666666','changchun','2018-06-08 11:38:32',1,'已退订'),(8,'123',6,'666666','changchun','2018-06-08 12:12:44',1,'已退订'),(24,'123',23,'1234567890','吉林省长春市','2018-06-14 06:29:10',1,'已退订'),(25,'123',36,'1234567890','长春','2018-06-14 05:58:20',2,'已退订'),(26,'999',70,NULL,NULL,'2018-06-14 05:38:33',1,'未购买'),(27,'999',13,NULL,NULL,'2018-06-14 05:38:45',2,'未购买'),(28,'888',70,NULL,NULL,'2018-06-14 05:46:26',1,'未购买'),(29,'888',20,'888','888','2018-06-14 05:51:12',2,'未购买'),(30,'888',23,'888','888','2018-06-14 05:51:54',1,'未购买'),(31,'666',70,'666','666','2018-06-14 06:25:52',2,'未购买'),(32,'666',6,'666','666','2018-06-14 06:26:02',1,'未购买'),(33,'123',12,'1234567890','长春','2018-06-14 06:30:24',2,'未购买');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` double DEFAULT NULL,
  `image_addr` varchar(45) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `category` varchar(15) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  `store_id` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_id` (`store_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'酸奶',6,'images/pic/diwen_04.jpg','酸奶，还是熟悉的味道','饮料',9,'1234'),(2,'农夫山泉',10,'images/banner04.jpg','源深好水，9.9元起','食品',100,'1234'),(3,'咖啡机',499,'images/banner05.jpg','最高满减130元 咖啡机  39元抢超值家电汇','电子',100,'12345'),(4,'婴儿用品',60,'images/banner08.jpg','百货联合，满299减150','生活',99,'123'),(5,'小米耳机',49.9,'images/edivice/baokuan_01.jpg','小米耳机 电力持久 轻便音质好','电子',100,'12345'),(6,'小米鼠标',99.9,'images/edivice/baokuan_02.jpg','小米鼠标，白色，点击灵敏','电子',100,'12345'),(7,'摄像头',100,'images/edivice/baokuan_03.jpg','家用摄像头 高清又安全','电子',98,'12345'),(8,'联想电脑',4999,'images/edivice/diannao_02.jpg','联想电脑 快速启动 大容量8G内存 ','电子',99,'12345'),(9,'小新电脑',5000,'images/edivice/diannao_03.jpg','小新电脑详细信息','电子',98,'12345'),(10,'戴尔电脑',6000,'images/edivice/diannao_04.jpg','戴尔电脑详细信息','电子',98,'12345'),(11,'苹果mac',10000,'images/edivice/diannao_05.jpg','苹果mac详细信息','电子',88,'12345'),(12,'小米笔记本',3999,'images/edivice/diannao_06.jpg','小米笔记本详细信息','电子',88,'12345'),(13,'ipad air',3500,'images/edivice/diannao_07.jpg','ipad air详细信息','电子',88,'12345'),(14,'路由器',200,'images/edivice/diannao_08.jpg','路由器详细信息','电子',88,'12345'),(15,'数码表',422,'images/edivice/shuma_02.jpg','数码表详细信息','电子',88,'12345'),(16,'无人机',2800,'images/edivice/shuma_03.jpg','无人机详细信息','电子',88,'12345'),(17,'耳机',455,'images/edivice/shuma_06.jpg','耳机详细信息','电子',88,'12345'),(18,'充电宝',300,'images/edivice/shuma_07.jpg','充电宝详细信息','电子',150,'12345'),(19,'苹果',30,'images/fruit/guoshu_03.jpg','苹果详细信息','食品',150,'1234'),(20,'猕猴桃',10,'images/fruit/guoshu_04.jpg','猕猴桃详细信息','食品',150,'1234'),(21,'柑橘',22,'images/fruit/guoshu_06.jpg','柑橘详细信息','食品',150,'1234'),(22,'里脊',22,'images/fruit/huiju_04.jpg','里脊详细信息','食品',150,'1234'),(23,'小龙虾',52,'images/fruit/huiju_01.jpg','小龙虾详细信息','食品',150,'1234'),(24,'螃蟹',52,'images/fruit/huiju_06.jpg','螃蟹详细信息','食品',150,'1234'),(25,'鱼肉',35,'images/fruit/huiju_08.jpg','鱼肉详细信息','食品',150,'1234'),(26,'鸡蛋',36,'images/fruit/jidan_01.jpg','鸡蛋详细信息','食品',150,'1234'),(27,'牛肉',33,'images/fruit/niuyang_05.jpg','牛肉详细信息','食品',150,'1234'),(28,'鱿鱼',53,'images/fruit/niuyang_06.jpg','鱿鱼详细信息','食品',150,'1234'),(29,'牛腿肉',58,'images/fruit/niuyang_07.jpg','牛腿肉详细信息','食品',150,'1234'),(30,'猪腿肉',75,'images/fruit/niuyang_08.jpg','猪腿肉详细信息','食品',150,'1234'),(31,'瓶装蓝月亮',6.9,'images/lifeproduct/chuwei_07.jpg','蓝月亮 干净洁白 不伤手','生活',82,'123'),(32,'袋装蓝月亮',12,'images/lifeproduct/chuwei_08.jpg','蓝月亮 干净洁白 不伤手','生活',85,'123'),(33,'洁厕灵',8,'images/lifeproduct/chuwei_jiating_02.jpg','洁厕灵详细信息','生活',85,'123'),(34,'纸巾',10,'images/lifeproduct/chuwei_zhipin_03.jpg','纸巾详细信息','生活',85,'123'),(35,'牙膏',8,'images/lifeproduct/gehu_kouqiang_07.jpg','牙膏详细信息','生活',85,'123'),(36,'老干妈',10,'images/lifeproduct/guochan_chufang_04.jpg','老干妈详细信息','生活',85,'123'),(37,'士力架',35,'images/lifeproduct/guochan_qiaokeli_03.jpg','士力架详细信息','生活',85,'123'),(38,'巧克力',45,'images/lifeproduct/guochan_qiaokeli_05.jpg','巧克力详细信息','生活',85,'123'),(39,'糖果',45,'images/lifeproduct/jinkou_09.jpg','糖果详细信息','生活',85,'123'),(40,'饼干',10,'images/lifeproduct/jinkou_bingan_01.jpg','饼干详细信息','生活',85,'123'),(41,'口红',238,'images/make_up/lipstick.jpg','汤姆福特黑管唇膏，保湿持久','美妆',3,'1234'),(42,'腮红',99,'images/make_up/blush.jpeg','单色腮红，保湿提亮肤色','美妆',12,'1234'),(43,'眼影',99,'images/make_up/eye_shadow.jpg','魅惑眼影盘，非哑光裸妆少女系大地色','美妆',20,'1234'),(44,'精华露',1480,'images/make_up/skii.jpg','sk2护肤精华露，补水保湿提亮肤色','美妆',5,'1234'),(45,'护肤套装',99,'images/make_up/suit.png','复颜抗皱紧致护肤品套装，淡化细纹','美妆',8,'1234'),(46,'面膜',109,'images/make_up/mask.jpg','玻尿酸高保湿弹润面膜，紧致提亮','美妆',36,'1234'),(47,'面霜',255,'images/make_up/cream.jpg','水分缘舒缓凝霜，补水保湿清爽滋润面霜','美妆',4,'1234'),(48,'卸妆膏',95,'images/make_up/cream_cleansing.png','芭妮兰卸妆膏，致柔脸部温和深层卸妆','美妆',12,'1234'),(49,'绷带',15.8,'images/medical/1.jpg','纯棉脱脂纱布卷，医用伤口包扎固定','医疗用品',22,'1234'),(50,'创可贴',6,'images/medical/2.jpg','医用防磨脚创可贴，止血贴','医疗用品',20,'1234'),(51,'急救箱',146,'images/medical/3.jpg','家庭家用铝合金医药箱，收纳便携','医疗用品',9,'1234'),(52,'胶带',9.5,'images/medical/4.jpg','无纺布膏药布，透气胶带','医疗用品',34,'1234'),(53,'消毒片',6.5,'images/medical/5.jpg','一次性酒精消毒棉片，100片','医疗用品',14,'1234'),(54,'医用口罩',17.9,'images/medical/6.jpg','一次性口罩，医用无菌三层外科护理','医疗用品',35,'1234'),(55,'医用手套',36.8,'images/medical/7.jpg','一次性手套，乳胶白丁晴医橡胶食品级','医疗用品',17,'1234'),(56,'连衣裙女',349,'images/clothes/f_dress.jpg','ONLY夏季新款仙女连衣裙女','服饰鞋靴',8,'1234'),(57,'衬衫女',249,'images/clothes/f_shirt.jpg','ONLY2018夏新款蕾丝吊带衬衫两件套女','服饰鞋靴',12,'1234'),(58,'短袖女',99,'images/clothes/f_t.jpg','ONLY夏季新款纯棉印花短袖修身','服饰鞋靴',18,'1234'),(59,'牛仔裤女',599,'images/clothes/f_trousers.jpg','高腰修身弹力小脚牛仔裤女','服饰鞋靴',5,'1234'),(60,'衬衫男',219,'images/clothes/m_t.jpg','夏季修身男士衬衣，商务休闲男装','服饰鞋靴',14,'1234'),(61,'西装裤男',189,'images/clothes/m_trousers.jpg','商务休闲修身型正装裤子，免烫男士西裤','服饰鞋靴',18,'1234'),(62,'鞋',799,'images/clothes/nike.jpg','nike官方，男子跑步鞋','服饰鞋靴',5,'1234'),(63,'仿真玩具枪',193,'images/toys/1.jpg','儿童仿真玩具枪，电动连发m4手动下供弹水弹枪','玩具',4,'1234'),(64,'轨道玩具',188,'images/toys/2.jpg','托马斯小火车套装轨道玩具，和谐号过山车儿童益智玩具','玩具',8,'1234'),(65,'毛绒玩具',168,'images/toys/3.jpg','毛绒玩具兔子抱枕公仔布娃娃，女孩玩偶生日礼物','玩具',13,'1234'),(66,'粘贴玩具',369,'images/toys/4.jpg','神奇水雾魔法珠，儿童手工diy制作','玩具',56,'1234'),(67,'玩具车',569,'images/toys/5.jpg','婴儿儿童电动车可坐遥控汽车','玩具',24,'1234'),(68,'拼图',38,'images/toys/6.jpg','儿童铁盒拼图，益智玩具','玩具',111,'1234'),(69,'机器人',239,'images/toys/7.jpg','凯蒂维特机器人，只能故事机装饰儿歌','玩具',24,'1234'),(70,'电脑',4999,'images/banner01.jpg','32元秒杀 抢面单返现','电子',10,'12345');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `store_id` varchar(15) NOT NULL,
  `name` varchar(45) NOT NULL,
  `income` decimal(14,2) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  `introduction` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`store_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  CONSTRAINT `store_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('123','享受生活',37888.00,'2018-05-31 16:00:00','享受生活，享受生命'),('1234','百货商城',0.00,'2018-06-13 01:38:31','我的百货商城'),('12345','电子科技',0.00,'2018-06-14 01:31:25','各种电子产品，欢迎购买'),('666','666',0.00,'2018-06-14 06:26:48','666');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-14 15:24:25
