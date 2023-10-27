package org.note;

public class MyNoteIMPL implements MyNote {

    private Major major;
    private String ppq;
    private int bpm;
    private int channel = 6;//钢琴
    private String explan;

    private boolean rightStart;
    private boolean leftStart;

    private final int baseTick = 32;
    /**
     * 每个小节的长度
     */
    private int barTick = baseTick * 4;
    private OnMajorChangedListener majorChangedListener;
    private OnPPQChangedListener ppqChangedListener;
    private OnBPMChangedListener bpmChangedListener;

    public Major getMajor() {
        return major;
    }

    public MyNoteIMPL() {
        this.major = Major.C;
    }

    public String getExplan() {
        return explan;
    }

    public void setExplan(String explan) {
        this.explan = explan;
    }

    public void setMajorChangedListener(OnMajorChangedListener majorChangedListener) {
        this.majorChangedListener = majorChangedListener;
    }

    public void setPpqChangedListener(OnPPQChangedListener ppqChangedListener) {
        this.ppqChangedListener = ppqChangedListener;
    }

    public void setBpmChangedListener(OnBPMChangedListener bpmChangedListener) {
        this.bpmChangedListener = bpmChangedListener;
    }


    public String getPpq() {
        return ppq;
    }

    public int getBpm() {
        return bpm;
    }

    public boolean isRightStart() {
        return rightStart;
    }

    public boolean isLeftStart() {
        return leftStart;
    }

    public MyNoteIMPL(Major major, OnMajorChangedListener listener) {
        this.majorChangedListener = listener;
        if (majorChangedListener != null) {
            majorChangedListener.onMajorChange(this.major, major);
        }
        this.major = major;
    }

    @Override
    public MyNote setMajor(Major major) {
        if (majorChangedListener != null) {
            majorChangedListener.onMajorChange(this.major, major);
        }
        this.major = major;
        return this;
    }

    @Override
    public MyNote setPPQ(String ppq) {
        if (ppqChangedListener != null) {
            ppqChangedListener.onPPQChanged(this.ppq, ppq);
        }
        this.ppq = ppq;
        return this;
    }

    @Override
    public MyNote setBPM(int bpm) {
        if (bpmChangedListener != null) {
            bpmChangedListener.OnBPMChanged(this.bpm, bpm);
        }
        this.bpm = bpm;
        return this;
    }

    @Override
    public MyNote setRightStart(boolean rightStart) {
        this.rightStart = rightStart;
        return this;
    }

    @Override
    public MyNote setLeftStart(boolean leftStart) {
        this.leftStart = leftStart;
        return this;
    }

    /**
     * @param area 基于中央区升降几个区域
     * @return
     */
    public int getDo(int area) {
        return major.value + 12 * area;
    }

    public int getDoUp(int area) {
        return major.value + 1 + 12 * area;
    }

    public int getRe(int area) {
        return major.value + 2 + 12 * area;
    }

    public int getReUp(int area) {
        return major.value + 3 + 12 * area;
    }

    public int getMi(int area) {
        return major.value + 4 + 12 * area;
    }

    public int getFa(int area) {
        return major.value + 5 + 12 * area;
    }

    public int getFaUp(int area) {
        return major.value + 6 + 12 * area;
    }

    public int getSol(int area) {
        return major.value + 7 + 12 * area;
    }

    public int getSolUp(int area) {
        return major.value + 8 + 12 * area;
    }

    public int getLa(int area) {
        return major.value + 9 + 12 * area;
    }

    public int getLaUp(int area) {
        return major.value + 10 + 12 * area;
    }

    public int getSi(int area) {
        return major.value + 11 + 12 * area;
    }

    public MyNoteIMPL setMajor(String major) {
        if (major == null) return this;
        major = major.toLowerCase();
        switch (major) {
            case "a":
                setMajor(Major.A);
                break;
            case "a#":
                setMajor(Major.AUp);
                break;
            case "b":
                setMajor(Major.B);
                break;
            case "c":
                setMajor(Major.C);
                break;
            case "c#":
                setMajor(Major.CUp);
                break;
            case "d":
                setMajor(Major.D);
                break;
            case "d#":
                setMajor(Major.DUp);
                break;
            case "e":
                setMajor(Major.E);
                break;
            case "f":
                setMajor(Major.F);
                break;
            case "f#":
                setMajor(Major.FUp);
                break;
            case "g":
                setMajor(Major.G);
                break;
            case "g#":
                setMajor(Major.GUp);
                break;
            default:
                throw new RuntimeException("非法的音调");
        }
        return this;
    }

    public String calNote(int code) {
        if (code == 0) {
            return "0";
        }
        int res = code - 57;
        int note = res % 12;
        int area = 0;
        String prin = "";
        if (res < 0) {
            area = (res - 12) / 12;
            for (int i = 0; i < Math.abs(area); i++) {
                prin += "l";
            }
        } else {
            area = res / 12;
            for (int i = 0; i < area; i++) {
                prin += "h";
            }
        }
        switch (Math.abs(note)) {
            case 0:
                prin += "a ";
                break;
            case 1:
                prin += "a#";
                break;
            case 2:
                prin += "b ";
                break;
            case 3:
                prin += "c ";
                break;
            case 4:
                prin += "c#";
                break;
            case 5:
                prin += "d ";
                break;
            case 6:
                prin += "d#";
                break;
            case 7:
                prin += "e ";
                break;
            case 8:
                prin += "f ";
                break;
            case 9:
                prin += "f#";
                break;
            case 10:
                prin += "g ";
                break;
            case 11:
                prin += "g#";
                break;
        }
        return prin;
    }

    public String calNoteNum(int code) {
        if (code == 0) {
            return "0";
        }
        int res = code - major.value;
        int note = res % 12;
        int area = 0;
        String prin = "";
        if (res < 0) {
            area = (res - 12) / 12;
            for (int i = 0; i < Math.abs(area); i++) {
                prin += "l";
            }
        } else {
            area = res / 12;
            for (int i = 0; i < area; i++) {
                prin += "h";
            }
        }
        if (note < 0)
            note += 12;
        switch (Math.abs(note)) {
            case 0:
                prin += "1";
                break;
            case 1:
                prin += "1#";
                break;
            case 2:
                prin += "2";
                break;
            case 3:
                prin += "2#";
                break;
            case 4:
                prin += "3";
                break;
            case 5:
                prin += "4";
                break;
            case 6:
                prin += "4#";
                break;
            case 7:
                prin += "5";
                break;
            case 8:
                prin += "5#";
                break;
            case 9:
                prin += "6";
                break;
            case 10:
                prin += "6#";
                break;
            case 11:
                prin += "7";
                break;
        }
        return prin;
    }

    public int getBaseTick() {
        return baseTick;
    }

    public int getBarTick() {
        return barTick;
    }

    public void setBarTick(int barTick) {
        this.barTick = barTick;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
