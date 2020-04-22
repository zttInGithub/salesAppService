package com.hrtp.system.util;


/**
 * 二进制,十进制,十六进制,BYTE,字符串,汉字之间的转换
 *
 * @author 殷烈.
 * @E-mail: yin_lie@126.com
 * @DateTime: May 3, 2011 9:18:01 AM
 */
public class HexConvert {

    private static final String TAG = "HexConvert";

    /***************************************************************************
     * 将byte数组转为16进制字符串
     *
     * @author yaoy
     * @param bytes
     * @return
     */
    public static String byte2HexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /***************************************************************************
     * 将字符串包括英文字母转为16进制
     *
     * @author yaoy
     * @param
     * @return
     */
    public static String toHexString(String s) {
        String str = "";
        if (null == s) {
            return "";
        }
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 把16进制字符串转换成字节数组
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制转二进制
     *
     * @param rsrcc 16进制字符串
     * @return 2进制
     */
    public static String hexToBin(String rsrcc) {
        String two = "";
        for (int i = 0; i < rsrcc.length(); i++) {
            String s = rsrcc.substring(i, i + 1);
            if ("0".equals(s)) {
                two += "0000";
            } else if ("1".equals(s)) {
                two += "0001";
            } else if ("2".equals(s)) {
                two += "0010";
            } else if ("3".equals(s)) {
                two += "0011";
            } else if ("4".equals(s)) {
                two += "0100";
            } else if ("5".equals(s)) {
                two += "0101";
            } else if ("6".equals(s)) {
                two += "0110";
            } else if ("7".equals(s)) {
                two += "0111";
            } else if ("8".equals(s)) {
                two += "1000";
            } else if ("9".equals(s)) {
                two += "1001";
            } else if ("A".equals(s)) {
                two += "1010";
            } else if ("B".equals(s)) {
                two += "1011";
            } else if ("C".equals(s)) {
                two += "1100";
            } else if ("D".equals(s)) {
                two += "1101";
            } else if ("E".equals(s)) {
                two += "1110";
            } else if ("F".equals(s)) {
                two += "1111";
            }
        }
        return two;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param src
     * @return
     */
    public static String twoTohexString(String src) {
        String two = "";
        for (int i = 0; i < src.length(); i += 4) {
            String s = src.substring(i, i + 4);
            if ("0000".equals(s)) {
                two += "0";
            } else if ("0001".equals(s)) {
                two += "1";
            } else if ("0010".equals(s)) {
                two += "2";
            } else if ("0011".equals(s)) {
                two += "3";
            } else if ("0100".equals(s)) {
                two += "4";
            } else if ("0101".equals(s)) {
                two += "5";
            } else if ("0110".equals(s)) {
                two += "6";
            } else if ("0111".equals(s)) {
                two += "7";
            } else if ("1000".equals(s)) {
                two += "8";
            } else if ("1001".equals(s)) {
                two += "9";
            } else if ("1010".equals(s)) {
                two += "A";
            } else if ("1011".equals(s)) {
                two += "B";
            } else if ("1100".equals(s)) {
                two += "C";
            } else if ("1101".equals(s)) {
                two += "D";
            } else if ("1110".equals(s)) {
                two += "E";
            } else if ("1111".equals(s)) {
                two += "F";
            }
        }
        return two;
    }

    /**
     * 十六进制字符串转ASCII码
     *
     * @param src
     * @return
     */
    public static String HexToStingbeyAscII(String src) {
        // 如果30代表是 16进制的30话，就取16
        // 如果30代表是 10进制的30话，就取10
        // code = Integer.parseInt(source, 10);
        String result = "";
        for (int i = 0; i < (src.length() - 2); ) {
            int code = Integer.parseInt(src.substring(i, i + 2), 16);
            i = i + 2;
            result += code;
        }
        return result;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
     * 0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 16进制转换成字符
     *
     * @param b byte[]
     * @return String
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            char hex = (char) Integer.parseInt(Integer.toHexString(b[i]), 16);
            ret += hex;
        }
        return ret;
    }

    /**
     * 16进制转换成字符
     *
     * @param b byte[]
     * @return String
     */
    public static String Bytes2HexString(byte[] b, int j) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String a = Integer.valueOf(String.valueOf(b[i]), 16).toString();// 16进制的字符串变10进制的字符串
            char hex = (char) Integer.parseInt(a);
            ret += hex;
        }
        return ret;
    }

    /**
     * 16[]进制转10进制
     *
     * @param b
     * @return
     */
    public static String hexStingbySrc(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            ret = Integer.valueOf(String.valueOf(b[i]), 16).toString();
        }
        return ret;
    }

    /**
     * 16进制转10进制数字
     *
     * @param hex 16进制字符串
     * @return 10进制
     */
    public static String hexToDec(String hex) {
        String dec = null;
        dec = Integer.valueOf(hex, 16).toString();
        return dec;
    }

    /**
     * @函数功能: BCD码转为10进制串(阿拉伯数据)
     * @输入参数: BCD码
     * @输出结果: 10进制串
     */
    public static String bcd2Str10(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return "0".equalsIgnoreCase(temp.toString().substring(0, 1)) ? temp
                .toString().substring(1) : temp.toString();
    }

    /** */
    /**
     * @函数功能: 10进制串转为BCD码
     * @输入参数: 10进制串
     * @输出结果: BCD码
     */
    public static byte[] str10Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * 转化十六进制编码为字符串
     *
     * @param s
     * @return
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
            }
        }

        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
        }
        return s;
    }

    /**
     * 转化字符串为十六进制字符编码
     */
    public static String toHexStrings(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 汉字转16进制(UTF-8)
     *
     * @param s
     * @return
     */
    public static String stringCinvertHex(String s) {
        String str = "";
        try {
            byte[] b = s.getBytes("gb2312");
            for (int i = 0; i < b.length; i++) {
                Integer I = new Integer(b[i]);
                String strTmp = Integer.toHexString(b[i]);
                if (strTmp.length() > 2)
                    strTmp = strTmp.substring(strTmp.length() - 2);
                str = str + strTmp;
            }
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 16进制转汉字
     *
     * @param s
     * @return
     */
    public static String hexConvertString(String s) {
        String str = "";
        byte[] theByte = new byte[s.length() / 2];
        int j;
        try {
            j = 0;
            for (int i = 0; i < s.length(); i += 4) {
                theByte[i / 4 + j] = Integer.decode(
                        "0X" + s.substring(i, i + 2)).byteValue();
                if (i + 4 <= s.length()) {
                    theByte[i / 4 + 1 + j] = Integer.decode(
                            "0X" + s.substring(i + 2, i + 4)).byteValue();
                }
                j++;
            }
            str = new String(theByte, 0, theByte.length, "gb2312");
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 压缩BCD码转换成字符串
     *
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return "0".equalsIgnoreCase(temp.toString().substring(0, 1)) ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * 字符串转换成压缩BCD
     *
     * @param asc
     * @return
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * beyte转非压缩BCD码
     *
     * @param in
     * @return
     */
    public static byte[] ByteToBCD(byte[] in) {
        byte[] out = new byte[in.length * 2];
        int tmp;
        for (int i = 0; i < in.length * 2; i++) {
            tmp = (in[i >> 1] >> ((i + 1) % 2) * 4) & 0x0f;

            switch (tmp) {
                case 10:
                    out[i] = 'A';
                    break;
                case 11:
                    out[i] = 'B';
                    break;
                case 12:
                    out[i] = 'C';
                    break;
                case 13:
                    out[i] = 'D';
                    break;
                case 14:
                    out[i] = 'E';
                    break;
                case 15:
                    out[i] = 'F';
                    break;
                default:
                    out[i] = (byte) (tmp | 0x30);
            }
        }

        return out;
    }

    /**
     * 异或
     *
     * @param szHexOffset byte数组
     * @param szStrSeed2  字符串
     * @return
     */
    public static byte[] xor(byte[] szHexOffset, String szStrSeed2)
            throws Exception {
        // 转换成非压缩BCD格式byte
        byte[] bByte = HexConvert.HexString2Bytes(szStrSeed2.toString());
        byte[] cString = new byte[bByte.length];
        try {
            for (int i = 0; i < bByte.length; i++) {
                int a = szHexOffset[i];
                int b = bByte[i];
                cString[i] = (byte) (a ^ b);
            }
        } catch (Exception e) {
        }
        return cString;
    }

    /**
     * 转化十六进制编码为字符串
     *
     * @param s
     * @return
     */
    public static String toStringHexs1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
        }
        return s;
    }

    /**
     * 转化十六进制编码为字符串
     *
     * @param s
     * @return
     */
    public static String toStringHexs(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
        }
        return s;
    }

    /**
     * 不足多少位的填充空格
     *
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.getBytes().length;
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuilder sb = new StringBuilder();
                //        sb.append("0").append(str);//左补0
                sb.append(str).append(" ");//右补0
                str = sb.toString();
                strLen++;
            }
        } else {
            str = str.substring(0, 20);
        }
        return str;
    }

    public static byte[] intToByte(int i) {

        byte[] abyte0 = new byte[4];

        abyte0[0] = (byte) (0xff & i);

        abyte0[1] = (byte) ((0xff00 & i) >> 8);

        abyte0[2] = (byte) ((0xff0000 & i) >> 16);

        abyte0[3] = (byte) ((0xff000000 & i) >> 24);

        return abyte0;
    }

    public static int bytesToInt(byte[] bytes) {

        int addr = bytes[0] & 0xFF;

        addr |= ((bytes[1] << 8) & 0xFF00);

        addr |= ((bytes[2] << 16) & 0xFF0000);

        addr |= ((bytes[3] << 24) & 0xFF000000);

        return addr;
    }
}
