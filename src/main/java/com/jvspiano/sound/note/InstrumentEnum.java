package com.jvspiano.sound.note;

public enum InstrumentEnum {

    ACOUSTIC_GRAND_PIANO(0,"大钢琴"),
    ELECTRIC_GRAND_PIANO(2,"电钢琴"),
    VIOLIN(40,"小提琴"),
    ;

    public final int value;
    public final String znName;

    InstrumentEnum(int value,String znName){
        this.value = value;
        this.znName = znName;
    }
}
