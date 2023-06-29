package com.antgroup.antchain.xbuilders.service.impl;


import com.antgroup.antchain.xbuilders.model.entity.ExhibitionSessionBO;
import com.antgroup.antchain.xbuilders.model.entity.ScanLoginTokenStatusBO;
import com.antgroup.antchain.xbuilders.model.entity.SessionBO;
import com.antgroup.antchain.xbuilders.model.enums.LoginTokenStatusEnum;
import com.antgroup.antchain.xbuilders.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@ConditionalOnProperty(name = "redis.enable", havingValue = "false", matchIfMissing = false)
public class SessionServiceMemoryImpl implements SessionService {
    public static String CACHE_KEY_SESSION_PREFIX = "";

    public static String EXHIBITION_CACHE_KEY_SESSION_PREFIX = "";

    public static String EXHIBITION_LOGIN_LOCK = "";

    @Value("${session.ttl}")
    private long timeToLive;

    @Value("${session.cache-max-size}")
    private long cacheMaxSize;

    @Value("${lab.exhibition.session.ttl}")
    private long exhibitionTtl;

    @Value("${lab.exhibition.cache-max-size}")
    private long exhibitionCacheMaxSize;

    /**
     * 创建session
     *
     * @param alipayUid 用户id
     * @return
     */
    @Override
    public SessionBO createSession(String alipayUid) {
        return null;
    }

    @Override
    public ScanLoginTokenStatusBO createScanLoginSession() {
        String tokenId = UUID.randomUUID().toString();

        return new ScanLoginTokenStatusBO(tokenId, LoginTokenStatusEnum.IDLE,
                System.currentTimeMillis() + exhibitionTtl);
    }

    @Override
    public ExhibitionSessionBO getScanLoginSession(String loginToken) {
        return null;
    }

    @Override
    public ScanLoginTokenStatusBO updateScanLoginSession(String loginToken,
                                                         LoginTokenStatusEnum fromStatus,
                                                         LoginTokenStatusEnum toStatus, String scannedUser) {
        return null;
    }

    /**
     * 获取会话租户id
     *
     * @param sessionId 会话id
     * @return
     */
    @Override
    public String getSessionUser(String sessionId) {
        return null;
    }

    private String generateSessionKey(String sessionId) {
        return CACHE_KEY_SESSION_PREFIX + sessionId;
    }

    private String generateExhibitionSessionKey(String sessionId) {
        return EXHIBITION_CACHE_KEY_SESSION_PREFIX + sessionId;
    }

    private String generateExhibitionSessionLockKey(String sessionId) {
        return EXHIBITION_LOGIN_LOCK + sessionId;
    }
}
