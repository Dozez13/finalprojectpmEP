CREATE database taksi_online;

USE taksi_online;

DROP TABLE IF EXISTS car_category;
DROP TABLE IF EXISTS car_info;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user_order;
DROP TABLE IF EXISTS profile_info;

CREATE TABLE `user_info`
(
    `userId`       int NOT NULL AUTO_INCREMENT,
    `login`        varchar(20)  DEFAULT NULL,
    `userPassword` char(60)  DEFAULT NULL,
    `userType`     enum('client','administrator') DEFAULT NULL,
    `userEmail`    varchar(320) DEFAULT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE KEY `login` (`login`),
    UNIQUE KEY `userEmail` (`userEmail`)
);
--In web app
INSERT INTO `user_info` VALUES (1,'kiberboy','$2a$12$2DOsFzUxt4c.K90q2Zd8Qe.Z66zL/ts1vIPx1f2.Etjey2zUG0u42','administrator','manuilenkopavel@gmail.com'),
(3,'Vectorto','$2a$12$xLVl9x922oWSi9pUTwp2OOuZ/rdBp2CGsmKOoBkQH1OK.DhgasX52','client','manyuile@com.com'),
(8,'МоэИмя','$2a$12$NR8WXsvE/xeWiDIftwxKP.Py6KmXiDETLfEB3fmGzz.W4bXrwt5Ae','client','myemailsomeemail@sd.com'),
(48,'myLoginMy','$2a$12$5n2zWF2c9ATvrTBvm.O5suEQWGvo04LLLbogrgVJOLKrlDZTqwQnO','client','someemailso@msd.com'),
(49,'kiberboykiber','$2a$12$.yOkruoEHHMP5aTW/TKAV.4cuGVZsLFKlRxj6nn/ouwQwaZb74rfK','client','someemail@sd.com'),
(50,'DozezDozez','$2a$12$W9lCq3uxCHwCJTLEBDFT1.Xk6zuEWRpxEdOqTv5PAuwMi/ZqKXmES','client','gasdadsad@qwe.wqe'),
(51,'dozezkiberboy','$2a$12$bpOLz070iyiXj5uZCzsbeekZ2Jn2GWb/sfPSa3pkzojpuV.8/ygna','client','asdasdasd@qwe.com'),
(53,'someSmart','$2a$12$JTIZloiQ1CaWCpjEQAk2outEb08orOxOnY5m41OGFlBsiFbxFQQ/S','client','someSmart@qw.com'),
(54,'smartPerson','$2a$12$VKcE5MTh7f373LHo2kcCJe9j0vU9TxP1e2PiZL6BPPhTgj4F/3BwK','client','asdasd@we.com');
CREATE TABLE `car_category`
(
    `carCategory`         varchar(15) NOT NULL,
    `costPerOneKilometer` decimal(9, 1) DEFAULT NULL,
    `discountPerPrice`    decimal(5, 4) DEFAULT NULL,
    `carCategoryImage`    longblob,
    PRIMARY KEY (`carCategory`),
    CONSTRAINT `chk_discountPercentValue` CHECK ((`discountPerPrice` between 0 and 1))
);
-- Insert CarCategory info
--Instead null value in carCategoryImage column there should be image as byte array
INSERT INTO car_category values('Business',50.0,0.2,null);
INSERT INTO car_category values('Standard',25.0,0.1,null);
INSERT INTO car_category values('Premium',35.0,0.15,null);

--Creating car_info table that will contain information about car
CREATE TABLE `car_info`
(
    `carId`       int NOT NULL,
    `carCategory` varchar(15) DEFAULT NULL,
    `numOfPas`    int    DEFAULT NULL,
    `carState`    enum('ready','on Order','not active') DEFAULT NULL,
    `carName`     varchar(25) DEFAULT NULL,
    `carImage`    longblob,
    PRIMARY KEY (`carId`),
    KEY `carCategoryFK` (`carCategory`),
    CONSTRAINT `carCategoryFK` FOREIGN KEY (`carCategory`) REFERENCES `car_category` (`carCategory`) ON DELETE CASCADE
);

-- inserting values into table carCategory should contain only values that exist in car_category table
--Instead null value in carImage column there should be image as byte array
INSERT INTO car_info values(3,'Business',5,'ready','BMW',null);
INSERT INTO car_info values(4,'Standard',7,'ready','Nissan',null);
INSERT INTO car_info values(35,'Premium',7,'ready','Honda',null);
INSERT INTO car_info values(53,'Business',5,'ready','Infinity',null);
INSERT INTO car_info values(78,'Standard',4,'ready','Toyota',null);
INSERT INTO car_info values(79,'Premium',3,'ready','Mercedes',null);
INSERT INTO car_info values(85,'Business',6,'ready','Porsche',null);
INSERT INTO car_info values(34,'Standard',5,'ready','Acura',null);
INSERT INTO car_info values(115,'Premium',8,'ready','Ferrari',null);

CREATE TABLE `profile_info`
(
    `userId`               int         DEFAULT NULL,
    `profileId`            int NOT NULL AUTO_INCREMENT,
    `userRegistrationDate` datetime    DEFAULT NULL,
    `userFirstName`        varchar(25) DEFAULT NULL,
    `userSurName`          varchar(25) DEFAULT NULL,
    `accountBalance`       double      DEFAULT '0',
    PRIMARY KEY (`profileId`),
    UNIQUE KEY `userId` (`userId`,`profileId`),
    KEY                    `fkUserId` (`userId`),
    CONSTRAINT `fkUserId` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`),
    CONSTRAINT `chk_accountBalance` CHECK ((`accountBalance` >= 0))
);

INSERT INTO `profile_info`(userId,userRegistrationDate, userFirstName, userSurName, accountBalance) VALUES (48,'2021-06-08 18:29:55','Павел','Мануйленко',0),
(49,'2021-06-08 21:51:24','Иваныв','Мануйленко',0),
(50,'2021-06-08 23:17:11','Имойе','йцуйцуйй',0),
(51,'2021-06-09 00:09:28','Павел','Мануйленко',0),
(53,'2021-06-10 21:41:43','Павел','Мануйленко',7593.0999999999985),
(54,'2021-06-14 21:02:45','Павелыв','Мануйленко',0);

CREATE TABLE `user_order`
(
    `orderId`         int NOT NULL AUTO_INCREMENT,
    `userId`          int            DEFAULT NULL,
    `carId`           int            DEFAULT NULL,
    `userAddress`     varchar(60)    DEFAULT NULL,
    `userDestination` varchar(60)    DEFAULT NULL,
    `orderCost`       decimal(10, 2) DEFAULT NULL,
    `orderDate`       datetime       DEFAULT NULL,
    UNIQUE KEY `orderId` (`orderId`),
    UNIQUE KEY `UNQ_carUserId` (`userId`,`carId`),
    KEY               `orderUserId` (`userId`),
    KEY               `orderCarId` (`carId`),
    CONSTRAINT `orderCarId` FOREIGN KEY (`carId`) REFERENCES `car_info` (`carId`) ON DELETE CASCADE,
    CONSTRAINT `orderUserId` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE
);
INSERT INTO `user_order`(userId, carId, userAddress, userDestination, orderCost, orderDate) VALUES (1,4,'Україна, Дніпро, Кулагіна вулиця, 33','Україна, Дніпро, Кулагіна вулиця, 33',0.00,'2021-06-07 18:52:50'),
(1,3,'Україна, Дніпро, Кулагіна вулиця, 33','Україна, Дніпро, Кулагіна вулиця, 33',15.00,'2021-06-07 18:52:50'),
(53,3,'Україна, Дніпро, Кулагіна вулиця, 33','Україна, Київ, Главная улица, 46',17406.90,'2021-06-11 00:48:15');

--Test database
SELECT*FROM user_info;
SELECT*FROM car_info;
SELECT*FROM car_category;
SELECT*FROM profile_info;
SELECT*FROM user_order;