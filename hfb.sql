/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3308
 Source Schema         : hfb

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/08/2022 00:14:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user_account
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户code',
  `amount` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0.00' COMMENT '帐户可用余额',
  `freeze_amount` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `t_user_bind`;
CREATE TABLE `t_user_bind`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_id` bigint(0) NOT NULL COMMENT '商户id',
  `agent_user_id` bigint(0) NULL DEFAULT NULL COMMENT '商户的用户id',
  `personal_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `bank_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `bind_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定账户协议号',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'email',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `notify_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'notifyUrl',
  `return_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'returnUrl',
  `timestamp` bigint(0) NULL DEFAULT NULL COMMENT 'timestamp',
  `pay_passwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付密码',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_invest
-- ----------------------------
DROP TABLE IF EXISTS `t_user_invest`;
CREATE TABLE `t_user_invest`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编码',
  `vote_bind_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资人绑定协议号',
  `benefit_bind_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '借款人绑定协议号',
  `agent_project_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编号',
  `agent_project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `agent_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户订单号',
  `vote_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资金额',
  `vote_prize_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资奖励金额',
  `vote_fee_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户手续费',
  `project_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目总金额',
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资备注',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户投资' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_item_return
-- ----------------------------
DROP TABLE IF EXISTS `t_user_item_return`;
CREATE TABLE `t_user_item_return`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_return_id` bigint(0) NOT NULL COMMENT '还款id',
  `agent_project_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款项目编号',
  `vote_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资单号',
  `to_bind_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款人（投资人）',
  `transit_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款金额',
  `base_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款本金',
  `benifit_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款利息',
  `fee_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户手续费',
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户还款明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_return
-- ----------------------------
DROP TABLE IF EXISTS `t_user_return`;
CREATE TABLE `t_user_return`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编码',
  `agent_goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户商品名称',
  `agent_batch_no` varbinary(50) NULL DEFAULT NULL COMMENT '批次号',
  `from_bind_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款人绑定协议号',
  `total_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款总额',
  `vote_fee_amt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户手续费',
  `data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款明细数据',
  `note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户还款表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
