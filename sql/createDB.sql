CREATE
DATABASE taksi_online;

CREATE TABLE `user_info`
(
    `userId`       int NOT NULL AUTO_INCREMENT,
    `login`        varchar(20)  DEFAULT NULL,
    `userPassword` varchar(60)  DEFAULT NULL,
    `userType`     enum('client','administrator') DEFAULT NULL,
    `userEmail`    varchar(320) DEFAULT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE KEY `login` (`login`),
    UNIQUE KEY `userEmail` (`userEmail`)
);

CREATE TABLE `car_category`
(
    `carCategory`         varchar(15) NOT NULL,
    `costPerOneKilometer` decimal(9, 1) DEFAULT NULL,
    `discountPerPrice`    decimal(5, 4) DEFAULT NULL,
    `carCategoryImage`    longblob,
    PRIMARY KEY (`carCategory`),
    CONSTRAINT `chk_discountPercentValue` CHECK ((`discountPerPrice` between 0 and 1))
);
CREATE TABLE `car_info`
(
    `carId`       int NOT NULL,
    `carCategory` varchar(15) DEFAULT NULL,
    `numOfPas`    int         DEFAULT NULL,
    `carState`    enum('ready','on Order','not active') DEFAULT NULL,
    `carName`     varchar(25) DEFAULT NULL,
    `carImage`    longblob,
    PRIMARY KEY (`carId`),
    KEY           `carCategoryFK` (`carCategory`),
    CONSTRAINT `carCategoryFK` FOREIGN KEY (`carCategory`) REFERENCES `car_category` (`carCategory`) ON DELETE CASCADE
);
CREATE TABLE `profile_info`
(
    `userId`               int         DEFAULT NULL,
    `profileId`            int NOT NULL AUTO_INCREMENT,
    `userRegistrationDate` datetime    DEFAULT NULL,
    `userFirstName`        varchar(25) DEFAULT NULL,
    `userSurName`          varchar(25) DEFAULT NULL,
    PRIMARY KEY (`profileId`),
    UNIQUE KEY `userId` (`userId`,`profileId`),
    KEY                    `fkUserId` (`userId`),
    CONSTRAINT `fkUserId` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`)
);
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