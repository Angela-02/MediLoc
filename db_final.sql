/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - mediloc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mediloc` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `mediloc`;

/*Table structure for table `appointment` */

DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `appointment_id` int(10) NOT NULL AUTO_INCREMENT,
  `doctors_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date` varchar(500) DEFAULT NULL,
  `status` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `appointment` */

insert  into `appointment`(`appointment_id`,`doctors_id`,`user_id`,`description`,`date`,`status`) values 
(1,1,1,'new','2024-10-03','prescription Added'),
(2,1,1,'heart ','2024-10-07','pending'),
(3,2,2,'heart diseases ','2024-10-08','paid'),
(4,1,2,'ENT','2024-10-08','pending');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(10) NOT NULL AUTO_INCREMENT,
  `sender_id` int(10) DEFAULT NULL,
  `complaints` varchar(50) DEFAULT NULL,
  `reply` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`sender_id`,`complaints`,`reply`,`date`) values 
(1,4,'new','oky','2024-10-03'),
(2,10,'new','pending','2024-10-08');

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` int(10) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(10) DEFAULT NULL,
  `department_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `department` */

insert  into `department`(`department_id`,`hospital_id`,`department_name`) values 
(1,1,'Heart'),
(2,2,'Physical therapy'),
(3,1,'ENT'),
(4,1,'Cardiology'),
(5,1,'Neurology'),
(6,1,'General surgery'),
(7,NULL,'General medicine');

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctors_id` int(10) NOT NULL AUTO_INCREMENT,
  `login_id` int(10) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL,
  `hospital_id` int(10) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`doctors_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

insert  into `doctors`(`doctors_id`,`login_id`,`department_id`,`hospital_id`,`first_name`,`last_name`,`place`,`phone`,`email`,`qualification`) values 
(1,7,1,1,'Brian Hoffman','Malcolm Hoover','Aliquip tempor ipsa','+1 (625) 401-1205','huror@mailinator.com','MD,MBBS'),
(2,8,2,2,'Beck Lucas','Chadwick Barnes','Nulla ut ad quidem i','+1 (393) 785-2037','xobezywun@mailinator.com','Occaecat vel et ad e');

/*Table structure for table `enquiry` */

DROP TABLE IF EXISTS `enquiry`;

CREATE TABLE `enquiry` (
  `enquiry_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `hospital_id` int(10) DEFAULT NULL,
  `enquiry` varchar(500) DEFAULT NULL,
  `reply` varchar(504) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`enquiry_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `enquiry` */

insert  into `enquiry`(`enquiry_id`,`user_id`,`hospital_id`,`enquiry`,`reply`,`date`) values 
(1,1,1,'new','ok ','2024-10-03'),
(2,2,1,'hjjjhghjjjhfyffugi','pending','2024-10-08');

/*Table structure for table `hospital` */

DROP TABLE IF EXISTS `hospital`;

CREATE TABLE `hospital` (
  `hospital_id` int(10) NOT NULL AUTO_INCREMENT,
  `login_id` int(10) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `hospital` */

insert  into `hospital`(`hospital_id`,`login_id`,`hospital_name`,`place`,`phone`,`email`,`latitude`,`longitude`) values 
(1,5,'Lucy Lawson','Animi asperiores ex','+1 (899) 731-3504','jivegul@mailinator.com','10.561565850593839','76.16799831390381'),
(2,6,'Hadley Curry','Aut illum nobis con','+1 (359) 798-4979','calafege@mailinator.com','9.988715685028335','76.2879467010498'),
(3,9,'Baxter Dennis','Voluptate eos vero q','+1 (966) 316-1218','zoxeqypyz@mailinator.com','Earum ipsa voluptat','Vel tenetur sint ma');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`user_name`,`password`,`user_type`) values 
(1,'admin','admin','admin'),
(4,'amal','1234','user'),
(5,'hosp','1234','hospital'),
(6,'hosp1','1234','hospital'),
(7,'doct','1234','doctor'),
(8,'Fredericka Williams','Pa$$w0rd!','doctor'),
(9,'Ignatius Wilkerson','Pa$$w0rd!','pending'),
(10,'amal','123456','user');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(10) NOT NULL AUTO_INCREMENT,
  `appointment_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`appointment_id`,`user_id`,`amount`,`status`,`date`) values 
(1,1,1,'250','paid','2024-10-03'),
(2,3,2,'250','paid','2024-10-08');

/*Table structure for table `prescription` */

DROP TABLE IF EXISTS `prescription`;

CREATE TABLE `prescription` (
  `prescription_id` int(10) NOT NULL AUTO_INCREMENT,
  `appointment_id` int(10) DEFAULT NULL,
  `medicine_details` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`prescription_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `prescription` */

insert  into `prescription`(`prescription_id`,`appointment_id`,`medicine_details`,`date`) values 
(1,1,'djsdddc','2024-10-03');

/*Table structure for table `review` */

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `review_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `doctors_id` int(10) DEFAULT NULL,
  `review` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `review` */

insert  into `review`(`review_id`,`user_id`,`doctors_id`,`review`,`date`) values 
(1,1,1,'good','2024-10-03'),
(2,1,1,'good','2024-10-08');

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `schedule_id` int(10) NOT NULL AUTO_INCREMENT,
  `doctors_id` int(10) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `start_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `schedule` */

insert  into `schedule`(`schedule_id`,`doctors_id`,`date`,`start_time`,`end_time`,`status`) values 
(1,1,'2024-10-08','15:30','18:30','AVIABLE'),
(2,2,'2024-10-08','12:00','16:00','AVIABLE'),
(3,1,'2024-10-08','7:00','1:00','AVIABLE');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `login_id` int(10) DEFAULT NULL,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `u_place` varchar(500) DEFAULT NULL,
  `u_phone` varchar(500) DEFAULT NULL,
  `u_email` varchar(500) DEFAULT NULL,
  `u_age` varchar(500) DEFAULT NULL,
  `u_gender` varchar(500) DEFAULT NULL,
  `latitude` varchar(500) DEFAULT NULL,
  `longitude` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`f_name`,`l_name`,`u_place`,`u_phone`,`u_email`,`u_age`,`u_gender`,`latitude`,`longitude`) values 
(1,4,'amal','jj','thrissur','230','amal@gmail.com','78','male','10.5155575','76.2180474'),
(2,10,'new','fjfj','Thrissur','595665959','dnnsnsn@gmail.com','2222','Male','10.5155575','76.2180474');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
