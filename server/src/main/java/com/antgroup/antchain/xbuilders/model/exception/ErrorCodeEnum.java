package com.antgroup.antchain.xbuilders.model.exception;

/**
 * 错误码
 */
public enum ErrorCodeEnum {

    /**
     * 成功默认
     */
    SUCCESS("00000", "操作成功"),

    // 用户异常
    /**
     * 请求参数异常
     */
    PARAMS_FAIL("10001", "P5", "参数异常"),
    /**
     * 数据重复
     */
    DATA_REPEAT("10002", "P5", "数据重复"),
    /**
     * 需要更新的数据不存在
     */
    UPDATE_DATA_NOT_FOUND("10003", "P5", "需要更新的数据不存在"),
    /**
     * 查询的数据不存在
     */
    SELECT_DATA_NOT_FOUND("10004", "P5", "查询的数据不存在"),
    /**
     * 内容校验不通过
     */
    CONTENT_CHECK_FAIL("10005", "P5", "内容校验不通过"),
    /**
     * 服务不存在
     */
    SERVICE_NOT_FOUND("10006", "P5", "服务不存在"),
    /**
     * 命名空间重复
     */
    NAMESPACE_REPEAT("10007", "P5", "命名空间重复"),
    /**
     * 命名空间不存在
     */
    NAMESPACE_NOT_FOUND("10008", "P5", "命名空间不存在"),
    /**
     * 使用方记录不存在
     */
    CONSUMER_NOT_FOUND("10009", "P5", "使用方记录不存在"),
    /**
     * 重复审核
     */
    AUDIT_REPEAT("10010", "P5", "请勿重复审核"),
    /**
     * 重复加入
     */
    JOIN_REPEAT("10011", "P5", "请勿重复加入服务"),
    /**
     * 授权码不存在
     */
    AUTH_CODE_NOT_FOUND("10012", "P5", "授权码不存在"),
    /**
     * 合约不存在
     */
    CONTRACT_NOT_FOUND("10013", "P5", "合约不存在"),
    /**
     * 未登录
     */
    UNAUTHORIZED_ERROR("10014", "P5", "请重新登录金融云"),
    /**
     * 未入驻平台
     */
    NOT_OPEN_SERVER_ERROR("10015", "P5", "请先入驻开放平台"),
    /**
     * 重复添加链
     */
    ADD_BLOCKCHAIN_REPEAT("10016", "P5", "请勿重复新增链/节点"),
    /**
     * 开放联盟链无节点
     */
    OPEN_CHAIN_NOT_NODE("10017", "P5", "开放联盟链无节点"),
    /**
     * 区块链未找到
     */
    BLOCKCHAIN_NOT_FOUND("10018", "P5", "区块链未找到"),
    /**
     * 合约重复添加
     */
    CONTRACT_REPEAT("10019", "P5", "请勿重复添加合约"),
    /**
     * 服务开发组件不存在
     */
    SERVICE_SDK_NOT_FOUND("10020", "P5", "服务开发组件不存在"),

    // 业务异常
    /**
     * 系统异常
     */
    SYS_FAIL("20001", "P1", "系统异常"),
    /**
     * 无权限
     */
    PERMISSION_DENIED("20002", "P4", "无权限"),
    /**
     * 合约不存在
     */
    CONTRACT_NOT_EXISTED("20003", "P5", "合约不存在"),
    /**
     * 合约非用户所有
     */
    CONTRACT_NOT_OWNED("20004", "P5", "合约非用户所有"),
    /**
     * 合约状态异常
     */
    CONTRACT_STATUS_ERROR("20005", "P5", "合约状态异常"),
    /**
     * 服务没有活动的合约方法
     */
    SERVICE_NO_ACTIVE_CONTRACT("20006", "P5", "服务没有活跃的合约方法"),
    /**
     * 服务状态异常
     */
    SERVICE_STATUS_ERROR("20007", "P5", "服务状态异常"),
    /**
     * 合约方法只能添加至一个服务中
     */
    CONTRACT_BIND_REPEAT("20008", "P5", "合约方法只能添加至一个服务中"),

    // 三方系统异常
    /**
     * 系统调用异常(超时或无服务)
     */
    RPC_ERROR("30001", "P3", "系统调用异常"),

    /**
     * 区块链注册失败
     */
    BLOCKCHAIN_REGISTER_ERROR("30002", "P2", "区块链注册失败"),

    /**
     * 合约方法检查失败
     */
    CONTRACT_QUERY_ERROR("30003", "P2", "合约方法检查失败"),

    /**
     * 服务注册失败
     */
    SERVICE_REGISTER_ERROR("30004", "P2", "服务注册失败"),

    /**
     * 合约方法绑定失败
     */
    CONTRACT_BIND_ERROR("30005", "P2", "合约方法绑定失败"),

    /**
     * 服务加入失败
     */
    SERVICE_JOIN_ERROR("30006", "P2", "服务加入失败"),

    /**
     * 查询租户信息失败
     */
    QUERY_TENANT_ERROR("30007", "P2", "查询租户信息失败"),

    /**
     * 租户登录验证失败
     */
    VERIFY_TENANT_ERROR("30008", "P2", "校验登录信息失败"),

    /**
     * 查询租户机构名称失败
     */
    GET_TENANT_CUSTOMER_ERROR("30009", "P3", "请稍后重试"),

    /**
     * 邮件发送失败
     */
    SEND_MAIL_ERROR("30010", "P3", "请稍后重试"),

    /**
     * 获取服务日志信息失败
     */
    GET_SERVICE_LOG_ERROR("30010", "P3", "获取服务日志信息失败"),

    /**
     * 获取服务统计信息失败
     */
    GET_SERVICE_STATISTICS_ERROR("30010", "P3", "获取服务统计信息失败"),

    /**
     * 速搭创建用户失败
     */
    BSP_CREATE_DEFAULT_USER_ERROR("30011", "P3", "速搭创建用户失败"),

    /**
     * 速搭应用创建失败
     */
    BSP_APPLICATION_CREATE_ERROR("30012", "P3", "速搭应用创建失败"),

    /**
     * 获取区块链账户失败
     */
    GET_BLOCKCHAIN_ACCOUNT_ERROR("30013", "P3", "获取区块链账户失败"),

    /**
     * 区块链账户注册失败
     */
    BLOCKCHAIN_ACCOUNT_REGISTER_ERROR("30014", "P3", "区块链账户注册失败"),

    /**
     * cloudIDE 交易发送异常
     */
    CLOUD_IDE_DELEGATE_ERROR("30015", "P3", "cloudIDE服务异常"),

    /**
     * 区块链证书创建失败
     */
    APPLY_BLOCKCHAIN_CERTIFICATION_ERROR("30016", "P3", "区块链证书创建失败"),

    /**
     * 区块链证书重置失败
     */
    RESET_BLOCKCHAIN_CERTIFICATION_ERROR("30017", "P3", "重置区块链证书失败"),

    BLOCKCHAIN_CERTIFICATION_NOT_EXIST("30019", "P5", "证书不存在"),

    BLOCKCHAIN_CERTIFICATION_ERROR("30020", "P4", "获取证书错误"),

    BLOCKCHAIN_TEE_PUB_KEY_ERROR("30020", "P4", "获取TEE公钥错误"),

    /**
     * 验证码错误
     */
    VCODE_ERROR("30028", "P3", "验证码错误"),

    BLOCKCHAIN_BAAS_REST_ERROR("30031", "P3", "BAAS REST调用失败"),

    BLOCKCHAIN_CREATE_ETH_ACCOUNT_REPEAT("30032", "P5", "账户已存在"),

    //TODO
    /**
     * IO异常
     */
    IO_ERROR("40001", "P4", "IO异常"),
    /**
     * OSS异常
     */
    OSS_ERROR("40002", "P4", "OSS异常"),

    // 开发者社区下游服务错误
    /**
     * 支付宝开放平台请求异常
     */
    ALIPAY_OPEN_ERROR("50001", "P3", "支付宝开放平台请求失败"),

    /**
     * 调用合约失败
     */
    CALL_CONTRACT_FAIL_ERROR("50002", "P4", "调用合约失败"),

    /**
     * mypocket服务异常
     */
    MYPOCKET_SERVICE_ERROR("50003", "P4", "MyPocket服务异常"),

    /**
     * ChainInsight 服务异常
     */
    CHAININSIGHT_SERVICE_ERROR("50004", "P4", "ChainInsight服务异常"),

    // 开发者社区错误
    /**
     * 社区用户不存在
     */
    COMMUNITY_USER_NOT_EXIST_ERROR("60001", "P5", "开发者社区用户不存在"),

    /**
     * 奖励发放失败
     */
    ISSUE_REWARD_FAIL_ERROR("60002", "P4", "奖励发放失败"),

    /**
     * did没有绑定
     */
    DID_NOT_BIND_ERROR("60003", "P5", "did未绑定"),

    /**
     * 更新用户资料失败
     */
    UPDATE_USER_PROFILE_ERROR("60004", "P4", "更新用户资料失败"),

    /**
     * 资产不存在
     */
    ASSET_NOT_FOUND_ERROR("60005", "P4", "资产不存在"),

    /**
     * did绑定失败
     */
    DID_BIND_FAIL_ERROR("60006", "P5", "DID绑定失败"),

    /**
     * 资产已发放
     */
    ASSET_ALREADY_REDEEMED_ERROR("60007", "P5", "您已领取过该资产"),

    /**
     * 资产已发放
     */
    ASSET_NOT_APPLICABLE_ERROR("60008", "P5", "未满足当前资产兑换条件"),

    /**
     * 数科id绑定失败
     */
    DIGITAL_ID_BIND_FAIL_ERROR("60009", "P5", "数科ID绑定失败"),

    /**
     * 资产库存不存在
     */
    ASSET_STOCK_NOT_FOUND_ERROR("60010", "P4", "资产不存在"),

    /**
     * 资产兑换太频繁
     */
    ASSET_REDEEM_TOO_FREQUENT_ERROR("60011", "P5", "资产兑换太频繁"),

    /**
     * 资产兑换失败
     */
    ASSET_REDEEM_FAIL_ERROR("60012", "P5", "资产兑换失败"),

    /**
     * 展会注册失败
     */
    EXHIBITION_REGISTRATION_ERROR("60013", "P5", "展会注册失败"),

    // 展会相关
    /**
     * QR CODE 已被其他用户扫描
     */
    QR_CODE_NOT_EFFECTIVE("70001", "P5", "二维码已失效"),

    /**
     * 扫码登录失败
     */
    SCAN_LOGIN_FAIL("70002", "P5", "扫码登录失败"),

    /**
     * 兑换券不存在
     */
    COUPON_NOT_EXIST("70003", "P5", "兑换券不存在"),

    /**
     * 兑换券不可重复核销
     */
    COUPON_ALREADY_REDEEMED("70004", "P5", "兑换券不可重复核销"),

    /**
     * 用户无权访问
     */
    NO_PERMISSION("70005", "P5", "用户无权访问"),

    /**
     * 资产锁定失败
     */
    ASSET_LOCK_FAIL("70006", "P5", "资产锁定失败"),

    /**
     * 资产不存在
     */
    ASSET_NOT_REDEEMED_ERROR("70007", "P4", "资产未兑换"),

    /**
     * 资产不存在
     */
    INVALID_ASSET("70008", "P4", "非法资产"),

    /**
     * 登录码不存在
     */
    LOGIN_TOKEN_NOT_FOUND("70009", "P5", "login token不存在"),

    /**
     * 资产已被领取
     */
    ASSET_ALREADY_REDEEMED("70010", "P5", "资产已被领取"),

    ASSET_MINT_INPUT_ADDRESS_INVALID("80023", "P5", "输入的账户地址长度不对"),
    ASSET_MINT_URI_EMPTY("80024", "P5", "输入的metadata uri 不能为空"),
    ASSET_MINT_APPLY_ERROR("80025", "P4", "创建ASSET_MINT请求失败"),
    ASSET_MINT_ORDER_EXIST("80026", "P5", "铸造订单交易已发送"),
    ASSET_MINT_ORDER_STATUS_ERROR("80027", "P4", "铸造订单状态异常"),
    ASSET_MINT_ORDER_NOT_FOUND("80028", "P5", "铸造订单不存在"),
    OSS_ADD_METADATA_FAILED("80029", "P5", "上传MetaData失败"),
    OSS_ADD_IMG_FAILED("80030", "P5", "上传IMG资源失败"),
    AVATAR_NOT_FOUND("80031", "P5", "图像资源未找到"),
    ;


    private String errorLevel;

    private String errorCode;

    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    ErrorCodeEnum(String errorCode, String errorLevel, String errorMsg) {
        this.errorCode = errorCode;
        this.errorLevel = errorLevel;
        this.errorMsg = errorMsg;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
