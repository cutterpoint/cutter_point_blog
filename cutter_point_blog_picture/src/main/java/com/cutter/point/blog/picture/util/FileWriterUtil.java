package com.cutter.point.blog.picture.util;

import com.cutter.point.blog.picture.config.FileConfig;
import com.cutter.point.blog.picture.entity.TFileStore;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName FileWriterUtil
 * @Author xiaof
 * @Date 2019/10/9 21:30
 * @Version 1.0
 **/
public class FileWriterUtil {

    private Logger logger = Logger.getLogger(FileWriterUtil.class);

    //使用mapperbuffer
    private File curFile = null;
    private MappedByteBuffer mappedByteBuffer = null;
    private RandomAccessFile randomAccessFile = null;
    private FileChannel fileChannel = null;
    private long curPosition = 0;
    private volatile static FileWriterUtil INSTACE;

    private FileWriterUtil(){
        //保持单例
    }

    public static FileWriterUtil getInstance() {
        if (INSTACE == null) {
            synchronized (FileWriterUtil.class) {
                if (INSTACE == null) {

                    INSTACE = new FileWriterUtil();
                }
            }
        }
        return INSTACE;
    }

    //写入文件的时候，判断文件位置，文件是否存在，不存在要创建对应的文件
    public TFileStore write(byte[] data) {
        //写入文件，判断文件是否存在
        TFileStore fileInfo = new TFileStore();
        targetMap();
        fileInfo.setFileUrl(curFile.getAbsolutePath());
        fileInfo.setFilePosition(curPosition);
        fileInfo.setFileSize(data.length);
        //写入文件内容
        synchronized(mappedByteBuffer) {
            mappedByteBuffer.put(data);

            curPosition += data.length;
        }
        return fileInfo;
    }

    private File createFile(String dirPath) {
        String dataFileName = currentDateToString("yyyyMMddHHmmssSSS") + "." + FileConfig.STORE_FILE_EXPAND;
        curFile = new File(dirPath + File.separator + dataFileName);
        try {
            curFile.createNewFile();

        } catch (IOException e) {
//            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        return curFile;
    }

    private MappedByteBuffer createMappedByteBuffer() {

        //多线程情况下使用
        synchronized (this) {
            String dirPath = FileConfig.getFileBasePath() + File.separator + currentDateToString("yyyy-MM-dd");
            File file = new File(dirPath);
            //判断文件夹是否存在
            if (!file.exists()) {
                file.mkdirs();
            }

            //判断文件是否存在，重启等原因
            File[] files = file.listFiles(new FileWriterUtil.NpiFileFileter());
            if (files.length == 0) {
                //然后创建文件
                curFile = createFile(dirPath);
            } else if (files.length == 1) {
                //恰好是之前创建的文件
                //判断文件大小
                File fileTarget = files[0];
                if (fileTarget.length() >= FileConfig.getMaxFileSize()) {
                    // file size over setting
                    // temp文件大于最大長度时,创建新的文件
                    String fileName = fileTarget.getAbsolutePath();// temp
                    fileName = fileName.substring(0, fileName.length() - 5);
                    fileTarget.renameTo(new File(fileName));
                    // create new file
                    curFile = createFile(dirPath);
                } else {
                    curFile = file;
                }
            }
        }
        return mappedByteBuffer;
    }

    private MappedByteBuffer targetMap() {
        //定位的时候判断是否为空，如果为空那么就要创建一个，如果不为空判断是否需要扩文件
        if (mappedByteBuffer == null) {
            //创建一个文件
            mappedByteBuffer = createMappedByteBuffer();
        }

        return mappedByteBuffer;
    }

    public static String currentDateToString(String timeRegex) {
        SimpleDateFormat formatDate = new SimpleDateFormat(timeRegex);
        String str = formatDate.format(new Date());
        return str;
    }

    class NpiFileFileter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            String fileName = pathname.getName();
            if (fileName.indexOf("_temp") != -1) {
                return true;
            } else {
                return false;
            }

        }
    }

}
