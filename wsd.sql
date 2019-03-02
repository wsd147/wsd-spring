/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : wsd

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-03-02 17:59:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_organization`;
CREATE TABLE `t_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `organizationId` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `delFlag` tinyint(4) DEFAULT NULL,
  `orderNum` tinyint(4) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_organization
-- ----------------------------
INSERT INTO `t_organization` VALUES ('1', '1', '0', '公司一', null, '1', '1', '2019-02-23 13:35:16', null);
INSERT INTO `t_organization` VALUES ('2', '2', '0', '公司二', null, '1', '2', '2019-02-23 13:36:31', null);
INSERT INTO `t_organization` VALUES ('3', '3', '1', '部门一', null, '1', '2', '2019-02-23 13:36:34', null);
INSERT INTO `t_organization` VALUES ('4', '4', '1', '部门二', null, '1', '1', '2019-02-23 13:36:36', null);
INSERT INTO `t_organization` VALUES ('5', '5', '2', '部门三', null, '1', '1', '2019-02-23 13:36:47', null);
INSERT INTO `t_organization` VALUES ('6', '6', '0', 'wsd', '			qwertewq					', '1', '3', '2019-02-03 17:34:57', null);
INSERT INTO `t_organization` VALUES ('7', '7', '0', '32423', '		123						', null, '4', null, null);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `systemId` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(255) DEFAULT NULL,
  `permissionValue` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uri` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `icon` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  `orderNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '123', '0', '系统管理', '0', 'sys', '#', 'fa fa-cogs', '1', '2019-02-26 16:22:45', null, '1');
INSERT INTO `t_permission` VALUES ('2', '123', '1', '用户管理', '1', 'sys:user', 'sys/user/user', 'fa fa-user', '1', '2019-02-26 16:23:44', null, '1');
INSERT INTO `t_permission` VALUES ('3', '123', '1', '角色管理', '1', 'sys:role', 'sys/role/role', 'fa fa-users', '1', '2019-02-26 16:24:41', null, '2');
INSERT INTO `t_permission` VALUES ('4', '123', '2', '添加', '2', 'sys:user:add', 'sys/user/add', 'fa fa-plus', '1', '2019-02-26 16:26:15', '2019-02-26 16:26:37', '1');
INSERT INTO `t_permission` VALUES ('5', '123', '2', '修改', '2', 'sys:user:edit', 'sys/user/edit', 'fa fa-edit', '1', '2019-02-26 16:27:42', '2019-02-26 16:28:15', '2');
INSERT INTO `t_permission` VALUES ('6', '123', '3', '添加', '2', 'sys:role:add', 'sys/role/add', 'fa fa-plus', '1', '2019-02-26 16:28:51', '2019-02-27 09:25:10', '1');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  `orderNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('4', 'admin', '管理员', '最高权限', '1', '2019-02-26 16:14:55', '2019-03-02 15:28:28', '1');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `rolePermissionId` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `permissionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`rolePermissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('11', '4', '1');
INSERT INTO `t_role_permission` VALUES ('12', '4', '2');
INSERT INTO `t_role_permission` VALUES ('13', '4', '4');
INSERT INTO `t_role_permission` VALUES ('14', '4', '5');
INSERT INTO `t_role_permission` VALUES ('15', '4', '3');
INSERT INTO `t_role_permission` VALUES ('16', '4', '6');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `realname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `locked` tinyint(4) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'wsd', 'wsd', 'wsd', 'wsd', '/static/img/a1.jpg', '15016943779', 'wsd@qq.com', '1', '1', '2019-02-23 10:43:27', null);
INSERT INTO `t_user` VALUES ('2', 'sss', 'ss', 'sss', 'sss', '/static/img/a1.jpg', '15016943779', 'wsd@qq.com', '1', '1', '2019-02-23 10:43:27', null);
INSERT INTO `t_user` VALUES ('4', 'wensdia', null, null, '文思达', null, '1566666', '123@11.com', '1', '0', '2019-02-27 15:24:13', null);
INSERT INTO `t_user` VALUES ('5', 'admin', null, null, '文思达', null, '15666', '123@11.com', '1', '1', '2019-03-02 17:09:47', null);

-- ----------------------------
-- Table structure for t_user_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_user_organization`;
CREATE TABLE `t_user_organization` (
  `userOrganizationId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `organizationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userOrganizationId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user_organization
-- ----------------------------
INSERT INTO `t_user_organization` VALUES ('1', '5', null);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `userRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '4', '4');
INSERT INTO `t_user_role` VALUES ('2', '5', '4');
