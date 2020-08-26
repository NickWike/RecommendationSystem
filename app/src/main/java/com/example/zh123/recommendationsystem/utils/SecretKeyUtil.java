package com.example.zh123.recommendationsystem.utils;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * Created by zh123 on 20-3-14.
 */

public class SecretKeyUtil {
    private static PublicKey mRSAPublicKey = null;

    public static void loadPublicKey(String pubKey) {

        try {
            // 这里是由于 服务端传回的pubKey 会带有 begin/end 标识,这里的作用就是去除标识
            String[] sArr = pubKey.split("\n");
            if("-----BEGIN PUBLIC KEY-----".equals(sArr[0])){
                StringBuilder tempKey = new StringBuilder();
                // 下标 1~length-2 去掉头尾
                for(int i=1;i<sArr.length-1;i++){tempKey.append(sArr[i]);}
                pubKey = tempKey.toString();
            }

            byte[] buffer = Base64.decode(pubKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            mRSAPublicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encryptWithRSA(String plainData) throws Exception {
        if (mRSAPublicKey == null) {
            throw new NullPointerException("encrypt PublicKey is null !");
        }

        Cipher cipher = null;
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"加密出来的信息JAVA服务器无法解析

        cipher.init(Cipher.ENCRYPT_MODE, mRSAPublicKey);

        byte[] output = cipher.doFinal(plainData.getBytes("utf-8"));
        // 必须先encode成 byte[]，再转成encodeToString，否则服务器解密会失败
        // byte[] encode = Base64.encode(output, Base64.URL_SAFE|Base64.NO_WRAP);
        // return Base64.encodeToString(encode, Base64.URL_SAFE|Base64.NO_WRAP);
        // 用原始Android自带的base64编码器计算出的编码会多64个长度,这里就用的自己写的一个base64编码工具
        return Base64Encoder.encode(output);
    }

}
