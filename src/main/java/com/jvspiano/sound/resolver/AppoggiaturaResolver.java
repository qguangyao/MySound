package com.jvspiano.sound.resolver;


import com.jvspiano.sound.note.MyNoteIMPL;
import com.jvspiano.sound.note.NoteInfo;

/**
 * 处理倚音倚音接口定义
 */
public interface AppoggiaturaResolver {
    NoteInfo[] appoggiatura(String noteFrontString, String noteAfterString, int noteStart, MyNoteIMPL myNoteIMPL, SingleNoteResolver singleNoteResolver);
}
