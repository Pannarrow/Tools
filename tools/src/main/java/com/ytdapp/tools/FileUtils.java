package com.ytdapp.tools;

import com.ytdapp.tools.log.YTDLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {

    /**
     * 清空文件
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        return deleteDir(filePath);
    }

    /**
     * 清空文件夹内文件
     * @param pPath 文件夹路径
     */
    public static boolean deleteDir(final String pPath) {
        return deleteDirWithFile(new File(pPath));
    }

    /**
     * 清空文件夹内文件
     * @param dir 文件夹
     */
    public static boolean deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists()){
            return false;
        }
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            if (files != null) {
                boolean b = true;
                for (File file : files) {
                    if (file.isFile()){
                        b &= file.delete(); // 删除所有文件
                    } else if (file.isDirectory()){
                        // 递规的方式删除文件夹
                        b &= deleteDirWithFile(file);
                    }
                }
                return b;
            }
        }
        return dir.delete(); // 删除目录本身
    }

    /**
     * 复制文件
     * @param source - 源文件
     * @param target - 目标文件
     */
    public static void copyFile(String source, String target) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            File sendFile = new File(target);
            if (!sendFile.exists()) {
                sendFile.createNewFile();
            }
            fi = new FileInputStream(source);
            fo = new FileOutputStream(target);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            YTDLog.log(e);
        } finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fo != null) {
                    fo.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                YTDLog.log(e);
            }
        }
    }
}
