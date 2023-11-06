package com.jvspiano.sound.resolver;

import com.jvspiano.sound.note.MyNoteIMPL;
import com.jvspiano.sound.note.NoteInfo;

/**
 * 钢琴技法,倚音实现类
 */
public class AppoggiaturaResolverIMPL implements AppoggiaturaResolver {
    /**
     * 钢琴技法,倚音的实现,此处定义倚音为一前一后(在音乐领域不一定是这样的,没研究过)
     * @param noteFrontString 倚音前边音符
     * @param noteAfterString 倚音后边音符
     * @param noteStart 音符开始的时间,相对于整首曲子
     * @param myNoteIMPL 音符映射类
     * @param singleNoteResolver 单个音符解析器
     * @return 音符信息的数组
     */
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
