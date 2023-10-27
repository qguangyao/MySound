package org;

import org.file.MyFIleReader;
import org.file.MyFileReaderTxt;
import org.file.MyStringDistributerIMPL;
import org.note.MyNote;
import org.note.MyNoteIMPL;

import javax.sound.midi.*;
import java.io.*;
import java.util.logging.Logger;

public class MyPlayer implements MetaEventListener {

    //(command >= 0xF0 || command < 0x80) //0x80开始，0x90结束，0xa0，0xb0，0xc0，0xd0，0xe0
    //(channel & 0xFFFFFFF0) != 0)
    //满足上边两个条件会报错
    MyStringDistributerIMPL md = new MyStringDistributerIMPL();
    MyFileReaderTxt mf = new MyFileReaderTxt();
    Sequencer player = null;
    String file;
    public MyPlayer() throws Exception{
        player = MidiSystem.getSequencer();
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

    int tick;
    boolean first = true;

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 127) {
            String data = new String(meta.getData());
            String arr[] = data.split(" ");
            String note = arr[0];
            int t = Integer.valueOf(arr[1]);
            if (tick < t / md.getMyNoteIMPL().getBarTick()) {
                tick = t / md.getMyNoteIMPL().getBarTick();
//                System.out.print("当前第" + (tick + 1) + "节：");
                System.out.println();
                System.out.print(String.format("%5d:当前第%3d/%3d节:  ",t, (tick + 1), md.totalTick / md.getMyNoteIMPL().getBarTick()));
                System.out.print(String.format("%-5s", md.getMyNoteIMPL().calNoteNum(Integer.parseInt(note))));
            } else {
                if (first) {
                    System.out.print(String.format("%5d:当前第%3d/%3d节:  ", t,(tick + 1), md.totalTick / md.getMyNoteIMPL().getBarTick()));
//                    System.out.print("当前第" + (tick + 1) + "节：");
                    first = false;
                }
                String s =  md.getMyNoteIMPL().calNoteNum(Integer.parseInt(note));
                System.out.print(String.format("%-5s",s));
            }
        } else if (meta.getType() == 47) {
//            File file = new File("起风了.midi");
//            p.saveMidiFile(file,sequence);
            System.out.println();
            System.out.println("播放完毕");
            player.close();
        } else {
            System.out.println(meta.getType());
        }
    }

    private void play() throws Exception{
        RandomAccessFile read = mf.Read(file);
        md.distribute(read);
        player.setTempoInBPM(md.getMyNoteIMPL().getBpm());
        player.open();
        Thread.sleep(200);
        player.setSequence(md.getSequence());
        player.addMetaEventListener(this);
        if (player.getSequence() == null){
            player.close();
            return;
        }
        player.start();
    }

    public static void main(String[] args) throws Exception{
        MyPlayer p = new MyPlayer();
//       p.setFile("起风了.txt");
//       p.setFile("Tassel.txt");
//       p.setFile("菊次郎的夏天.txt");
//       p.setFile("My Soul.txt");
//       p.setFile("悬溺.txt");
//        p.setFile("动天.txt");
        p.setFile("夜的钢琴曲五.txt");
        p.play();
    }
    
}