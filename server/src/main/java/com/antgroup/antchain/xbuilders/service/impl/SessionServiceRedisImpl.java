package com.antgroup.antchain.xbuilders.service.impl;


import com.antgroup.antchain.xbuilders.model.entity.ExhibitionSessionBO;
import com.antgroup.antchain.xbuilders.model.entity.ScanLoginTokenStatusBO;
import com.antgroup.antchain.xbuilders.model.entity.SessionBO;
import com.antgroup.antchain.xbuilders.model.enums.LoginTokenStatusEnum;
import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum.QR_CODE_NOT_EFFECTIVE;
import static com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum.SCAN_LOGIN_FAIL;

@Service
@Slf4j
@ConditionalOnProperty(name = "redis.enable", havingValue = "true", matchIfMissing = true)
public class SessionServiceRedisImpl implements SessionService {
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

    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建session
     *
     * @param alipayUid 用户id
     * @return
     */
    @Override
    public SessionBO createSession(String alipayUid) {
        String sessionId = UUID.randomUUID().toString();

        // 存储session到redis
        redissonClient.getBucket(generateSessionKey(sessionId))
                .set(alipayUid, timeToLive, TimeUnit.MILLISECONDS);

        SessionBO sessionBO = new SessionBO();
        sessionBO.setSessionId(sessionId);
        sessionBO.setSessionTime(System.currentTimeMillis() + timeToLive);

        return sessionBO;
    }

    @Override
    public ScanLoginTokenStatusBO createScanLoginSession() {
        String tokenId = UUID.randomUUID().toString();

        redissonClient.getBucket(generateExhibitionSessionKey(tokenId))
                .set(new ExhibitionSessionBO(LoginTokenStatusEnum.IDLE.getCode(), null),
                        exhibitionTtl, TimeUnit.MILLISECONDS);

        return new ScanLoginTokenStatusBO(tokenId, LoginTokenStatusEnum.IDLE,
                System.currentTimeMillis() + exhibitionTtl);
    }

    @Override
    public ExhibitionSessionBO getScanLoginSession(String loginToken) {
        return redissonClient.<ExhibitionSessionBO>getBucket(generateExhibitionSessionKey(loginToken)).get();
    }

    @Override
    public ScanLoginTokenStatusBO updateScanLoginSession(String loginToken,
                                                         LoginTokenStatusEnum fromStatus,
                                                         LoginTokenStatusEnum toStatus, String scannedUser) {
        RLock rLock = redissonClient.getLock(generateExhibitionSessionLockKey(loginToken));
        try {
            if (!rLock.tryLock(3, 10, TimeUnit.SECONDS)) {
                log.error("failed to lock session {}", loginToken);
                throw new BusinessException(SCAN_LOGIN_FAIL);
            }

            ExhibitionSessionBO sessionBO = getScanLoginSession(loginToken);
            if (sessionBO == null) {
                log.warn("invalid login token {}", loginToken);
                throw new BusinessException(QR_CODE_NOT_EFFECTIVE);
            }
            if (StringUtils.isNotBlank(sessionBO.getScannedUid())
                    && !sessionBO.getScannedUid().equals(scannedUser)) {
                log.error("qrcode used by {}, {} not owner", sessionBO.getScannedUid(), scannedUser);
                throw new BusinessException(QR_CODE_NOT_EFFECTIVE);
            }

            // 起始态为空，不检查起始态
            if (fromStatus == null
                    // 起始态符合预期
                    || fromStatus.getCode() == sessionBO.getTokenStatus()
                    // 转移态和当前一致，那证明已经转移过了，重复转移做幂等
                    || toStatus.getCode() == sessionBO.getTokenStatus()) {
                sessionBO.setScannedUid(scannedUser);
                sessionBO.setTokenStatus(toStatus.getCode());

                redissonClient.getBucket(generateExhibitionSessionKey(loginToken))
                        .set(sessionBO, exhibitionTtl, TimeUnit.MILLISECONDS);
                return new ScanLoginTokenStatusBO(loginToken, toStatus,
                        System.currentTimeMillis() + exhibitionTtl);
            }

            log.error("session {}, login status {}, not as expected {}",
                    loginToken, LoginTokenStatusEnum.getStatusByCode(sessionBO.getTokenStatus()), fromStatus);
            throw new BusinessException(SCAN_LOGIN_FAIL);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("unexpected session update error", e);
            throw new BusinessException(SCAN_LOGIN_FAIL, e);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 获取会话租户id
     *
     * @param sessionId 会话id
     * @return
     */
    @Override
    public String getSessionUser(String sessionId) {
        // 从redis获取session
        Object userId = redissonClient.getBucket(generateSessionKey(sessionId)).get();
        if (ObjectUtils.isEmpty(userId)) {
            return null;
        }

        return (String) userId;
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
