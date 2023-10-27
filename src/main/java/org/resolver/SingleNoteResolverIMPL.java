package org.resolver;

import org.note.MyNoteIMPL;
import org.note.NoteInfo;

public class SingleNoteResolverIMPL implements SingleNoteResolver{
    @Override
    public NoteInfo singleNoteResolve(MyNoteIMPL myNoteIMPL ,String noteString, int originTick) {
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.originTick = originTick;
        if (noteString == null || noteString.isEmpty()) {
            return null;
        }
        int tick = myNoteIMPL.getBaseTick();
        int deta = 0;
        int note = -1;
        boolean hasPoint = false;
        int volume = 100;
        int noteMuteTick = 0;
        char[] separatedNote = noteString.toCharArray();
        for (int i = 0; i < separatedNote.length; i++) {
            if (separatedNote[i] == '+') {
                tick = tick + myNoteIMPL.getBaseTick();
            } else if (separatedNote[i] == '-') {
                tick = tick >> 1;
            } else if (separatedNote[i] == 'l') {
                deta -= 12;
            } else if (separatedNote[i] == 'h') {
                deta += 12;
            } else if (separatedNote[i] == 'u') {
                deta += 1;
            } else if (separatedNote[i] == 'b') {
                deta -= 1;
            } else if (separatedNote[i] == '0') {
                note = 0;
                volume = 0;
            } else if (separatedNote[i] == '1') {
                note = myNoteIMPL.getDo(0);
            } else if (separatedNote[i] == '2') {
                note = myNoteIMPL.getRe(0);
            } else if (separatedNote[i] == '3') {
                note = myNoteIMPL.getMi(0);
            } else if (separatedNote[i] == '4') {
                note = myNoteIMPL.getFa(0);
            } else if (separatedNote[i] == '5') {
                note = myNoteIMPL.getSol(0);
            } else if (separatedNote[i] == '6') {
                note = myNoteIMPL.getLa(0);
            } else if (separatedNote[i] == '7') {
                note = myNoteIMPL.getSi(0);
            } else if (separatedNote[i] == '.') {
                hasPoint = true;
            } else if (separatedNote[i] == '^') {
                volume = 0;
            } else if (separatedNote[i] == '>') {
                noteMuteTick++;
            }
        }
        if (hasPoint) {
            tick = tick * 3 / 2;
        }
        noteInfo.noteTick = tick;
        if (noteMuteTick > 0) {
            noteMuteTick = tick * noteMuteTick / 4;
//                                noteEndTick = baseTick * 4;
        } else {
            noteMuteTick = myNoteIMPL.getBaseTick() * 4;
        }
        noteInfo.muteTick = noteMuteTick;
        noteInfo.volume = volume;
        noteInfo.note = note + deta;
        return noteInfo;
    }
}
