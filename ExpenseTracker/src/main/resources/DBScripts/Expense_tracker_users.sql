DROP TABLE IF EXISTS `expense_tracker_users`;
CREATE TABLE `expense_tracker_users` (
  `USER_ID` int NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) NOT NULL,
  `EMAIL` varchar(80) NOT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`EMAIL`),
  KEY `USER_ID` (`USER_ID`)
);
