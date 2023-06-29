CREATE TABLE `xbuilders_chain_config`
(
    `chain_id`             varchar(32)  NOT NULL COMMENT 'baas链id，唯一',
    `chain_name`           varchar(100)          DEFAULT '' NOT NULL comment '链名称',
    `access_key`           varchar(36) COMMENT '链ak',
    `access_secret`        text COMMENT '链sk',
    `tenant`               varchar(20) NULL DEFAULT '' COMMENT '链租户',
    `admin_account_name`   varchar(100) NOT NULL COMMENT '链账户名称',
    `admin_account_kms_id` varchar(100) NOT NULL COMMENT '链账户KMS ID',
    `del_status`           tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除标志【 0-删除 1-正常 】',
    `gmt_create`           datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `chain_browser_url`    varchar(255) NOT NULL DEFAULT '#' COMMENT '区块链浏览器地址',
    PRIMARY KEY (chain_id)
) DEFAULT CHARACTER SET = utf8 COMMENT '开发者社区链配置';

CREATE TABLE `lab_dev_community_asset`
(
    `id`              varchar(36) NOT NULL COMMENT '资产业务id',
    `group_id`        varchar(36)          DEFAULT '' COMMENT '本资产所在资产组id，可为空',
    `chain_id`        varchar(36) NOT NULL DEFAULT '' COMMENT '链id',
    `contract_id`     varchar(64) NOT NULL DEFAULT '' COMMENT '合约id',
    `display_order`   int(20) NOT NULL DEFAULT 1 COMMENT '展示顺序',
    `contract_name`   varchar(64) NOT NULL DEFAULT '' COMMENT '合约名称',
    `exhibition`      varchar(36) NULL DEFAULT '' COMMENT '资产所属展会，可为空',
    `contract_type`   tinyint     NOT NULL DEFAULT 0 COMMENT '资产合约类型【0-ERC721 1-ERC1155 2-DRC721】',
    `sub_id`          varchar(36) COMMENT '资产子ID，仅部分资产合约生效',
    `asset_type`      tinyint     NOT NULL DEFAULT 0 COMMENT '资产类型【0-avatar 1-badge】',
    `name`            varchar(64) NOT NULL DEFAULT '' COMMENT '资产名称',
    `redeemed_amount` int         NOT NULL DEFAULT 0 COMMENT '资产已发放数量',
    `total_amount`    int         NOT NULL DEFAULT 0 COMMENT '资产发放总量，0为无上限',
    `del_status`      tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除标志【 0-删除 1-正常 】',
    `gmt_create`      datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY               `idx_lab_dev_community_asset_group` (group_id)
) DEFAULT CHARACTER SET = utf8 COMMENT '开发者社区资产表';

CREATE TABLE `web3_asset_mint`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `meta_uri`   varchar(128)          DEFAULT NULL COMMENT '铸造信息',
    `to_address` varchar(64)  NOT NULL DEFAULT '' COMMENT '目标地址',
    `tx_hash`    varchar(64)           DEFAULT NULL COMMENT '铸造交易哈希',
    `asset_id`   varchar(36)  NOT NULL COMMENT '业务资产id',
    `order_id`   varchar(128) NOT NULL COMMENT '交易单号',
    `status`     tinyint unsigned NOT NULL DEFAULT 0 COMMENT '状态: 0初始化, 1已发送, 2成功, 3失败',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_asset_mint_order_id` (`order_id`),
    KEY          `uk_meta_uri_to_address` (`meta_uri`,`to_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='web3官网生态服务_AIGC资产铸造'
;