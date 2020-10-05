package gameelements.enums;

public enum EffectFrequency {
    ONCE(0),
    EVERY_TURN(1),
    EVERY_AGE(2);

    private final int index;

    EffectFrequency(int index) {
        this.index = index;
    }
}
