package gameelements;

public abstract class Effect {
    String name = null;
    // 0 = instantaneous, 1 = end of the game
    int delay = 0;
    // 0 = once, 1 every turn, 2 every Age
    int repeat = 0;
    // Change to true if already activated.
    boolean status = false;


    public Effect(String name, int delay, int repeat) {
        this.name = name;
        this.delay = delay;
        this.repeat = repeat;
    }

    public Effect(String name) {
        this.name = name;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect() {
        if (delay == 0 && repeat == 0) {
            if (!status) {
                throw new Error("Effect already executed.");
            }
        }
    }

    protected void changeStatus() {
        this.status = !this.status;
    }
}
