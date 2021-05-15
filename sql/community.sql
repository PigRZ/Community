/*
Navicat MySQL Data Transfer

Source Server         : study
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2021-04-23 08:44:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `account` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountindex` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '管理员1', 'admin', '123456');
INSERT INTO `admin` VALUES ('2', '管理员2', 'admin2', '123456');
INSERT INTO `admin` VALUES ('5', '管理员3', 'admin3', '123456');
INSERT INTO `admin` VALUES ('6', '管理员4', 'admin4', '123456');

-- ----------------------------
-- Table structure for adminpost
-- ----------------------------
DROP TABLE IF EXISTS `adminpost`;
CREATE TABLE `adminpost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adminid` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `adminid` (`adminid`),
  CONSTRAINT `adminid` FOREIGN KEY (`adminid`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminpost
-- ----------------------------
INSERT INTO `adminpost` VALUES ('4', '2', '4.2晚上10点停水到4.3早上6点', '请大家及时储水', '2021-04-01 14:10:03');
INSERT INTO `adminpost` VALUES ('6', '1', '小区内遛狗请系狗链', '文明小区，你我共建', '2021-04-21 17:10:44');

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sendType` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `adminid` int(11) DEFAULT NULL,
  `msgType` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid6` (`userid`),
  KEY `admin3` (`adminid`),
  CONSTRAINT `admin3` FOREIGN KEY (`adminid`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userid6` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_chk_1` CHECK ((`sendType` in (_utf8mb3'user',_utf8mb3'admin'))),
  CONSTRAINT `chat_chk_2` CHECK ((`msgType` in (_utf8mb3'text',_utf8mb3'image')))
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES ('5', 'user', '11', null, 'text', '有谁看到我家狗狗吗', null, '2021-04-16 12:53:05');
INSERT INTO `chat` VALUES ('6', 'user', '11', null, 'text', '他走丢了', null, '2021-04-16 12:53:15');
INSERT INTO `chat` VALUES ('7', 'user', '11', null, 'text', '体型大概60cm长，棕色毛发', null, '2021-04-16 12:53:17');
INSERT INTO `chat` VALUES ('8', 'user', '11', null, 'text', '看到的邻居请麻烦联系我一下，万分感谢', null, '2021-04-16 12:53:19');
INSERT INTO `chat` VALUES ('62', 'user', '3', null, 'text', '我看到了，在3号楼下跟李大爷在一块玩', '', '2021-04-16 14:11:50');
INSERT INTO `chat` VALUES ('63', 'admin', null, '1', 'text', '李大爷电话15902004321', '', '2021-04-16 14:14:13');
INSERT INTO `chat` VALUES ('64', 'admin', null, '1', 'text', '3号楼301的别吵了', null, '2021-04-20 17:09:30');
INSERT INTO `chat` VALUES ('65', 'user', '13', null, 'text', '明天早上有没有好兄弟一起健身', null, '2021-04-21 15:51:21');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userpostid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `postid_idx` (`userpostid`) USING BTREE,
  KEY `userid4` (`userid`),
  CONSTRAINT `postid` FOREIGN KEY (`userpostid`) REFERENCES `userpost` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userid4` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('4', '29', '3', '我也觉得', null, '2021-04-21 19:16:48');
INSERT INTO `comment` VALUES ('5', '29', '3', '早上六点吵到现在了', null, '2021-04-21 19:17:13');
INSERT INTO `comment` VALUES ('6', '32', '13', '有兴趣的邻居加我wx：dhdhjd', null, '2021-04-21 19:32:45');
INSERT INTO `comment` VALUES ('7', '32', '3', '好可惜，这周末要加班呜呜呜', null, '2021-04-21 19:33:35');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `imagepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nameindex` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES ('3', '螺丝刀', '/e72ae70777664da5a8981ab3192d6c80.jpg', '工具', '18.60');
INSERT INTO `good` VALUES ('4', '毛巾', '/4d500b4196c24c399e54240d6635521b.jpg', '生活用品', '8.70');
INSERT INTO `good` VALUES ('15', '羊肉', '/4aae26318ef2400dbe7cedbb6ae7168a.png', '食品', '56.00');
INSERT INTO `good` VALUES ('28', '食用盐', '/f744c2c7f7184c0487228b19c77d8662.png', '调味料', '1.50');
INSERT INTO `good` VALUES ('29', '牙刷', '/f0b2c1db1693425bad9152f37bab9f09.jpg', '生活用品', '5.80');

-- ----------------------------
-- Table structure for health
-- ----------------------------
DROP TABLE IF EXISTS `health`;
CREATE TABLE `health` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `temperature` double NOT NULL,
  `isUncomfortable` char(1) NOT NULL,
  `comeFromDangerous` char(1) NOT NULL,
  `detail` varchar(255) DEFAULT '',
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid_idx` (`userid`) USING BTREE,
  CONSTRAINT `userid5` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `b` CHECK ((`isUncomfortable` in (_utf8mb3'是',_utf8mb3'否'))),
  CONSTRAINT `c` CHECK ((`comeFromDangerous` in (_utf8mb3'是',_utf8mb3'否')))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of health
-- ----------------------------
INSERT INTO `health` VALUES ('2', '11', '36.5', '是', '是', '无不适症状', '2021-04-16 05:21:56');
INSERT INTO `health` VALUES ('4', '11', '37.2', '否', '否', '', '2021-04-16 05:31:15');
INSERT INTO `health` VALUES ('6', '11', '36', '否', '否', '', '2021-04-16 06:19:00');
INSERT INTO `health` VALUES ('7', '11', '36', '否', '否', '', '2021-04-16 06:19:04');
INSERT INTO `health` VALUES ('8', '11', '36', '否', '否', '', '2021-04-16 06:19:09');
INSERT INTO `health` VALUES ('9', '11', '36.5', '否', '否', '健康', '2021-04-16 10:55:11');
INSERT INTO `health` VALUES ('10', '11', '38.5', '否', '否', '', '2021-04-16 10:55:28');
INSERT INTO `health` VALUES ('11', '11', '36.5', '否', '否', '', '2021-04-18 22:24:44');
INSERT INTO `health` VALUES ('12', '13', '36.5', '否', '否', '正常', '2021-04-21 16:46:14');
INSERT INTO `health` VALUES ('13', '13', '39.6', '是', '否', '发烧了', '2021-04-21 16:46:44');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `goodid` int(11) NOT NULL,
  `date` date NOT NULL,
  `done` varchar(1) NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `goodid` (`goodid`),
  KEY `userid3` (`userid`),
  CONSTRAINT `goodid` FOREIGN KEY (`goodid`) REFERENCES `good` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userid3` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_chk_1` CHECK ((`done` in (_utf8mb3'是',_utf8mb3'否')))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', '5', '4', '2021-03-11', '否', '3');
INSERT INTO `order` VALUES ('4', '11', '3', '2021-04-08', '是', '1');
INSERT INTO `order` VALUES ('7', '11', '4', '2021-04-09', '是', '2');
INSERT INTO `order` VALUES ('9', '13', '3', '2021-04-21', '否', '2');
INSERT INTO `order` VALUES ('10', '13', '28', '2021-04-21', '否', '1');
INSERT INTO `order` VALUES ('11', '13', '29', '2021-04-21', '否', '1');
INSERT INTO `order` VALUES ('12', '13', '15', '2021-04-21', '否', '3');

-- ----------------------------
-- Table structure for partymember
-- ----------------------------
DROP TABLE IF EXISTS `partymember`;
CREATE TABLE `partymember` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `telephone` varchar(11) NOT NULL,
  `work` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tel` (`telephone`) USING BTREE,
  CONSTRAINT `ck_work` CHECK ((`work` in (_utf8mb3'是',_utf8mb3'否')))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of partymember
-- ----------------------------
INSERT INTO `partymember` VALUES ('1', '王铁柱', '11122223333', '是');
INSERT INTO `partymember` VALUES ('5', '张三', '16542315555', '是');

-- ----------------------------
-- Table structure for repair
-- ----------------------------
DROP TABLE IF EXISTS `repair`;
CREATE TABLE `repair` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `repairitemid` int(11) NOT NULL,
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `date` date NOT NULL,
  `done` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_repairitemid` (`userid`,`repairitemid`) USING BTREE,
  KEY `repairitemid` (`repairitemid`),
  CONSTRAINT `repairitemid` FOREIGN KEY (`repairitemid`) REFERENCES `repairitem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userid2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `donecheck` CHECK ((`done` in (_utf8mb3'是',_utf8mb3'否')))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of repair
-- ----------------------------
INSERT INTO `repair` VALUES ('1', '10', '1', '厕所堵了', '2021-03-10', '是');
INSERT INTO `repair` VALUES ('6', '11', '1', null, '2021-04-12', '否');
INSERT INTO `repair` VALUES ('8', '13', '4', '马桶堵了', '2021-04-21', '否');
INSERT INTO `repair` VALUES ('9', '13', '1', '卧室墙碎了', '2021-04-21', '否');

-- ----------------------------
-- Table structure for repairitem
-- ----------------------------
DROP TABLE IF EXISTS `repairitem`;
CREATE TABLE `repairitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of repairitem
-- ----------------------------
INSERT INTO `repairitem` VALUES ('1', '墙体脱落', '泥');
INSERT INTO `repairitem` VALUES ('4', '马桶堵塞', '水');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `identity` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `telephone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `section` int(11) NOT NULL,
  `cell` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `health` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tel_idx` (`telephone`) USING BTREE,
  UNIQUE KEY `identity_idx` (`identity`) USING BTREE,
  CONSTRAINT `CK_health` CHECK ((`health` in (_utf8mb3'绿',_utf8mb3'红',_utf8mb3''))),
  CONSTRAINT `user_chk_1` CHECK ((`sex` in (_utf8mb3'男',_utf8mb3'女'))),
  CONSTRAINT `user_chk_2` CHECK ((`section` between 1 and 30))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', '张哥', '男', '123321122100005555', '13952118881', '23', '302', '123456', '绿', '785eebc5ce6f4ed59daa5f58f4a630d2.png');
INSERT INTO `user` VALUES ('4', '李小虎', '女', '777654198703246722', '13952118882', '7', '211', '123456', '红', 'ef7f91b8336643728fd5f5a1fe2d18c5.png');
INSERT INTO `user` VALUES ('5', '张牛宝', '女', '818281200101015465', '13952118821', '4', '2112', '123456', '红', 'b66a92ac96284cf3b0c47aedf1a8c187.png');
INSERT INTO `user` VALUES ('10', '李一哥', '男', '435627199607185234', '13952118118', '6', '434', '123456', '', 'f8e3f3ef7dc94b70bf3b725feff7976d.png');
INSERT INTO `user` VALUES ('11', '朱老六', '男', '325672199212072212', '13952121888', '2', '565', '123456', '绿', '50ac5f8463864b68878fec7a112cca4a.png');
INSERT INTO `user` VALUES ('12', '李姐', '女', '124652200207235768', '13952218888', '3', '332', '123456', '', '00db1b557ec64e1297a304cd7823364b.png');
INSERT INTO `user` VALUES ('13', '李二爷', '男', '212121196209191111', '15622223333', '3', '221', '123456', '红', 'be25d0ba81d443d0b8d14af7e1e78904.png');

-- ----------------------------
-- Table structure for userpost
-- ----------------------------
DROP TABLE IF EXISTS `userpost`;
CREATE TABLE `userpost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `userid1` (`userid`),
  CONSTRAINT `userid1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userpost
-- ----------------------------
INSERT INTO `userpost` VALUES ('3', '11', '遛狗能不能栓狗链啊', '你真觉得狗狗不咬人啊，我家孩子很害怕', '2021-04-12 12:24:51', '');
INSERT INTO `userpost` VALUES ('24', '11', '有没有好兄弟一起打球', '每周六下午四点半有空的邻居可以联系我，电话13624567654', '2021-04-17 13:22:03', '');
INSERT INTO `userpost` VALUES ('26', '13', '大家好', '初来匝道，请多指教', '2021-04-20 16:52:17', '18de3bcbca5e4bf39e6622e85c70845b.png');
INSERT INTO `userpost` VALUES ('29', '13', '外面施工好吵啊', '大晚上的吵死啦', '2021-04-21 19:15:59', '7466c5a653d34e1fbf80704d0fad6ad4.png');
INSERT INTO `userpost` VALUES ('32', '13', '有没有教我游泳', '周末有空一块游泳哦', '2021-04-21 19:31:53', '43ee55ec6d57492a968466ebfa6d6a6e.jpg');
