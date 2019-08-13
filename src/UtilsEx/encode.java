/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsEx;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Chí Công Jr
 */
public class encode {
     public static String encrypt(String srcText) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        String enrText = null;
        
        MessageDigest msd = MessageDigest.getInstance("MD5");
        byte[] srcTextBytes = srcText.getBytes("UTF-8");
        byte[] enrTextBytes = msd.digest(srcTextBytes);
        BigInteger bigInteger = new BigInteger(1, enrTextBytes);
        enrText = bigInteger.toString(16);
        return enrText;
    }
}
