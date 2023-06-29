package com.antgroup.antchain.xbuilders.service;

import com.antgroup.antchain.xbuilders.model.entity.ExhibitionSessionBO;
import com.antgroup.antchain.xbuilders.model.entity.ScanLoginTokenStatusBO;
import com.antgroup.antchain.xbuilders.model.entity.SessionBO;
import com.antgroup.antchain.xbuilders.model.enums.LoginTokenStatusEnum;

public interface SessionService {
    /**
     * 创建session
     *
     * @param alipayUid 租户id
     * @return
     */
    SessionBO createSession(String alipayUid);

    /**
     * 创建扫码登录session
     *
     * @return
     */
    ScanLoginTokenStatusBO createScanLoginSession();

    /**
     * 查看login token状态
     *
     * @return
     */
    ExhibitionSessionBO getScanLoginSession(String loginToken);

    /**
     * 更新登录会话信息
     *
     * @param loginToken  登录token
     * @param fromStatus  期望的当前登录状态
     * @param toStatus    要更新的登录态
     * @param scannedUser 扫码用户
     * @return
     */
    ScanLoginTokenStatusBO updateScanLoginSession(String loginToken,
                                                  LoginTokenStatusEnum fromStatus,
                                                  LoginTokenStatusEnum toStatus, String scannedUser);

    /**
     * 获取会话支付宝uid
     *
     * @param sessionId 会话id
     * @return
     */
    String getSessionUser(String sessionId);
}
