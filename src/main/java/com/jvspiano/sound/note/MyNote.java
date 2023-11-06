package com.jvspiano.sound.note;

/**
 * 音符映射
 */
public interface MyNote {
    enum Major {
        A(57), AUp(58), B(59), C(60), CUp(61), D(62),
        DUp(63), E(64), F(65), FUp(66), G(67), GUp(68);
        public final int value;

        Major(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    MyNote setMajor(Major major);
    MyNote setPPQ(String ppq);
    MyNote setBPM(int bpm);
    MyNote setRightStart(boolean rightStart);
    MyNote setLeftStart(boolean leftStart);
}
