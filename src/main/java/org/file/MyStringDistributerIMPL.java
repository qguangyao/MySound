package org.file;

import org.note.MyNoteIMPL;
import org.note.NoteInfo;
import org.resolver.AppoggiaturaResolverIMPL;
import org.resolver.SingleNoteResolver;
import org.resolver.SingleNoteResolverIMPL;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyStringDistributerIMPL implements MyStringDistributer {

    private MyNoteIMPL myNoteIMPL = new MyNoteIMPL();
    private Sequence sequence;
    private Track track;
    private SingleNoteResolver singleNoteResolver = new SingleNoteResolverIMPL();
    private AppoggiaturaResolverIMPL appoggiaturaResolverIMPL = new AppoggiaturaResolverIMPL();
    private static final String COLON = ":";
    private static SEPARATOR separator = SEPARATOR.SPACE;

    public int rightTick = 0;
    public int leftTick = 0;
    public int totalTick = 0;

    @Override
    public void distribute(RandomAccessFile randomAccessFile) {
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
    }

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


    public void addNote(Track track, int note, int volume, int channel, int tick, int endTick) throws Exception {
        ShortMessage messageOn = new ShortMessage();
        messageOn.setMessage(ShortMessage.NOTE_ON, channel, note, volume);
        MidiEvent eventOn = new MidiEvent(messageOn, tick);
        track.add(eventOn);
        //
        MetaMessage metaMessage = new MetaMessage();
        byte[] data = String.valueOf(messageOn.getData1() + " " + tick).getBytes();
        metaMessage.setMessage(127, data, data.length);
        MidiEvent midiEvent = new MidiEvent(metaMessage, tick);
        track.add(midiEvent);
        //
        ShortMessage messageOff = new ShortMessage();
        messageOff.setMessage(ShortMessage.NOTE_OFF, channel, note, volume);
        MidiEvent eventOff = new MidiEvent(messageOff, endTick);
        track.add(eventOff);
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
