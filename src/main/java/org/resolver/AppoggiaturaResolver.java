package org.resolver;

import org.note.MyNoteIMPL;
import org.note.NoteInfo;

/**
 * 处理倚音倚音
 */
public interface AppoggiaturaResolver {
    NoteInfo[] appoggiatura(String noteFrontString, String noteAfterString, int noteStart, MyNoteIMPL myNoteIMPL, SingleNoteResolver singleNoteResolver);
}
