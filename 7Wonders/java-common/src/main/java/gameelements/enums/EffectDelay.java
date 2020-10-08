package gameelements.enums;

public enum EffectDelay {
    INSTANTANEOUS(0),
    END_OF_THE_GAME(1);

    private final int index;

    EffectDelay(int index) {
        this.index = index;
    }
}