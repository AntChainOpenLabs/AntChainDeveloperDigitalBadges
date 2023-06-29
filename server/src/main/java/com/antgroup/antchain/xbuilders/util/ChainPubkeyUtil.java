package com.antgroup.antchain.xbuilders.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class ChainPubkeyUtil {

    /**
     *  读取PEM格式的ECC公钥
     * @param publicKeyPem
     * @return
     * @throws InvalidKeySpecException
     */
    public static ECPublicKey loadPublicKeyPEM(String publicKeyPem) throws InvalidKeySpecException {
        PemReader pemReader = new PemReader(new StringReader(publicKeyPem));
        PemObject pemObject = null;
        try {
            pemObject = pemReader.readPemObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (pemObject == null) {
            throw new InvalidKeySpecException("wrong PEM format");
        }
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pemObject.getContent());
        PublicKey key = null;
        try {
            key = KeyFactory.getInstance("EC", "BC").generatePublic(pubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        return (ECPublicKey) key;
    }


    /**
    将ECPublicKey 转换为 plain hex格式。仅支持256bit EC曲线。
     */
    public static String publicKeyPEMtoHex(ECPublicKey pk) {
        ECPoint w = pk.getW();
        return String.format("%064x", w.getAffineX()) + String.format("%064x", w.getAffineY());
    }
}
