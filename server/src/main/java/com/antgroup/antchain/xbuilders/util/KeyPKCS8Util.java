/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.antgroup.antchain.xbuilders.util;

import com.alipay.mychain.sdk.crypto.keyoperator.Pkcs8KeyOperator;
import com.alipay.mychain.sdk.crypto.keypair.Keypair;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class KeyPKCS8Util {

    /**
     * 根据加密私钥和密码，解密私钥公钥对
     *
     * @param encryptKey
     * @param password
     * @return
     */
    public static Keypair getKeypair(String encryptKey, String password) {
        try {
            return new Pkcs8KeyOperator().load(encryptKey.getBytes(), password);
        } catch (Exception e) {
            log.error("getKeypair error", e);
            return null;
        }
    }

}