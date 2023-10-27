package org;


import org.note.MyNote;
import org.note.MyNoteIMPL;

import javax.sound.midi.*;
import java.awt.*;

public class Teach {
    public static void main1(String[] args) throws Exception {
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

    public static void left( Track track,int channel,MyNoteIMPL myNoteIMPL,int volume,int tick,int beat,int length) throws Exception{
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

    public static void main(String[] args) throws Exception{
        Robot robot = new Robot();
    }

}
