package org.resolver;

import org.note.MyNoteIMPL;
import org.note.NoteInfo;

public interface SingleNoteResolver {
    NoteInfo singleNoteResolve(MyNoteIMPL myNoteIMPL,String noteString, int originTick);
}
