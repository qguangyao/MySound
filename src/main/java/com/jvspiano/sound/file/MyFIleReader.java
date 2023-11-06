package com.jvspiano.sound.file;

import java.io.RandomAccessFile;

/**
 * 读取文件接口定义
 */
public interface MyFIleReader {
    RandomAccessFile Read(String path);
}
