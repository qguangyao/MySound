package com.jvspiano.sound.resolver;


import com.jvspiano.sound.note.MyNoteIMPL;
import com.jvspiano.sound.note.NoteInfo;

/**
 * 单个音符解析接口定义
 */
public interface SingleNoteResolver {
    NoteInfo singleNoteResolve(MyNoteIMPL myNoteIMPL, String noteString, int originTick);
}
