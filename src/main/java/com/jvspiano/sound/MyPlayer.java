package com.jvspiano.sound;


import com.jvspiano.sound.file.MyFileReaderTxt;
import com.jvspiano.sound.file.MyStringDistributerIMPL;
import com.jvspiano.sound.note.MyNoteIMPL;
import com.jvspiano.sound.note.NoteInfo;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 对外接口
 */
public class MyPlayer implements MetaEventListener {

    //(command >= 0xF0 || command < 0x80) //0x80开始，0x90结束，0xa0，0xb0，0xc0，0xd0，0xe0
    //(channel & 0xFFFFFFF0) != 0)
    //满足上边两个条件会报错
    MyStringDistributerIMPL md = new MyStringDistributerIMPL();
    MyFileReaderTxt mf = new MyFileReaderTxt();
    Sequencer player = null;
    String file;
    private boolean reserve = false;

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public MyPlayer() throws Exception {
        player = MidiSystem.getSequencer();
    }

    public CopyOnWriteArrayList<NoteInfo> getNoteInfoList() {
        return md.getNoteInfoList();
    }

    public long getCurrentTick() {
        return player.getTickPosition();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void saveMidiFile(File file, Sequence sequence) {
        try {
            int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
            if (fileTypes.length == 0) {
                System.out.println("Can't save sequence");
            } else {
                if (MidiSystem.write(sequence, fileTypes[0], file) == -1) {
                    throw new IOException("Problems writing to file");
                }
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


//    public int getCurrentTick() {
//        return currentTick;
//    }

    long tick;
    boolean first = true;
//    int currentTick;

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 127) {
            String data = new String(meta.getData());
            String arr[] = data.split(" ");
            String note = arr[0];
            long currentTick = player.getTickPosition();
            if (tick < currentTick / md.getMyNoteIMPL().getBarTick()) {
                tick = currentTick / md.getMyNoteIMPL().getBarTick();
                System.out.println();
                System.out.print(String.format("%5d:当前第%3d/%3d节:  ", currentTick, (tick + 1), player.getTickLength() / md.getMyNoteIMPL().getBarTick()));
                System.out.print(String.format("%-5s", md.getMyNoteIMPL().calNoteNum(Integer.parseInt(note))));
            } else {
                if (first) {
                    System.out.print(String.format("%5d:当前第%3d/%3d节:  ", currentTick, (tick + 1), player.getTickLength() / md.getMyNoteIMPL().getBarTick()));
                    first = false;
                }
                String s = md.getMyNoteIMPL().calNoteNum(Integer.parseInt(note));
                System.out.print(String.format("%-5s", s));
            }
        } else if (meta.getType() == 47) {
            System.out.println();
            System.out.println("播放完毕");
            player.close();
        } else {
            System.out.println(meta.getType());
        }
    }

    public MyNoteIMPL getMyNoteIMPL() {
        return md.getMyNoteIMPL();
    }

    public float getBPM() {
        return player.getTempoInBPM();
    }

    public String getPPQ() {
        return getMyNoteIMPL().getPpq();
    }

    public void play() throws Exception {
        RandomAccessFile read = mf.Read(file);
        md.distribute(read);
        player.setTempoInBPM(md.getMyNoteIMPL().getBpm());
        player.open();
        Thread.sleep(200);
        if (reserve){
            reversePlay();
        }
        player.setSequence(md.getSequence());
        player.addMetaEventListener(this);
        if (player.getSequence() == null) {
            player.close();
            return;
        }
        player.start();
    }

    /**
     * 倒放
     */
    private void reversePlay() {
        Track track = md.getSequence().getTracks()[0];
        Track track1 = md.getSequence().createTrack();
        CopyOnWriteArrayList<NoteInfo> noteInfoList = md.getNoteInfoList();
        CopyOnWriteArrayList<NoteInfo> noteInfoList2 = new CopyOnWriteArrayList();

        for (int i = 0; i < track.size(); i++) {
            MidiEvent midiEvent = track.get(i);
            long tick = track.ticks() - midiEvent.getTick();
            midiEvent.setTick(tick);
            track1.add(midiEvent);
        }
        for (int i = noteInfoList.size() - 1; i >= 0; i--) {
            long tick = track1.ticks();
            NoteInfo noteInfo = noteInfoList.get(i);
            noteInfo.setOriginTick(tick - noteInfo.getOriginTick());
            noteInfoList2.add(noteInfo);
        }
        md.setNoteInfoList(noteInfoList2);
        md.getSequence().deleteTrack(track);
    }

    public boolean isRunning() {
        if (player == null)
            return false;
        return player.isRunning();
    }

    public static void main(String[] args) throws Exception {
//        Instrument[] instruments = MidiSystem.getSynthesizer().getDefaultSoundbank().getInstruments();
//        for (int i = 0; i < instruments.length; i++) {
//            if (instruments[i].getName().contains("Violin"))
//            System.out.println(i+":"+instruments[i].getName());
//        }
        MyPlayer p = new MyPlayer();
        p.setReserve(false);//写true就倒放,好玩捏
//       p.setFile("起风了.txt");
//       p.setFile("Tassel.txt");
//       p.setFile("菊次郎的夏天.txt");
//       p.setFile("My Soul.txt");
       p.setFile("悬溺.txt");
//        p.setFile("动天.txt");//不好听
//        p.setFile("夜的钢琴曲五.txt");
//        p.setFile("生日快乐歌.txt");//不好听
//        p.setFile("komorebi.txt");
        p.play();

    }

}