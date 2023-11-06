package com.jvspiano.sound;


import com.jvspiano.sound.note.MyNote;
import com.jvspiano.sound.note.MyNoteIMPL;

import javax.sound.midi.*;


/**
 * 教学用视频中用的类
 */
public class Teach {
    public static void main(String[] args) throws Exception {
        //https://www.oracle.com/java/technologies/java-sound-demo.html
        //4613、572、3572、613
        int scale = 8;
        int beat = 4 * scale;
        int channel = 6;
        int volume = 100;
        int tick = 0;
        int length = beat * 4;
        MyNoteIMPL myNoteIMPL = new MyNoteIMPL();
        myNoteIMPL.setMajor(MyNote.Major.D);
        Sequencer player = MidiSystem.getSequencer();
        Sequence sequence = new Sequence(Sequence.PPQ, 4 * scale);
        player.setSequence(sequence);
        Track track = sequence.createTrack();
//        try {
//            ShortMessage shortMessage = new ShortMessage();
//            shortMessage.setMessage(ShortMessage.PROGRAM_CHANGE,6,40,0);
//            MidiEvent event = new MidiEvent(shortMessage,0);
//            track.add(event);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        for (int i = 0; i < 10; i++) {
//            addNote(track, 6, 60 + i, 100, i * (beat / 2), 4, i * (beat / 2) + 1);
//        }
        left(track,channel,myNoteIMPL,volume,tick,beat,length);
        right(track,channel,myNoteIMPL,volume,tick,beat,length);

        player.open();
        Thread.sleep(100);
        player.setTempoInBPM(90);
        player.start();

    }

    private static void right(Track track, int channel, MyNoteIMPL myNoteIMPL, int volume, int tick, int beat, int length) throws Exception{
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);

        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);

        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);

        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);

        tick += addNote(track, channel, myNoteIMPL.getSol(0), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getDo(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getRe(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getMi(1), volume, tick, beat / 4, tick + beat / 4 / 4);

        tick += addNote(track, channel, myNoteIMPL.getRe(1), volume, tick, beat / 2, tick + beat / 2 / 4);
        tick += addNote(track, channel, myNoteIMPL.getDo(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getDo(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getDo(1), 0, tick, beat , tick + beat);

        tick += addNote(track, channel, 0, 0, tick, beat, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getSol(0), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getDo(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getRe(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        tick += addNote(track, channel, myNoteIMPL.getMi(1), volume, tick, beat / 4, tick + beat / 4 / 4);
        //第七小节
    }

    public static void left(Track track, int channel, MyNoteIMPL myNoteIMPL, int volume, int tick, int beat, int length) throws Exception{
        tick += addNote(track, channel, myNoteIMPL.getLa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getLa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getFa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getFa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getSol(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getDo(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(0), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getLa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getLa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getFa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getFa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getSol(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getDo(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(0), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getLa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getLa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getFa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getFa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getSol(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getDo(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(0), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getLa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getLa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getMi(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getFa(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getFa(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getSol(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getRe(-1), volume, tick, beat / 2, tick+ beat / 8);

        tick += addNote(track, channel, myNoteIMPL.getDo(-2), volume, tick, beat / 2, tick + length);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getDo(0), volume, tick, beat / 2, tick+ beat / 8);
        tick += addNote(track, channel, myNoteIMPL.getSol(-1), volume, tick, beat / 2, tick+ beat / 8);

    }

    public static int addNote(Track track, int channel, int note, int volume, int tick, int length, int endTick) throws Exception {
        ShortMessage shortMessage = new ShortMessage(ShortMessage.NOTE_ON, channel, note, volume);
        MidiEvent midiEvent = new MidiEvent(shortMessage, tick);
        track.add(midiEvent);
        ShortMessage shortMessage2 = new ShortMessage(ShortMessage.NOTE_OFF, channel, note, volume);
        MidiEvent midiEvent2 = new MidiEvent(shortMessage2, endTick);
        track.add(midiEvent2);
        return length;
    }


    public static void main1(String[] args) throws Exception{
        System.out.println("hello world");
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setTempoInBPM(90);
        Sequence sequence = new Sequence(Sequence.PPQ, 4 );
        sequencer.setSequence(sequence);
        Track track = sequence.createTrack();
        //右手部分
        addNote(track,0,60);
        addNote(track,4,60);
        addNote(track,8,67);
        addNote(track,12,67);
        addNote(track,16,69);
        addNote(track,20,69);
        addNote(track,24,67);
        addNote(track,32,65);
        addNote(track,36,65);
        addNote(track,40,64);
        addNote(track,44,64);
        addNote(track,48,62);
        addNote(track,52,62);
        addNote(track,56,60);
        //此行防止最后一个音不完整,拉长播放时间用
        addNote(track,70,0);
        //左手部分
        addNote(track,0,48);
        addNote(track,4,48);
        addNote(track,8,48);
        addNote(track,12,48);
        addNote(track,16,53);
        addNote(track,20,53);
        addNote(track,24,48);
        addNote(track,28,48);
        addNote(track,32,43);
        addNote(track,36,43);
        addNote(track,32,48);
        addNote(track,36,48);
        addNote(track,40,43);
        addNote(track,44,43);
        addNote(track,48,48);
        addNote(track,52,48);
        sequencer.open();
        Thread.sleep(100);
        sequencer.start();
    }
    private static void addNote(Track track,int tick,int data1) throws Exception{
        ShortMessage shortMessage = new ShortMessage(ShortMessage.NOTE_ON,0,data1,100);
        MidiEvent midiEvent = new MidiEvent(shortMessage,tick);
        track.add(midiEvent);
    }

}
