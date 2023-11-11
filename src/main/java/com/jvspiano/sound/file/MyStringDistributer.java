package com.jvspiano.sound.file;

import java.io.RandomAccessFile;

/**
 * 行解析并分发给相应的解析行为接口定义
 */
public interface MyStringDistributer {
    enum Type {
        MAJOR("major"),
        PPQ("ppq"),
        BPM("bpm"),
        RIGHT_START("rightStart"),
        RIGHT_END("rightEnd"),
        LEFT_START("leftStart"),
        LEFT_END("leftEnd"),
        EXPLANATORY_NOTE("//"),
        INSTRUMENT("instrument"),
        CHANNEL("channel"),

        ;
        public final String value;

        Type(String value) {
            this.value = value.toLowerCase();
        }
    }

    enum SEPARATOR {
        SPACE(" "),
        COMMA(",")
        ;
        public final String value;
        SEPARATOR(String value){
            this.value = value;
        }
    }

    void distribute(RandomAccessFile randomAccessFile);
    int lineNoteDistributeNote(String line, int lineNum, int lineStart);
}
