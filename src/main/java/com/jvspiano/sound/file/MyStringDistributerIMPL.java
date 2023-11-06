package com.jvspiano.sound.file;

import com.jvspiano.sound.note.MyNoteIMPL;
import com.jvspiano.sound.note.NoteInfo;
import com.jvspiano.sound.resolver.AppoggiaturaResolverIMPL;
import com.jvspiano.sound.resolver.SingleNoteResolver;
import com.jvspiano.sound.resolver.SingleNoteResolverIMPL;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 行解析分发器
 */
public class MyStringDistributerIMPL implements MyStringDistributer {

    private MyNoteIMPL myNoteIMPL = new MyNoteIMPL();
    private Sequence sequence;
    private Track track;
    private SingleNoteResolver singleNoteResolver = new SingleNoteResolverIMPL();
    private AppoggiaturaResolverIMPL appoggiaturaResolverIMPL = new AppoggiaturaResolverIMPL();
    private static final String COLON = ":";
    private static SEPARATOR separator = SEPARATOR.SPACE;

    CopyOnWriteArrayList<NoteInfo> noteInfoList = new CopyOnWriteArrayList<>();

    public int rightTick = 0;
    public int leftTick = 0;
    public int totalTick = 0;

    /**
     * 行解析分发方法,以行为单位,分发给相应的解析行为
     * @param randomAccessFile 读取的文件
     */
    @Override
    public void distribute(RandomAccessFile randomAccessFile) {
        noteInfoList.clear();
        if (randomAccessFile == null)
            return;
        String line = null;
        String explan = "";
        int lineNumber = 0;
        rightTick = 0;
        leftTick = 0;

        try {
            while ((line = randomAccessFile.readLine()) != null) {
                lineNumber++;
                if (line == null || line.replace(" ", "").isEmpty()) {
                    continue;
                }
                line = line.toLowerCase();
                if (line.startsWith(Type.MAJOR.value)) {
                    System.out.println(line);
                    myNoteIMPL.setMajor(line.replace(Type.MAJOR.value + COLON, ""));
                } else if (line.startsWith(Type.PPQ.value)) {
                    System.out.println(line);
                    myNoteIMPL.setPPQ(line.replace(Type.PPQ.value + COLON, ""));
                    String[] ppq = myNoteIMPL.getPpq().split("/");
                    int ppq0 = Integer.parseInt(ppq[0]);
                    int ppq1 = Integer.parseInt(ppq[1]);
                    myNoteIMPL.setBarTick(myNoteIMPL.getBaseTick() * 4 * ppq0 / ppq1);
                    if (sequence == null) {
                        try {
                            sequence = new Sequence(Sequence.PPQ, myNoteIMPL.getBaseTick() * 4 / ppq1);
                            track = sequence.createTrack();
                        } catch (InvalidMidiDataException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (line.startsWith(Type.BPM.value)) {
                    System.out.println(line);
                    myNoteIMPL.setBPM(Integer.parseInt(line.replace(Type.BPM.value + COLON, "")));
                    NoteInfo noteInfo = new NoteInfo();
                    if (myNoteIMPL.isRightStart()){
                        noteInfo.originTick = rightTick;
                    } else if (myNoteIMPL.isLeftStart()) {
                        noteInfo.originTick = leftTick;
                    }
                    noteInfo.setBpm(myNoteIMPL.getBpm());
                    noteInfoList.add(noteInfo);
                } else if (line.startsWith(Type.EXPLANATORY_NOTE.value)) {
                    myNoteIMPL.setExplan(line.replace(Type.EXPLANATORY_NOTE.value, ""));
                    continue;
                } else if (line.startsWith(Type.RIGHT_START.value)) {
                    rightTick = 0;
                    myNoteIMPL.setRightStart(true);
                } else if (line.startsWith(Type.LEFT_START.value)) {
                    leftTick = 0;
                    myNoteIMPL.setLeftStart(true);
                } else if (line.startsWith(Type.RIGHT_END.value)) {
                    myNoteIMPL.setRightStart(false);
                } else if (line.startsWith(Type.LEFT_END.value)) {
                    myNoteIMPL.setLeftStart(false);
                } else {
                    if (myNoteIMPL.isRightStart() && myNoteIMPL.isLeftStart()) {
                        throw new RuntimeException("左右声部不能同时开始");
                    } else if (myNoteIMPL.isRightStart()) {
                        int tick = lineNoteDistributeNote(line, lineNumber, rightTick);
                        rightTick += tick;
                        if (totalTick < rightTick)
                            totalTick = rightTick;
                    } else if (myNoteIMPL.isLeftStart()) {
                        int tick = lineNoteDistributeNote(line, lineNumber, leftTick);
                        leftTick += tick;
                        if (totalTick < leftTick)
                            totalTick = leftTick;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(noteInfoList, new Comparator<NoteInfo>() {
            @Override
            public int compare(NoteInfo o1, NoteInfo o2) {
                return (int) (o1.originTick - o2.originTick);
            }
        });
    }

    public CopyOnWriteArrayList<NoteInfo> getNoteInfoList() {
        return noteInfoList;
    }

    public void setNoteInfoList(CopyOnWriteArrayList<NoteInfo> noteInfoList) {
        this.noteInfoList = noteInfoList;
    }

    /**
     * 行分发器判定行为音符时调用此方法,对改行的音符进行解析
     * @param line 从文件里读出的一行信息
     * @param lineNum 行号
     * @param lineStart 本行开始的时间tick,相对于整首曲子
     * @return 此行音符的总长度
     */
    @Override
    public int lineNoteDistributeNote(String line, int lineNum, int lineStart) {
        int lineTick = 0;
        int start = lineStart;
        String[] notesPre = line.split(separator.value);
        int noteStart = start;
        for (int i = 0; i < notesPre.length; i++) {
            if (notesPre == null || notesPre.length < 1) {
                break;
            }
            String[] singleNotes = notesPre[i].split(COLON);
            int noteTick = 0;
            for (int k = 0; k < singleNotes.length; k++) {
                if (singleNotes[k].contains("<")) {
                    String[] split = singleNotes[k].split("<");
                    NoteInfo[] noteInfos = appoggiaturaResolverIMPL.appoggiatura(split[0], split[1], noteStart, myNoteIMPL, singleNoteResolver);
                    if (noteTick < noteInfos[0].noteTick + noteInfos[1].noteTick)
                        noteTick = noteInfos[0].noteTick + noteInfos[1].noteTick;
                    noteInfoList.add(noteInfos[0]);
                    noteInfoList.add(noteInfos[1]);
                    try {
                        addNote(track, noteInfos[0].note, noteInfos[0].volume, myNoteIMPL.getChannel(), noteInfos[0].originTick, noteInfos[0].originTick + noteInfos[0].muteTick);
                        addNote(track, noteInfos[1].note, noteInfos[1].volume, myNoteIMPL.getChannel(), noteInfos[1].originTick, noteInfos[0].originTick + noteInfos[1].muteTick);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    NoteInfo noteInfo = singleNoteResolver.singleNoteResolve(myNoteIMPL, singleNotes[k], noteStart);
                    if (noteInfo == null) {
                        continue;
                    }
                    if (noteTick < noteInfo.noteTick) {
                        noteTick = noteInfo.noteTick;
                    }
                    noteInfoList.add(noteInfo);
                    try {
                        addNote(track, noteInfo.note, noteInfo.volume, myNoteIMPL.getChannel(), noteInfo.originTick, noteInfo.originTick + noteInfo.muteTick);
                    } catch (Exception e) {
                        System.out.println("异常:"+lineNum);
                        throw new RuntimeException(e);
                    }
                }
            }
            lineTick += noteTick;
            noteStart += noteTick;
        }
        if (lineTick != myNoteIMPL.getBarTick()) {
            System.out.println("小节长度有误,第" + lineNum + "行长度为" + lineTick + ",应为:" + myNoteIMPL.getBarTick());
        }
        return lineTick;
    }

    /**
     * 添加解析的音符到音轨中
     * @param track 音轨
     * @param note 音符
     * @param volume 音量
     * @param channel 频道
     * @param tick 音符的时间位置,相对于整首曲子
     * @param endTick 该音符的结束位置,相对于整首曲子
     * @throws Exception 创建音轨发生异常会抛出异常
     */
    public void addNote(Track track, int note, int volume, int channel, long tick, long endTick) throws Exception {
        ShortMessage messageOn = new ShortMessage();
        messageOn.setMessage(ShortMessage.NOTE_ON, channel, note, volume);
        MidiEvent eventOn = new MidiEvent(messageOn, tick);
        track.add(eventOn);
        //
        MetaMessage metaMessage = new MetaMessage();
        byte[] data = (messageOn.getData1() + " " + "").getBytes();
        metaMessage.setMessage(127, data, data.length);
        MidiEvent midiEvent = new MidiEvent(metaMessage, tick);
        track.add(midiEvent);
        //
        if ((endTick - tick) != myNoteIMPL.getBaseTick() * 4){
            ShortMessage messageOff = new ShortMessage();
            messageOff.setMessage(ShortMessage.NOTE_OFF, channel, note, volume);
            MidiEvent eventOff = new MidiEvent(messageOff, endTick);
            track.add(eventOff);
        }
    }

    public MyNoteIMPL getMyNoteIMPL() {
        return myNoteIMPL;
    }

    public void setMyNoteIMPL(MyNoteIMPL myNoteIMPL) {
        this.myNoteIMPL = myNoteIMPL;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
}
