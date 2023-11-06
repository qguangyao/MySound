package com.jvspiano.sound.note;

public class NoteInfo implements Comparable<NoteInfo> {
    /**
     * 音符开始
     */
    public long originTick;
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
    /**
     * 播放的时间点
     */
    public long playTime = 0;

    /**
     * @return
     */
    public int bpm = -1;

    public int getNoteLength() {
        if (muteTick == 0) {
            return noteTick;
        } else {
            return Math.min(noteTick, muteTick);
        }

    }

    @Override
    public int compareTo(NoteInfo o) {
        return (int) (originTick - o.originTick);
    }


    public long getOriginTick() {
        return originTick;
    }

    public void setOriginTick(long originTick) {
        this.originTick = originTick;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getNoteTick() {
        return noteTick;
    }

    public void setNoteTick(int noteTick) {
        this.noteTick = noteTick;
    }

    public int getMuteTick() {
        return muteTick;
    }

    public void setMuteTick(int muteTick) {
        this.muteTick = muteTick;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
