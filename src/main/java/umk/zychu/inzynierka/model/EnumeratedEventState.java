package umk.zychu.inzynierka.model;

/**
 * Created by emag on 21.12.16.
 */
public enum EnumeratedEventState {

    IN_A_BASKET(1),
    IN_PROGRESS(2),
    READY_TO_ACCEPT(3),
    THREATENED(4),
    APPROVED(5);

    private int value;

    EnumeratedEventState(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
