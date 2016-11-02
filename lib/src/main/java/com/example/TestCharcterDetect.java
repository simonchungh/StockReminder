package com.example;


import java.io.UnsupportedEncodingException;

public class TestCharcterDetect {
    public static void main(String[] args) throws java.io.IOException {
        String str = new String("�й�ʯ��");
        byte[] bytes = str.getBytes("GB2312");
        System.out.println("hex=" + bytesToHex(bytes));
        for(int i=0; i<bytes.length; i++) {
            System.out.println("byte=0x" + bytes[i]);
        }
        String utf8 = new String(bytes,"utf-8");
        System.out.println("utf8=" + utf8);
        //System.out.println("utf8=" + gbToUtf8(str));
    }
    public static String gbToUtf8(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (s.charAt(0) > 0x80) {
                byte[] bytes = s.getBytes("Unicode");
                String binaryStr = "";
                for (int j = 2; j < bytes.length; j += 2) {
                    // the first byte
                    String hexStr = getHexString(bytes[j + 1]);
                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));
                    binaryStr += binStr;
                    // the second byte
                    hexStr = getHexString(bytes[j]);
                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));
                    binaryStr += binStr;
                }
                // convert unicode to utf-8
                String s1 = "1110" + binaryStr.substring(0, 4);
                String s2 = "10" + binaryStr.substring(4, 10);
                String s3 = "10" + binaryStr.substring(10, 16);
                byte[] bs = new byte[3];
                bs[0] = Integer.valueOf(s1, 2).byteValue();
                bs[1] = Integer.valueOf(s2, 2).byteValue();
                bs[2] = Integer.valueOf(s3, 2).byteValue();
                String ss = new String(bs, "UTF-8");
                sb.append(ss);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
    private static String getHexString(byte b) {
        String hexStr = Integer.toHexString(b);
        int m = hexStr.length();
        if (m < 2) {
            hexStr = "0" + hexStr;
        } else {
            hexStr = hexStr.substring(m - 2);
        }
        return hexStr;
    }

    private static String getBinaryString(int i) {
        String binaryStr = Integer.toBinaryString(i);
        int length = binaryStr.length();
        for (int l = 0; l < 8 - length; l++) {
            binaryStr = "0" + binaryStr;
        }
        return binaryStr;
    }
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
