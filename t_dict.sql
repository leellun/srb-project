/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3308
 Source Schema         : srb

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/08/2022 00:14:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '上级id',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` int(0) NULL DEFAULT NULL COMMENT '值',
  `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES (2, 1, '行业', NULL, 'industry', '2022-08-13 20:08:27', NULL, 0);
INSERT INTO `t_dict` VALUES (3, 1, '学历', NULL, 'education', '2022-08-13 20:08:24', NULL, 0);
INSERT INTO `t_dict` VALUES (4, 1, '收入', NULL, 'income', '2022-08-13 20:08:23', NULL, 0);
INSERT INTO `t_dict` VALUES (5, 1, '收入来源', NULL, 'returnSource', '2022-08-13 20:08:22', NULL, 0);
INSERT INTO `t_dict` VALUES (6, 5, '工资', 1, NULL, '2022-08-13 20:08:21', NULL, 0);
INSERT INTO `t_dict` VALUES (7, 5, '股票', 2, NULL, '2022-08-13 20:08:20', NULL, 0);
INSERT INTO `t_dict` VALUES (8, 5, '简直', 3, NULL, '2022-08-13 20:08:19', NULL, 0);
INSERT INTO `t_dict` VALUES (9, 2, '会计', 1, NULL, '2022-08-13 20:08:00', NULL, 0);
INSERT INTO `t_dict` VALUES (10, 2, '计算机', 2, NULL, '2022-08-13 20:08:06', NULL, 0);
INSERT INTO `t_dict` VALUES (11, 2, '律师', 3, NULL, '2022-08-13 20:08:02', NULL, 0);
INSERT INTO `t_dict` VALUES (12, 3, '高中', 1, NULL, '2022-08-13 20:08:10', NULL, 0);
INSERT INTO `t_dict` VALUES (13, 3, '专科', 2, NULL, '2022-08-13 20:08:11', NULL, 0);
INSERT INTO `t_dict` VALUES (14, 3, '本科', 3, NULL, '2022-08-13 20:08:14', NULL, 0);
INSERT INTO `t_dict` VALUES (15, 4, '3000-5000', 1, NULL, '2022-08-13 20:08:15', NULL, 0);
INSERT INTO `t_dict` VALUES (16, 4, '5000-10000', 2, NULL, '2022-08-13 20:08:16', NULL, 0);
INSERT INTO `t_dict` VALUES (17, 4, '10000-20000', 3, NULL, '2022-08-13 20:08:17', NULL, 0);
INSERT INTO `t_dict` VALUES (18, 1, NULL, NULL, 'relation', '2022-08-13 20:20:10', NULL, 0);
INSERT INTO `t_dict` VALUES (19, 18, '父子', 1, NULL, '2022-08-13 20:21:32', NULL, 0);
INSERT INTO `t_dict` VALUES (20, 18, '母子', 2, NULL, '2022-08-13 20:21:34', NULL, 0);
INSERT INTO `t_dict` VALUES (21, 18, '子女', 3, NULL, '2022-08-13 20:21:36', NULL, 0);
INSERT INTO `t_dict` VALUES (22, 1, '还款方式', NULL, 'moneyUse', '2022-08-13 22:21:14', NULL, 0);
INSERT INTO `t_dict` VALUES (23, 1, '资金用途', NULL, 'returnMethod', '2022-08-13 22:23:56', NULL, 0);
INSERT INTO `t_dict` VALUES (24, 22, '等额本息', 1, NULL, '2022-08-13 22:24:13', NULL, 0);
INSERT INTO `t_dict` VALUES (25, 22, '等额本金', 2, NULL, '2022-08-13 22:24:25', NULL, 0);
INSERT INTO `t_dict` VALUES (26, 22, '每月还息一次还本', 3, NULL, '2022-08-13 22:24:31', NULL, 0);
INSERT INTO `t_dict` VALUES (27, 22, '一次还本还息', 4, NULL, '2022-08-13 22:24:43', NULL, 0);
INSERT INTO `t_dict` VALUES (28, 23, '生意周转', 1, NULL, '2022-08-13 22:25:40', NULL, 0);
INSERT INTO `t_dict` VALUES (29, 23, '学费周转', 2, NULL, '2022-08-13 22:26:03', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
