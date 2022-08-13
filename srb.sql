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

 Date: 14/08/2022 00:14:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_borrow_info
-- ----------------------------
DROP TABLE IF EXISTS `t_borrow_info`;
CREATE TABLE `t_borrow_info`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '借款人id',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款金额',
  `period` int(0) NULL DEFAULT NULL COMMENT '借款期限',
  `borrow_year_rate` decimal(6, 3) NULL DEFAULT NULL COMMENT '年华利率',
  `return_method` int(0) NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `money_use` int(0) NULL DEFAULT NULL COMMENT '资金用途',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态（0：默认 1：审核通过 -1：审核不通过）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '借款信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_borrower
-- ----------------------------
DROP TABLE IF EXISTS `t_borrower`;
CREATE TABLE `t_borrower`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `sex` tinyint(1) NOT NULL COMMENT '性别(1：男 0：女)',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `education` int(0) NULL DEFAULT NULL COMMENT '学历',
  `is_marry` tinyint(1) NULL DEFAULT NULL COMMENT '是否结婚',
  `industry` int(0) NULL DEFAULT NULL COMMENT '行业',
  `income` int(0) NULL DEFAULT NULL COMMENT '月收入',
  `return_source` int(0) NULL DEFAULT NULL COMMENT '还款来源',
  `contacts_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contacts_mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系手机',
  `contacts_relation` int(0) NULL DEFAULT NULL COMMENT '联系关系',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态（0：认证中 1：认证通过 -1：认证失败）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '借款人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_borrower_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_borrower_attach`;
CREATE TABLE `t_borrower_attach`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `borrower_id` bigint(0) NULL DEFAULT NULL COMMENT '借款人id',
  `image_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片类型（idCard1：身份证正面，idCard2：身份证反面，house：房产证，car：车）',
  `image_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `image_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '借款人上传资源表' ROW_FORMAT = Dynamic;

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
-- Table structure for t_integral_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_grade`;
CREATE TABLE `t_integral_grade`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `integral_start` int(0) NULL DEFAULT NULL COMMENT '积分区间开始',
  `integral_end` int(0) NULL DEFAULT NULL COMMENT '积分区间结束',
  `borrow_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款额度',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '开始时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分等级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lend
-- ----------------------------
DROP TABLE IF EXISTS `t_lend`;
CREATE TABLE `t_lend`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '借款用户id',
  `borrow_info_id` bigint(0) NULL DEFAULT NULL COMMENT '借款信息id',
  `lend_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标的编号',
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '标的金额',
  `period` int(0) NULL DEFAULT NULL COMMENT '投资期数',
  `lend_year_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `service_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '平台服务费',
  `return_method` int(0) NULL DEFAULT NULL COMMENT '还款方式',
  `lowest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '最低投资金额',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资金额',
  `invest_num` int(0) NULL DEFAULT NULL COMMENT '投资人数',
  `publish_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `lend_start_date` date NULL DEFAULT NULL COMMENT '开始时间',
  `lend_end_date` date NULL DEFAULT NULL COMMENT '结束时间',
  `lend_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `expect_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '平台预期收益',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际收益',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `check_admin_id` bigint(0) NULL DEFAULT NULL COMMENT '审核用户id',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '放款时间',
  `payment_admin_id` bigint(0) NULL DEFAULT NULL COMMENT '放款人id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标的准备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lend_item
-- ----------------------------
DROP TABLE IF EXISTS `t_lend_item`;
CREATE TABLE `t_lend_item`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编码',
  `lend_item_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资编号',
  `lend_id` bigint(0) NULL DEFAULT NULL COMMENT '标的id',
  `invest_user_id` bigint(0) NULL DEFAULT NULL COMMENT '投资用户id',
  `invest_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投资人金额',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资金额',
  `lend_year_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '年华利率',
  `invest_time` datetime(0) NULL DEFAULT NULL COMMENT '投资时间',
  `lend_start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `lend_end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `expect_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '预期收益',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际收益',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态（0：默认 1：已支付 2：已还款）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标的出借记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lend_item_return
-- ----------------------------
DROP TABLE IF EXISTS `t_lend_item_return`;
CREATE TABLE `t_lend_item_return`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_return_id` bigint(0) NULL DEFAULT NULL COMMENT '标的还款id',
  `lend_item_id` bigint(0) NULL DEFAULT NULL COMMENT '标的项id',
  `lend_id` bigint(0) NULL DEFAULT NULL COMMENT '标的id',
  `invest_user_id` bigint(0) NULL DEFAULT NULL COMMENT '出借用户id',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '出街金额',
  `real_return_time` datetime(0) NULL DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) NULL DEFAULT NULL COMMENT '是否预期',
  `overdue_total` decimal(10, 2) NULL DEFAULT NULL COMMENT '预期金额',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `lend_year_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `return_method` int(0) NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10, 2) NULL DEFAULT NULL COMMENT '本金',
  `interest` decimal(10, 2) NULL DEFAULT NULL COMMENT '利息',
  `total` decimal(10, 2) NULL DEFAULT NULL COMMENT '本息',
  `fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费',
  `return_date` date NULL DEFAULT NULL COMMENT '还款时指定的还款日期',
  `current_period` int(0) NULL DEFAULT NULL COMMENT '当前的期数',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标的出借回款记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_lend_return
-- ----------------------------
DROP TABLE IF EXISTS `t_lend_return`;
CREATE TABLE `t_lend_return`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_id` bigint(0) NULL DEFAULT NULL COMMENT '标的id',
  `borrow_info_id` bigint(0) NULL DEFAULT NULL COMMENT '借款信息id',
  `return_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '借款人用户id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '还款批次号',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款金额',
  `base_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '计息金额',
  `current_period` int(0) NULL DEFAULT NULL COMMENT '当前的期数',
  `lend_year_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `return_method` int(0) NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10, 2) NULL DEFAULT NULL COMMENT '本金',
  `interest` decimal(10, 2) NULL DEFAULT NULL COMMENT '利息',
  `total` decimal(10, 2) NULL DEFAULT NULL COMMENT '本息',
  `fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费',
  `return_date` date NULL DEFAULT NULL COMMENT '还款时指定的还款日期',
  `real_return_time` datetime(0) NULL DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) NULL DEFAULT NULL COMMENT '是否预期',
  `overdue_total` decimal(10, 2) NULL DEFAULT NULL COMMENT '预期金额',
  `is_last` tinyint(1) NULL DEFAULT NULL COMMENT '是否最后一次还款',
  `status` int(0) NULL DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '还款记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_operate_action
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_action`;
CREATE TABLE `t_operate_action`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `operate_id` bigint(0) NULL DEFAULT NULL COMMENT '操作主键id',
  `operate_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作内容',
  `act_user_id` bigint(0) NULL DEFAULT NULL COMMENT '操作用户id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作行为记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_trans_flow
-- ----------------------------
DROP TABLE IF EXISTS `t_trans_flow`;
CREATE TABLE `t_trans_flow`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `trans_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易单号',
  `trans_type` int(0) NULL DEFAULT NULL COMMENT '交易类型（1：充值 2：提现 3：投标 4：投资回款 ...）',
  `trans_type_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易类型名称',
  `trans_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '交易金额',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_account
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '账户可余额',
  `freeze_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `t_user_bind`;
CREATE TABLE `t_user_bind`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `bank_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_type` tinyint(1) NULL DEFAULT NULL COMMENT '银行类型',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `bind_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定账户协议号',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_type` tinyint(1) NOT NULL COMMENT '1：出借人 2：借款人',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `nick_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `openid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信用户标识openid',
  `head_img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `bind_status` int(0) NOT NULL DEFAULT 0 COMMENT '绑定状态（0：未绑定，1：绑定成功 -1：绑定失败）',
  `borrow_auth_status` int(0) NOT NULL DEFAULT 0 COMMENT '借款人认证状态（0：未认证 1：认证中 2：认证通过 -1：认证失败）',
  `bind_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定账户协议号',
  `integral` int(0) NOT NULL DEFAULT 0 COMMENT '用户积分',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态（0：锁定 1：正常）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_integral
-- ----------------------------
DROP TABLE IF EXISTS `t_user_integral`;
CREATE TABLE `t_user_integral`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `integral` int(0) NULL DEFAULT NULL COMMENT '积分',
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取积分说明',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login_record`;
CREATE TABLE `t_user_login_record`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `ip` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登录记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
