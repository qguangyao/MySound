package org.file;

import java.io.*;

public class MyFileReaderTxt implements MyFIleReader{
    @Override
    public RandomAccessFile Read(String path) {
        File file = null;
        RandomAccessFile rf = null;
        try {
            file = new File(path);
            if (!file.exists()){
                file.createNewFile();
                rf = new RandomAccessFile(file,"rw");
                String bootstrap = new String("major:F#\n" +
                        "PPQ:4/4\n" +
                        "BPM:80\n" +
                        "rightStart\n" +
                        "\n" +
                        "rightEnd\n" +
                        "leftStart\n" +
                        "\n" +
                        "leftEnd");
                rf.writeBytes(bootstrap);
            }else {
                rf = new RandomAccessFile(file,"rw");
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rf;
    }
}
