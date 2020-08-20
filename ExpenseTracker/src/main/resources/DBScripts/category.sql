CREATE TABLE `category` (
  `NAME` varchar(50) NOT NULL,
  `BUDGET` decimal(6,2) DEFAULT NULL,
  `EMAIL` varchar(80) NOT NULL,
  PRIMARY KEY (`NAME`,`EMAIL`)
)