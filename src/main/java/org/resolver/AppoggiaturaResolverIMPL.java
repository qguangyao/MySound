package org.resolver;

import org.note.MyNoteIMPL;
import org.note.NoteInfo;

public class AppoggiaturaResolverIMPL implements AppoggiaturaResolver {
    @Override
    public NoteInfo[] appoggiatura(String noteFrontString, String noteAfterString, int noteStart, MyNoteIMPL myNoteIMPL, SingleNoteResolver singleNoteResolver) {
        NoteInfo noteInfo0 = singleNoteResolver.singleNoteResolve(myNoteIMPL, noteFrontString, noteStart);
        NoteInfo noteInfo1 = singleNoteResolver.singleNoteResolve(myNoteIMPL, noteAfterString, noteStart);
        NoteInfo[] noteInfos = appoggiatura(noteInfo0, noteInfo1, myNoteIMPL);
        return noteInfos;
    }

    private NoteInfo[] appoggiatura(NoteInfo info1, NoteInfo info2, MyNoteIMPL myNoteIMPL) {
        info1.noteTick = myNoteIMPL.getBaseTick() / 16;
        info2.noteTick = info2.noteTick - info1.noteTick;
        info2.originTick += info1.noteTick;
        return new NoteInfo[]{info1, info2};
    }
}
