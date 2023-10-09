package com.ytdapp.tools;

import android.text.TextUtils;


import com.ytdapp.tools.log.YTDLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class YTDStringUtils {

    public static String componentsJoinedByString(List <String> stringList ,String regex){
        if (stringList == null || stringList.size() == 0) {
            return "";//如果为空,就返回""
        }
        StringBuilder stringBuilder = new StringBuilder();//实例化stringbuilder
        final int size = stringList.size();//获取列表总长度
        for (int i = 0; i < size; i++) {//for循环
            stringBuilder.append(stringList.get(i));//拼接字符
            if (i < size - 1) {//最后一位不用加分隔符
                stringBuilder.append(regex);//每个字符后面添加分隔符
            }
        }
        return stringBuilder.toString();//返回stringbuilder
    }

    public static List<String> componentsSeparatedByString(String string,String regex){
        if (TextUtils.isEmpty(string)) {
            return null;//如果为空,就返回null
        }
        return Arrays.asList(string.split(regex));
    }

    /*路径获取最后部分，文件名称加类型*/
    public static String lastPathComponent(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";//如果为空,就返回null
        }

        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        return segments[segments.length - 1];
    }

    /*路径获取最后部分，文件名称，删除文件类型*/
    public static String stringByDeletingPathExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";//如果为空,就返回null
        }

        String[] segments = filePath.split("\\.");
        if (segments.length < 2)
            return filePath;

        return segments[segments.length - 2];
    }

    /*路径获取最后部分，文件类型*/
    public static String pathExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";//如果为空,就返回null
        }

        String[] segments = filePath.split("\\.");
        if (segments.length < 2)
            return "";

        return segments[segments.length - 1];
    }

    /**
     * 获取文件名
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    /**
     * 获取文件名不带后缀
     * @param filePath
     * @return
     */
    public static String getFileNameNoExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            YTDLog.log(e);
        }
        return "";
    }

    public static String thumbnailImageSize(String imageUrl, int size) {
        return imageUrl + "?x-oss-process=image/resize,w_" + size;
    }

    /**
     * 字符串匹配
     * @param source
     * @param input
     * @return
     */
    public static boolean isAmbiguousMatch(String source, String input) {
        input = input.replace(" ", "").toUpperCase();
        if(source.contains(input)){
            return true;
        }
        char[] charArray = input.toCharArray();
        int i = 0;
        int index = 0;
        for(; i < charArray.length && index < source.length(); i++){
            index = source.indexOf(String.valueOf(charArray[i]), index);
            if(index < 0){
                return false;
            }
            index++;
        }
        if(i < charArray.length){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * 返回币种
     * @return
     */
    public static String getCurrencyUnit(String currency) {
        return "CNY".equals(currency) ? "￥" : "$";
    }

    /**
     * 是否是密码格式
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        return pwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,18}$");
    }

    /**
     * 是否账号格式
     * @param account
     * @return
     */
    public static boolean isAccount(String account) {
        return account.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
    }

    /**
     * 是否邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return email.matches("^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9])+.)+([a-zA-Z0-9]{2,4})+$");
    }

    /**
     * @Description: 四舍五入格式化金额，千分位，并保留两位小数
     * @param  text 类型 金额
     * @return String 格式化后金额（格式：12,345.67）
     */
    public static String fmtMicrometer(String text) {
        BigDecimal money = new BigDecimal(text);
        try {
            DecimalFormat df = new DecimalFormat(",##0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df.format(money);
        } catch(Exception e) {
            return null;
        }
    }
}
