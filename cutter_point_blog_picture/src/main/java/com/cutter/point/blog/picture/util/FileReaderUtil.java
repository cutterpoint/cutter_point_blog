package com.cutter.point.blog.picture.util;

import com.cutter.point.blog.picture.config.FileConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileReader
 * @Description TODO
 * @Author xiaof
 * @Date 2019/10/9 21:22
 * @Version 1.0
 **/
public class FileReaderUtil {

    private final static Logger logger = Logger.getLogger(FileReaderUtil.class);
    //设置读取文件缓存大小 8K
    private final static int readBufferSize = 1024 * 8;


    public static byte[] read(String fileUrl, long position, int size) {
        //从指定路径下读取长度为size的文件，注意这里的文件可能名字呗改了，那么就要读取去掉temp的数据块
        byte[] res = new byte[size];
        File targetFile = new File(fileUrl);
        if (!targetFile.exists()) {
            //判断是否是temp，如果是，那么就去掉后缀
            if (fileUrl.endsWith(FileConfig.STORE_FILE_EXPAND)) {
                targetFile = new File(fileUrl.substring(0, fileUrl.length() - 5));
            }
        }

        if (!targetFile.exists()) {
            return null;
        }

        //读取文件
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, size);

            int cur = 0;
            //每次读取1024
            while (cur < size) {
                int less = size - cur;
                if (less > readBufferSize) {
                    mappedByteBuffer.get(res, cur, readBufferSize);
                } else {
                    mappedByteBuffer.get(res, cur, less);
                }
                //不动单独判断+less，因为后面比less比缓存区还小，那么+一个缓存区的大小肯定比size大
                cur += readBufferSize;
            }

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }


        return res;
    }

}
