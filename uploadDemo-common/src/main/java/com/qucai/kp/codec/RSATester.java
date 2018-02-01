package com.qucai.kp.codec;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class RSATester {
    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCux3pjVmIvLO4evzYcKHPtw6J0Ya1xx+U3/PbP3WwTpcbqgMTqUbkRNGyDA5PZI+hqRn51iK9Byg5nr0T9nt/WZ882b/fneBw1wRT8F0IbCXVWVOOIOo70HZryewqVFjD5Kmv7kFCopiVl9/IsE3kuENzsLJJrM4Jda1PVkg71YwIDAQAB";
    static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK7HemNWYi8s7h6/Nhwoc+3DonRhrXHH5Tf89s/dbBOlxuqAxOpRuRE0bIMDk9kj6GpGfnWIr0HKDmevRP2e39ZnzzZv9+d4HDXBFPwXQhsJdVZU44g6jvQdmvJ7CpUWMPkqa/uQUKimJWX38iwTeS4Q3Owskmszgl1rU9WSDvVjAgMBAAECgYAFX668pShJqQj9OwXP4dKGqfAxwdsSV4INsaJpC7bJ99vg0+q6BZ86DzGS6aY/E+KOefJjbLtuu2ar+3CUl7DsGpZNK4kB9np1ShdaPU4ArVOb/0ULbEMk2/CruVN0q+/DtulOT3Q69x571l7ENn0p1XVsJ9nrhuNQ5Z5Xq0eiuQJBAO3E6omF/CeObNWOejFZVpL6LByu6Tytwne489WSkaAipdJws17OBJTnuXR9V2r22n91dBQqLIOpRn/ifyaVqF0CQQC8LieV3+D2TAEBS8UFxG8sH0TJq1J4aHvVNDylUaVE9dmL8PaIV4TE9KCW8eXnegcBrmkRqgHbEGHixeoYuji/AkEAvbsMjIDmHYqBPkhbKVXox4/p81l/tHPTYkWsDRjQK8B6IdrcxyIOdeQTEOmUAD7LTDolTpH4w3coUpQicl+p4QJADLhZ+KmPfVaZ4sqKYN9sXGEotzEbfAQkCr9jMhGYyFaOBAWfxIuV7/JRQKLNVZ5MBvMRKx6AzZwH4dpa/pzn5QJBAIpkTtETHNLAWuYCmeRM0iZTsEhsAiQkSvUZ+EmUg9RmFp/449FhEWx3FEN7XDmMai3785/eAZtY5LWBlO6Y+GI=";

    static {
        try {
            if(StringUtils.isBlank(publicKey) || StringUtils.isBlank(privateKey)) {
                Map<String, Object> keyMap = RSAUtil.genKeyPair();
                publicKey = RSAUtil.getPublicKey(keyMap);
                privateKey = RSAUtil.getPrivateKey(keyMap);
                System.out.println("公钥: " + publicKey);
                System.out.println("私钥： " + privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        testEncryptByPublicKey();
        testEncryptByPrivateKey();
        testSign();
    }

    static void testEncryptByPublicKey() throws Exception {
        System.out.println("公钥加密——私钥解密");
        String source = "现在进行的是：公钥加密——私钥解密";
        System.out.println("原文：" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPublicKey(data, publicKey);
        String afterEncrypt = Base64.encodeBase64String(encodedData);
        System.out.println("密文：" + afterEncrypt);
        byte[] decodedData = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(afterEncrypt),
                privateKey);
        String target = new String(decodedData);
        System.out.println("解密后: " + target);
    }
    
    static void testEncryptByPrivateKey() throws Exception {
        System.out.println("私钥加密——公钥解密");
        String source = "现在进行的是：私钥加密——公钥解密";
        System.out.println("原文：" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
        String afterEncrypt = Base64.encodeBase64String(encodedData);
        System.out.println("密文：" + afterEncrypt);
        byte[] decodedData = RSAUtil.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: " + target);
    }

    static void testSign() throws Exception {
//        System.out.println("私钥加密——公钥解密");
//        String source = "现在进行的是：私钥加密——公钥解密";
//        System.out.println("原文：" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
//        String afterEncrypt = Base64.encodeBase64String(encodedData);
//        System.out.println("密文：" + afterEncrypt);
//        byte[] decodedData = RSAUtil.decryptByPublicKey(encodedData, publicKey);
//        String target = new String(decodedData);
//        System.out.println("解密后: " + target);
        
        
        byte[] encodedData = "私钥加密密文".getBytes();
        System.out.println("私钥签名——公钥验证签名");
        String sign = RSAUtil.sign(encodedData, privateKey);
        System.out.println("签名:" + sign);
        boolean status = RSAUtil.verify(encodedData, publicKey, sign);
        System.out.println("验证结果:" + status);
    }
}
