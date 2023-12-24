-- ----------------------------
-- Table：分类表（课程分类、资源分类）
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`(
    `id`                bigint NOT NULL COMMENT '主键',
    `parent_id`         bigint NOT NULL DEFAULT '0' COMMENT '父分类ID,0表示顶级分类',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `category_type`     tinyint NOT NULL DEFAULT '1' COMMENT '类型(1:课程，2:资源)',
    `category_name`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
    `remark`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='分类';

-- ----------------------------
-- Table：课程信息表
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
    `id` bigint NOT NULL COMMENT '主键',
    `lecturer_id`       bigint NOT NULL DEFAULT '0' COMMENT '讲师ID',
    `category_id`       bigint DEFAULT '0' COMMENT '分类ID',
    `course_name`       varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '课程名称',
    `course_logo`       varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '课程封面',
    `introduce`         longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '课程简介',
    `is_free`           tinyint NOT NULL DEFAULT '0' COMMENT '是否免费(1:免费，0:收费)',
    `ruling_price`      decimal(11,2) DEFAULT NULL COMMENT '划线价',
    `course_price`      decimal(11,2) DEFAULT NULL COMMENT '课程价格',
    `is_upload`         tinyint NOT NULL DEFAULT '1' COMMENT '是否上架(1:上架，0:下架)',
    `course_sort`       int NOT NULL DEFAULT '0' COMMENT '课程排序(前端显示使用)',
    `count_buy`         int NOT NULL DEFAULT '0' COMMENT '购买人数',
    `count_study`       int NOT NULL DEFAULT '0' COMMENT '学习人数',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程信息';

-- ----------------------------
-- Table：章节信息表
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter` (
    `id`                bigint NOT NULL COMMENT '主键',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `chapter_name`      varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '章节名称',
    `chapter_desc`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '章节描述',
    `is_free`           tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否免费(1免费，0收费)',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='章节信息';

-- ----------------------------
-- Table：课时信息表
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter_period`;
CREATE TABLE `course_chapter_period` (
     `id`               bigint NOT NULL COMMENT '主键',
     `course_id`        bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
     `chapter_id`       bigint NOT NULL DEFAULT '0' COMMENT '章节ID',
     `period_name`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '课时名称',
     `period_desc`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '课时描述',
     `is_free`          tinyint NOT NULL DEFAULT '0' COMMENT '是否免费(1免费，0收费)',
     `resource_id`      bigint DEFAULT '0' COMMENT '资源ID',
     `video_url`        varchar(255) DEFAULT NULL COMMENT '视频url',
     `video_length`     int DEFAULT NULL COMMENT '视频时长',
     `video_name`       varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '视频名称',
     `doc_file_name`     varchar(255) DEFAULT NULL COMMENT '课件名称',
     `doc_file_url`      varchar(255) DEFAULT NULL COMMENT '课件地址',
     `status`           tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
     `sort`             int NOT NULL DEFAULT '1' COMMENT '排序',
     `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课时信息';

-- ----------------------------
-- Table：课程视频信息表(资源表)
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
    `id`                bigint NOT NULL COMMENT '主键',
    `resource_name`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '资源名称',
    `resource_type`     tinyint NOT NULL DEFAULT '1' COMMENT '资源类型(1:视频, 2:音频, 3:文档)',
    `resource_size`     bigint NOT NULL COMMENT '资源大小',
    `resource_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源地址',
    `video_status`      tinyint DEFAULT '1' COMMENT '状态(1:转码中, 2:成功, 3:失败)',
    `video_length`      varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '时长',
    `video_vid`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'vid',
    `vod_platform`      int DEFAULT '0',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程视频信息';

-- ----------------------------
-- Table：课程收藏表
-- ----------------------------
DROP TABLE IF EXISTS `user_course_collect`;
CREATE TABLE `user_course_collect` (
    `id`                bigint NOT NULL COMMENT '主键',
    `user_id`           bigint NOT NULL COMMENT '用户ID',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程收藏';

-- ----------------------------
-- Table：课程评论表
-- ----------------------------
DROP TABLE IF EXISTS `user_course_comment`;
CREATE TABLE `user_course_comment` (
    `id`                bigint NOT NULL COMMENT '主键',
    `user_id`           bigint NOT NULL COMMENT '用户ID',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `comment_id`        bigint unsigned NOT NULL DEFAULT '0' COMMENT '评论ID',
    `comment_text`      text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评论内容',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程评论';

-- ----------------------------
-- Table：用户信息
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
     `id`               bigint NOT NULL COMMENT '主键',
     `mobile`           varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号码',
     `mobile_salt`      varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码盐',
     `mobile_psw`       varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '登录密码',
     `nickname`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '昵称',
     `user_sex`         tinyint unsigned DEFAULT '3' COMMENT '用户性别(1:男, 2:女, 3:保密)',
     `user_age`         int DEFAULT NULL COMMENT '用户年龄',
     `user_head`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户头像',
     `remark`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
     `status`           tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
     `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户信息';

-- ----------------------------
-- Table：讲师信息表
-- ----------------------------
DROP TABLE IF EXISTS `lecturer`;
CREATE TABLE `lecturer` (
    `id`                bigint NOT NULL COMMENT '主键',
    `lecturer_name`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '讲师名称',
    `lecturer_mobile`   char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '讲师手机',
    `lecturer_position` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '讲师职位',
    `lecturer_head`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '讲师头像',
    `introduce`         longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '简介',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='讲师信息';

-- ----------------------------
-- Table：后台用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `mobile`            char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机',
    `mobile_salt`       varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码盐',
    `mobile_psw`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '登录密码',
    `real_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
    `sort`              int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `AK_phone` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1640881133980762114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='后台用户信息';

-- ----------------------------
-- Table：角色信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息';

-- ----------------------------
-- Table：角色用户关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
     `id`               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
     `role_id`          bigint unsigned NOT NULL COMMENT '角色ID',
     `user_id`          bigint unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
     `status`           tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
     `sort`             int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
     `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1682410794588880899 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色用户关联表';

-- ----------------------------
-- Table：菜单信息表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`         bigint unsigned NOT NULL COMMENT '父ID',
    `menu_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `menu_url`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由路径',
    `auth_value`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '权限值',
    `menu_icon`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `menu_type`         tinyint NOT NULL DEFAULT '1' COMMENT '菜单类型(1:目录, 2:菜单, 3:权限)',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1715623541707153411 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单信息';

-- ----------------------------
-- Table：菜单角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
     `id`               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
     `menu_id`          bigint unsigned NOT NULL COMMENT '菜单ID',
     `role_id`          bigint unsigned NOT NULL COMMENT '角色ID',
     `status`           tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常，0:禁用)',
     `sort`             int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
     `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1715623652164149251 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单角色关联表';

-- ----------------------------
-- Table：课程用户关联表
-- ----------------------------
DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course` (
    `id`                bigint NOT NULL DEFAULT '2' COMMENT '主键',
    `user_id`           bigint NOT NULL COMMENT '用户ID',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `buy_type`          tinyint DEFAULT '1' COMMENT '购买类型(1:支付, 2:免费)',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_course_id_user_no` (`course_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程用户关联表';

-- ----------------------------
-- Table：订单信息表
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
    `id`                bigint NOT NULL COMMENT '主键',
    `order_no`          bigint NOT NULL COMMENT '订单号',
    `user_id`           bigint NOT NULL COMMENT '下单用户编号',
    `mobile`            varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '下单用户电话',
    `register_time`     datetime DEFAULT NULL COMMENT '下单用户注册时间',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `ruling_price`      decimal(11,2) DEFAULT NULL COMMENT '划线价',
    `course_price`      decimal(11,2) DEFAULT NULL COMMENT '课程价格',
    `pay_type`          tinyint unsigned NOT NULL DEFAULT '0' COMMENT '支付方式(1:微信支付, 2:支付宝支付)',
    `order_status`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '订单状态(1:待支付, 2:成功支付, 3:支付失败, 4:关闭支付)',
    `remark_cus`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户备注',
    `remark`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '后台备注',
    `pay_time`          datetime DEFAULT NULL COMMENT '支付时间',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='订单信息表';

-- ----------------------------
-- Table：订单支付信息表
-- ----------------------------
DROP TABLE IF EXISTS `order_pay`;
CREATE TABLE `order_pay` (
    `id`                bigint NOT NULL COMMENT '主键',
    `order_no`          bigint NOT NULL COMMENT '订单号',
    `serial_number`     bigint NOT NULL DEFAULT '0' COMMENT '流水号',
    `ruling_price`      decimal(11,2) DEFAULT NULL COMMENT '划线价',
    `course_price`      decimal(11,2) DEFAULT NULL COMMENT '课程价格',
    `pay_type`          tinyint unsigned NOT NULL DEFAULT '0' COMMENT '支付方式(1:微信支付, 2:支付宝支付, 3:积分支付, 4:手工录单)',
    `order_status`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '订单状态(1:待支付, 2:成功支付, 3:支付失败, 4:已关闭, 5:已退款, 6:订单解绑)',
    `remark_cus`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户备注',
    `pay_time`          datetime DEFAULT NULL COMMENT '支付时间',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='订单支付信息表';

-- ----------------------------
-- Table：专区表
-- ----------------------------
DROP TABLE IF EXISTS `zone`;
CREATE TABLE `zone` (
    `id`                bigint NOT NULL COMMENT '主键',
    `zone_name`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '名称',
    `zone_desc`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '描述',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='专区';

-- ----------------------------
-- Table：专区课程关联表
-- ----------------------------
DROP TABLE IF EXISTS `zone_course`;
CREATE TABLE `zone_course` (
    `id`                bigint NOT NULL COMMENT '主键',
    `zone_id`           bigint NOT NULL DEFAULT '0' COMMENT '专区编号',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int NOT NULL COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='专区课程关联表';

-- ----------------------------
-- Table：专区讲师关联表
-- ----------------------------
DROP TABLE IF EXISTS `zone_teacher`;
CREATE TABLE `zone_teacher` (
    `id`                bigint NOT NULL COMMENT '主键',
    `teacher_id`        bigint DEFAULT '0' COMMENT '讲师ID',
    `remark`            varchar(200) DEFAULT '0' COMMENT '备注',
    `status`            tinyint DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int DEFAULT NULL COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='专区讲师关联表';

-- ----------------------------
-- Table：系统配置表
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
    `id`                bigint NOT NULL COMMENT '主键ID',
    `config_type`        tinyint NOT NULL DEFAULT '1' COMMENT '配置类型(1:站点信息, 2:系统信息, 3:其他)',
    `content_type`      tinyint NOT NULL DEFAULT '1' COMMENT '内容类型(1:文本, 2:富文本, 3:图片, 4:布尔, 5:枚举)',
    `config_name`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '参数名称',
    `config_key`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '参数键名',
    `config_value`       text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '参数键值',
    `config_show`        bit(1) NOT NULL DEFAULT b'1' COMMENT '配置展示(0:隐藏, 1:显示)',
    `remark`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
    `sort`              int NOT NULL DEFAULT '100' COMMENT '排序，默认值:100',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `ak_configkey` (`config_key`) USING BTREE COMMENT '参数键名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Table：行政区域表
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
    `id`                bigint unsigned NOT NULL COMMENT '主键',
    `parent_id`         bigint unsigned NOT NULL DEFAULT '0' COMMENT '父id',
    `level`             int unsigned NOT NULL DEFAULT '0' COMMENT '级别',
    `province_code`     varchar(9) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '区域编码（国标）',
    `center_lng`        decimal(12,9) NOT NULL COMMENT '中心经度',
    `center_lat`        decimal(12,9) NOT NULL COMMENT '中心维度',
    `province_id`       int NOT NULL COMMENT '省Id',
    `province_name`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '省名称',
    `city_id`           int NOT NULL COMMENT '市Id',
    `city_code`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '城市编码',
    `city_name`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '市名称',
    `region_name`       varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '区域名称',
    `district_name`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '区名称',
    `merger_name`       varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '全路径名称',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='行政区域表';

-- ----------------------------
-- Table：头部导航表
-- ----------------------------
DROP TABLE IF EXISTS `website_nav`;
CREATE TABLE `website_nav` (
    `id`                bigint NOT NULL DEFAULT '0' COMMENT '主键',
    `nav_title`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '导航标题',
    `nav_url`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '导航url',
    `target`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '跳转方式',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1有效, 0无效)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='头部导航';

-- ----------------------------
-- Table：广告信息表
-- ----------------------------
DROP TABLE IF EXISTS `website_carousel`;
CREATE TABLE `website_carousel` (
    `id`                bigint unsigned NOT NULL COMMENT '主键',
    `carousel_title`    varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '广告标题',
    `carousel_img`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '广告图片',
    `carousel_url`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '广告链接',
    `carousel_target`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '广告跳转方式',
    `begin_time`        datetime DEFAULT NULL COMMENT '开始时间',
    `end_time`          datetime DEFAULT NULL COMMENT '结束时间',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:正常, 0:禁用)',
    `sort`              int unsigned NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='广告信息';

-- ----------------------------
-- Table：站点友情链接表
-- ----------------------------
DROP TABLE IF EXISTS `website_link`;
CREATE TABLE `website_link` (
    `id`                bigint NOT NULL COMMENT '主键',
    `link_name`         varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
    `link_url`          varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '链接',
    `link_target`       varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '跳转方式(_blank，_self)',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:有效, 0:无效)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='站点友情链接';

-- ----------------------------
-- Table：站内信息表
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
    `id`                bigint NOT NULL COMMENT '主键',
    `msg_type`          tinyint NOT NULL DEFAULT '1' COMMENT '短信类型(1:系统消息, 2:其他)',
    `msg_title`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '短信标题',
    `msg_text`          text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '短信内容',
    `is_time_send`      tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否定时发送（1:是, 0:否）',
    `send_time`         datetime DEFAULT NULL COMMENT '发送时间',
    `is_send`           tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否已发送(1:是, 0:否)',
    `is_top`            tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否置顶(1:是, 0:否)',
    `remark`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
    `status_id`         tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:有效, 0:无效)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='站内信息表';

-- ----------------------------
-- Table：用户登录日志表
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
     `id`               bigint NOT NULL COMMENT '主键',
     `user_id`          bigint NOT NULL COMMENT '用户ID',
     `login_status`     tinyint NOT NULL DEFAULT '1' COMMENT '登录状态(0:失败，1:成功，2:注册)',
     `login_ip`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '登录IP',
     `country`          varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '国家',
     `province`         varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '省',
     `city`             varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '市',
     `browser`          varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '浏览器',
     `os`               varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '操作系统',
     `create_time`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户登录日志';

-- ----------------------------
-- Table：后台操作日志表
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
    `id`                bigint unsigned NOT NULL COMMENT '主键',
    `user_id`           bigint unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID',
    `real_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
    `ip`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'IP地址',
    `operation`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户操作',
    `method`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方法',
    `path`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求路径',
    `content`           varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='后台操作日志表';

-- ----------------------------
-- Table：课程用户学习日志表
-- ----------------------------
DROP TABLE IF EXISTS `user_study`;
CREATE TABLE `user_study` (
    `id`                bigint NOT NULL DEFAULT '0' COMMENT '主键',
    `course_id`         bigint NOT NULL DEFAULT '0' COMMENT '课程ID',
    `chapter_id`        bigint NOT NULL DEFAULT '0' COMMENT '章节ID',
    `period_id`         bigint NOT NULL DEFAULT '0' COMMENT '课时ID',
    `user_id`           bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
    `progress`          decimal(5,2) NOT NULL DEFAULT '20.00' COMMENT '进度(百分比)',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_peroid` (`period_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='课程用户学习日志';

-- ----------------------------
-- Table：站内信用户记录表
-- ----------------------------
DROP TABLE IF EXISTS `msg_user`;
CREATE TABLE `msg_user` (
    `id`                bigint NOT NULL COMMENT '主键',
    `msg_id`            bigint NOT NULL COMMENT '短信ID',
    `user_id`           bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
    `mobile`            char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
    `msg_type`          tinyint NOT NULL DEFAULT '1' COMMENT '短信类型(1:系统消息, 2:其他)',
    `msg_title`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '短信标题',
    `is_read`           tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否阅读(1:是, 0:否)',
    `is_top`            tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否置顶(1:是, 0:否)',
    `status`            tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态(1:有效, 0:无效)',
    `sort`              int NOT NULL DEFAULT '1' COMMENT '排序',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='站内信用户记录表';

-- ----------------------------
-- Table：xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group` (
     `id`               int NOT NULL AUTO_INCREMENT,
     `app_name`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器AppName',
     `title`            varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器名称',
     `address_type`     tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
     `address_list`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '执行器地址列表，多地址逗号分隔',
     `update_time`      datetime DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info` (
    `id`                            int NOT NULL AUTO_INCREMENT,
    `job_group`                     int NOT NULL COMMENT '执行器主键ID',
    `job_desc`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `add_time`                      datetime DEFAULT NULL,
    `update_time`                   datetime DEFAULT NULL,
    `author`                        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '作者',
    `alarm_email`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '报警邮件',
    `schedule_type`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
    `schedule_conf`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
    `misfire_strategy`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
    `executor_route_strategy`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器路由策略',
    `executor_handler`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`                varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器任务参数',
    `executor_block_strategy`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '阻塞处理策略',
    `executor_timeout`              int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
    `executor_fail_retry_count`     int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `glue_type`                     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE类型',
    `glue_source`                   mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'GLUE源代码',
    `glue_remark`                   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'GLUE备注',
    `glue_updatetime`               datetime DEFAULT NULL COMMENT 'GLUE更新时间',
    `child_jobid`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
    `trigger_status`                tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
    `trigger_last_time`             bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
    `trigger_next_time`             bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock` (
    `lock_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁名称',
    PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log` (
    `id`                            bigint NOT NULL AUTO_INCREMENT,
    `job_group`                     int NOT NULL COMMENT '执行器主键ID',
    `job_id`                        int NOT NULL COMMENT '任务，主键ID',
    `executor_address`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
    `executor_handler`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`                varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器任务参数',
    `executor_sharding_param`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
    `executor_fail_retry_count`     int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `trigger_time`                  datetime DEFAULT NULL COMMENT '调度-时间',
    `trigger_code`                  int NOT NULL COMMENT '调度-结果',
    `trigger_msg`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '调度-日志',
    `handle_time`                   datetime DEFAULT NULL COMMENT '执行-时间',
    `handle_code`                   int NOT NULL COMMENT '执行-状态',
    `handle_msg`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '执行-日志',
    `alarm_status`                  tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `I_trigger_time` (`trigger_time`) USING BTREE,
    KEY `I_handle_code` (`handle_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=558145 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report` (
    `id`            int NOT NULL AUTO_INCREMENT,
    `trigger_day`   datetime DEFAULT NULL COMMENT '调度-时间',
    `running_count` int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
    `suc_count`     int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
    `fail_count`    int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
    `update_time`   datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue` (
    `id`            int NOT NULL AUTO_INCREMENT,
    `job_id`        int NOT NULL COMMENT '任务，主键ID',
    `glue_type`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'GLUE类型',
    `glue_source`   mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'GLUE源代码',
    `glue_remark`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE备注',
    `add_time`      datetime DEFAULT NULL,
    `update_time`   datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry` (
    `id`                int NOT NULL AUTO_INCREMENT,
    `registry_group`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `registry_key`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `registry_value`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `update_time`       datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table：xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user` (
    `id`            int NOT NULL AUTO_INCREMENT,
    `username`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
    `password`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `role`          tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
    `permission`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
