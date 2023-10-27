package org.note;

public class NoteInfo {
    /**
     * 音符开始
     */
    public int originTick;
    /**
     * 音符音高
     */
    public int note = -1;
    /**
     * 音符长短
     */
    public int noteTick = 0;
    /**
     * 音符从@originTick开始后经过@muteTick个节拍静音
     */
    public int muteTick = 0;
    /**
     * 音符音量
     */
    public int volume = 100;
}
