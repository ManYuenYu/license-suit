/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.crypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RSAUtils {

    /**
     * 生成RSA公、私钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> generateRSAKeyPairs() {
        Map<String, String> keyPairMap = new HashMap<String, String>();
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        generator.initialize(2048);

        KeyPair keyPair = generator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        keyPairMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
        keyPairMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        return keyPairMap;
    }

    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /** 公钥加密 并 base64编码 */
    public static String encryptByPublicKey(String plain, String base64PublicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(base64PublicKey));
            return Base64.encodeBase64String(cipher.doFinal(plain.getBytes(UTF_8)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /** 私钥解密 */
    public static String decryptByPrivateKey(String base64String, String base64PrivateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(base64PrivateKey));
            return new String(cipher.doFinal(Base64.decodeBase64(base64String)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /** 私钥加密 */
    public static String encryptByPrivateKey(String plain, String base64PrivateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(base64PrivateKey));
            return Base64.encodeBase64String(cipher.doFinal(plain.getBytes(UTF_8)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /** 公钥解密 */
    public static String decryptByPublicKey(String base64String, String base64PublicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(base64PublicKey));
            return Base64.encodeBase64String(cipher.doFinal(Base64.decodeBase64(base64String)));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
